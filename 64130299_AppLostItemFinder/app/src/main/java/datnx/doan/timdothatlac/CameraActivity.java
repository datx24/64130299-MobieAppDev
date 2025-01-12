package datnx.doan.timdothatlac;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;
import android.util.Size;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.objects.DetectedObject;
import com.google.mlkit.vision.objects.ObjectDetection;
import com.google.mlkit.vision.objects.ObjectDetector;
import com.google.mlkit.vision.objects.defaults.ObjectDetectorOptions;

import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.DMatch;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.features2d.BFMatcher;
import org.opencv.features2d.ORB;
import org.opencv.imgproc.Imgproc;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutionException;
import com.google.common.util.concurrent.ListenableFuture;

public class CameraActivity extends AppCompatActivity implements LifecycleOwner {

    private static final double THRESHOLD = 30.0; // Similarity threshold for feature matching

    private PreviewView previewView;
    private TextView comparisonResultText;
    private ImageView sampleImageView;
    private String imageUrl;
    private Bitmap storedImageBitmap;
    private ExecutorService cameraExecutor;
    private ObjectDetector objectDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        // Thiết lập tool bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Thiết lập nút quay lại
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);//Hiển thị nút quay lại
        }

        previewView = findViewById(R.id.previewView);
        comparisonResultText = findViewById(R.id.comparisonResultText);
        sampleImageView = findViewById(R.id.sampleImageView);

        // Retrieve image URL from intent
        imageUrl = getIntent().getStringExtra("image_url");

        // Load the image from the URL for comparison
        if (imageUrl != null && !imageUrl.isEmpty()) {
            loadImageFromUrl(imageUrl);
        }

        // Setup camera
        cameraExecutor = Executors.newSingleThreadExecutor();
        setUpCamera();

        // Initialize object detector
        ObjectDetectorOptions options = new ObjectDetectorOptions.Builder()
                .setDetectorMode(ObjectDetectorOptions.SINGLE_IMAGE_MODE)
                .enableMultipleObjects()
                .build();
        objectDetector = ObjectDetection.getClient(options);
    }

    // Xử lý sự kiện khi người dùng nhấn nút quay lại trên Toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Xử lý sự kiện khi nhấn nút quay lại
        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // Quay lại Activity trước đó
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Setup camera for capturing images
    private void setUpCamera() {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(this);

        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();

                // Setup Preview use case
                Preview preview = new Preview.Builder().build();
                preview.setSurfaceProvider(previewView.getSurfaceProvider());

                // Setup ImageAnalysis for analyzing frames
                ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                        .setTargetResolution(new Size(1920, 1080))
                        .build();

                imageAnalysis.setAnalyzer(cameraExecutor, image -> {
                    // Convert ImageProxy to Bitmap
                    Bitmap cameraBitmap = imageProxyToBitmap(image);
                    detectObjectsInImage(cameraBitmap);
                    image.close();  // Close ImageProxy after processing
                });

                CameraSelector cameraSelector = new CameraSelector.Builder()
                        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                        .build();

                // Bind Preview and ImageAnalysis to camera lifecycle
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageAnalysis);
            } catch (ExecutionException | InterruptedException e) {
                Log.e("CameraActivity", "Camera setup failed", e);
            }
        }, ContextCompat.getMainExecutor(this));
    }

    // Convert ImageProxy to Bitmap
    private Bitmap imageProxyToBitmap(ImageProxy image) {
        ImageProxy.PlaneProxy plane = image.getPlanes()[0];
        ByteBuffer buffer = plane.getBuffer();
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    // Load image from URL and set to ImageView
    private void loadImageFromUrl(String url) {
        Glide.with(this)
                .asBitmap()
                .load(url)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                        storedImageBitmap = bitmap;
                        sampleImageView.setVisibility(View.VISIBLE);
                        sampleImageView.setImageBitmap(storedImageBitmap);
                    }
                });
    }

    // Detect objects in camera image
    private void detectObjectsInImage(Bitmap cameraBitmap) {
        if (cameraBitmap != null) {
            InputImage image = InputImage.fromBitmap(cameraBitmap, 0);
            objectDetector.process(image)
                    .addOnSuccessListener(detectedObjects -> {
                        drawBoundingBox(cameraBitmap, detectedObjects);
                        compareDetectedObjectsWithStoredImage(detectedObjects, cameraBitmap);
                    })
                    .addOnFailureListener(e -> {
                        Log.e("ObjectDetection", "Object detection failed", e);
                        updateComparisonResult(false);
                    });
        }
    }

    // Draw bounding boxes around detected objects
    private void drawBoundingBox(Bitmap bitmap, List<DetectedObject> detectedObjects) {
        Bitmap mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(mutableBitmap);
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);

        for (DetectedObject detectedObject : detectedObjects) {
            Rect boundingBox = detectedObject.getBoundingBox();
            RectF boundingBoxF = new RectF(boundingBox);
            canvas.drawRect(boundingBoxF, paint);
        }

        sampleImageView.setImageBitmap(mutableBitmap);
    }

    // Compare detected objects with the stored image
    private void compareDetectedObjectsWithStoredImage(List<DetectedObject> detectedObjects, Bitmap cameraBitmap) {
        if (detectedObjects != null && !detectedObjects.isEmpty()) {
            Mat storedMat = bitmapToMat(storedImageBitmap);
            MatOfKeyPoint storedKeyPoints = new MatOfKeyPoint();
            Mat storedDescriptors = new Mat();
            ORB orb = ORB.create();
            orb.detectAndCompute(storedMat, new Mat(), storedKeyPoints, storedDescriptors);

            Mat cameraMat = bitmapToMat(cameraBitmap);
            MatOfKeyPoint cameraKeyPoints = new MatOfKeyPoint();
            Mat cameraDescriptors = new Mat();
            orb.detectAndCompute(cameraMat, new Mat(), cameraKeyPoints, cameraDescriptors);

            BFMatcher matcher = BFMatcher.create(BFMatcher.BRUTEFORCE_HAMMING, true);
            MatOfDMatch matches = new MatOfDMatch();
            matcher.match(storedDescriptors, cameraDescriptors, matches);

            double totalDistance = 0;
            for (DMatch match : matches.toArray()) {
                totalDistance += match.distance;
            }
            double averageDistance = totalDistance / matches.rows();

            if (averageDistance < THRESHOLD) {
                updateComparisonResult(true);
            } else {
                updateComparisonResult(false);
            }
        } else {
            updateComparisonResult(false);
        }
    }

    // Convert Bitmap to OpenCV Mat
    private Mat bitmapToMat(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] intArray = new int[width * height];
        bitmap.getPixels(intArray, 0, width, 0, 0, width, height);
        Mat mat = new Mat(height, width, CvType.CV_8UC4);
        mat.put(0, 0, intArray);
        return mat;
    }

    // Update UI with comparison result
    private void updateComparisonResult(boolean isSame) {
        if (isSame) {
            comparisonResultText.setText("Đối tượng giống nhau!");
        } else {
            comparisonResultText.setText("Không giống nhau!");
        }
        comparisonResultText.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cameraExecutor.shutdown();
    }
}
