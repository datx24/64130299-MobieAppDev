package datnx.doan.timdothatlac;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.label.ImageLabeling;
import com.google.mlkit.vision.label.ImageLabeler;
import com.google.mlkit.vision.label.ImageLabel;
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions;
import com.google.mlkit.vision.objects.ObjectDetection;
import com.google.mlkit.vision.objects.ObjectDetector;
import com.google.mlkit.vision.objects.defaults.ObjectDetectorOptions;
import com.google.mlkit.vision.objects.DetectedObject;

import java.io.IOException;
import java.util.List;

public class AddItemActivity extends AppCompatActivity {
    private static final int REQUEST_CAMERA_PERMISSION = 100;
    private ImageView imageView;
    private Uri photoUri; // Lưu trữ Uri ảnh chất lượng cao
    private final ActivityResultLauncher<Intent> cameraResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        try {
                            // Sử dụng MediaStore để lấy ảnh chất lượng cao
                            Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), photoUri);
                            imageView.setImageBitmap(imageBitmap);

                            // Gọi hàm nhận diện nhãn và đối tượng sau khi ảnh được tải lên
                            labelImage(imageBitmap); // Nhận diện nhãn
                            detectObjects(imageBitmap); // Nhận diện đối tượng

                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(AddItemActivity.this, "Failed to load image", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        imageView = findViewById(R.id.imgItemPreview);
    }

    public void openCameraForPhoto(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        } else {
            launchCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                launchCamera();
            } else {
                Toast.makeText(this, "Camera permission is required", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void launchCamera() {
        // Tạo một Uri trong MediaStore để lưu ảnh chất lượng cao
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From Camera");
        photoUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri); // Chuyển Uri vào intent

        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            cameraResultLauncher.launch(cameraIntent);
        }
    }

    // Nhận diện nhãn trong ảnh sử dụng Image Labeling
    private void labelImage(Bitmap imageBitmap) {
        // Chuyển đổi từ Bitmap sang InputImage để sử dụng với ML Kit
        InputImage image = InputImage.fromBitmap(imageBitmap, 0);

        // Tạo đối tượng ImageLabelerOptions để cấu hình ML Kit
        ImageLabelerOptions options = new ImageLabelerOptions.Builder()
                .setConfidenceThreshold(0.8f)
                // Đặt ngưỡng độ tin cậy tối thiểu cho nhãn
                .build();

        // Sử dụng ImageLabeling để nhận diện nhãn
        ImageLabeler labeler = ImageLabeling.getClient(options);

        // Gọi phương thức process để nhận diện nhãn từ ảnh
        labeler.process(image)
                .addOnSuccessListener(labels -> {
                    // Xử lý nhãn đã nhận diện được
                    for (ImageLabel label : labels) {
                        String text = label.getText();  // Tên nhãn
                        float confidence = label.getConfidence();  // Độ tự tin của nhãn
                        // Hiển thị kết quả nhận diện
                        Toast.makeText(AddItemActivity.this, "Label: " + text + ", Confidence: " + confidence, Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    // Xử lý khi nhận diện thất bại
                    Toast.makeText(AddItemActivity.this, "Labeling failed", Toast.LENGTH_SHORT).show();
                });
    }


    private void detectObjects(Bitmap imageBitmap) {
        // Chuyển đổi ảnh Bitmap sang InputImage
        InputImage image = InputImage.fromBitmap(imageBitmap, 0);

        // Tùy chọn phát hiện đối tượng
        ObjectDetectorOptions options = new ObjectDetectorOptions.Builder()
                .setDetectorMode(ObjectDetectorOptions.SINGLE_IMAGE_MODE) // Chỉ xử lý 1 ảnh
                .build();

        // Tạo đối tượng ObjectDetector từ ML Kit
        ObjectDetector objectDetector = ObjectDetection.getClient(options);

        // Xử lý ảnh để phát hiện đối tượng
        objectDetector.process(image)
                .addOnSuccessListener(detectedObjects -> {
                    // Hiển thị thông tin các đối tượng phát hiện được
                    Toast.makeText(AddItemActivity.this,
                            "Số lượng đối tượng phát hiện: " + detectedObjects.size(),
                            Toast.LENGTH_SHORT).show();

                    // Vẽ bounding box lên ảnh
                    drawBoundingBoxes(imageBitmap, detectedObjects);
                })
                .addOnFailureListener(e -> {
                    // Xử lý lỗi khi nhận diện thất bại
                    Toast.makeText(AddItemActivity.this,
                            "Nhận diện đối tượng thất bại: " + e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                });
    }


    private void drawBoundingBoxes(Bitmap imageBitmap, List<DetectedObject> detectedObjects) {
        Bitmap mutableBitmap = imageBitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(mutableBitmap);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        paint.setColor(Color.RED);

        for (DetectedObject object : detectedObjects) {
            Rect boundingBox = object.getBoundingBox();
            canvas.drawRect(boundingBox, paint);
        }
        imageView.setImageBitmap(mutableBitmap);
    }

}
