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
import android.util.Log;
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

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;
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

import android.location.Location;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AddItemActivity extends AppCompatActivity {
    // Khởi tạo Firestore
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    // Truy cập vị trí hiện tại
    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CAMERA_PERMISSION = 100;
    // Khai báo mã yêu cầu quyền vị trí
    private static final int REQUEST_LOCATION_PERMISSION = 100;
    private ImageView imageView;
    private Uri photoUri; // Lưu trữ Uri ảnh chất lượng cao
    private TextInputEditText etItemName, etItemDescription;
    private MaterialButton btnCaptureImage, btnSaveItem;
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
        // Khởi tạo view
        btnCaptureImage = findViewById(R.id.btnCaptureImage);
        btnSaveItem = findViewById(R.id.btnSaveItem);
        etItemName = findViewById(R.id.etItemName);
        etItemDescription = findViewById(R.id.etItemDescription);
        imageView = findViewById(R.id.imgItemPreview);


        // Mở camera khi bấm nút Capture Image
        btnCaptureImage.setOnClickListener(view -> openCameraForPhoto());

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        btnSaveItem.setOnClickListener(view -> getCurrentLocationAndFetchAddress());

    }

    private void getCurrentLocationAndFetchAddress() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        } else {
            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                fetchAddressFromNominatim(location.getLatitude(), location.getLongitude());
                            } else {
                                Toast.makeText(AddItemActivity.this, "Không thể xác định vị trí hiện tại", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void fetchAddressFromNominatim(double latitude, double longitude) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://nominatim.openstreetmap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NominatimApi api = retrofit.create(NominatimApi.class);

        api.getLocation(latitude, longitude, "json").enqueue(new Callback<LocationResponse>() {
            @Override
            public void onResponse(Call<LocationResponse> call, Response<LocationResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String address = response.body().getDisplayName();
                    Toast.makeText(AddItemActivity.this, "Address: " + address, Toast.LENGTH_LONG).show();

                    // Lưu địa chỉ vào cơ sở dữ liệu
                    saveItemToFirestore(latitude, longitude, address);
                } else {
                    String address = null;
                    Toast.makeText(AddItemActivity.this, "Address not found", Toast.LENGTH_SHORT).show();
                    saveItemToFirestore(latitude, longitude, address);
                }
            }

            @Override
            public void onFailure(Call<LocationResponse> call, Throwable t) {
                String address = null;
                Toast.makeText(AddItemActivity.this, "API Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                saveItemToFirestore(latitude, longitude, address);
            }
        });
    }

    public void openCameraForPhoto() {
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

    // Lưu thông tin vào Firestore
    private void saveItemToFirestore(double latitude, double longitude, String address) {
        // Lấy dữ liệu từ giao diện
        String itemName = etItemName.getText().toString().trim();
        String itemDescription = etItemDescription.getText().toString().trim();
        String imageUrl = (photoUri != null) ? photoUri.toString() : "";

        if (itemName.isEmpty() || itemDescription.isEmpty() || imageUrl.isEmpty()) {
            Toast.makeText(this, "All fields are required!", Toast.LENGTH_SHORT).show();
            return;
        }



        // Tạo đối tượng để lưu
        Item item = new Item(itemName, itemDescription, imageUrl, latitude, longitude, address);

        // Thêm vào Firestore
        db.collection("items")
                .add(item)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, "Item saved successfully!", Toast.LENGTH_SHORT).show();
                    Log.d("Firestore", "Document ID: " + documentReference.getId());
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error saving item: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.e("Firestore", "Error", e);
                });
    }
}


