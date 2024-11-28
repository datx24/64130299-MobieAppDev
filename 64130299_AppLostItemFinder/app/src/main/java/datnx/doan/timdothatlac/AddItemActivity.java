package datnx.doan.timdothatlac;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
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
import androidx.annotation.Nullable;
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
    private static final int REQUEST_CAMERA_PERMISSION = 100;
    private static final int REQUEST_LOCATION_PERMISSION = 100;
    private ImageView imageView;
    private Uri photoUri;  //đại diện cho 1 địa chỉ hình ảnh
    private TextInputEditText etItemName,etItemDescription;
    private MaterialButton btnCaptureImage, btnSaveItem;
    private FusedLocationProviderClient fusedLocationProviderClient; // biến để truy cập vị trí đồ vật

    //sqllite db helper
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        //Khởi tạo các thành phần trong giao diện
        btnCaptureImage = findViewById(R.id.btnCaptureImage); //Nút chụp ảnh
        btnSaveItem = findViewById(R.id.btnSaveItem); //Nút lưu item
        etItemName = findViewById(R.id.etItemName); //Ô nhập tên item
        etItemDescription = findViewById(R.id.etItemDescription); //Ô nhập mô tả item
        imageView = findViewById(R.id.imgItemPreview);//Vùng hiển thị hình ảnh đã chụp

        //tạo đối đượng để thao tác ghi dữ liệu
        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        //Thiết lập sự kiện cho nút chụp ảnh
        btnCaptureImage.setOnClickListener(view -> openCameraForPhoto());

        //Khởi tạo đối tượng để lấy vị trí của item hiện tại
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        //Thiết lập sự kiện cho nút lưu đồ vật
        btnSaveItem.setOnClickListener(view -> getCurrentLocationAndSaveItem());
    }

    //Hàm để lấy vị trí hiện tại và gọi phương thức để lưu thông tin đồ vật
    private void getCurrentLocationAndSaveItem() {
        //Kiểm tra xem ứng dụng đã có quyền truy cập chưa
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            //Nếu chưa có quyền, yêu cầu truy cập vị trí
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION_PERMISSION);
        } else {
            //Nếu đã có quyền, lấy vị trí hiện tại của thiết bị
            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(location -> {
                        //Kiểm tra xem vị trí có khác null không
                        if(location != null) {
                            //Lưu thông tin vị trí (vĩ độ, kinh độ) vào cơ sở dữ liệu SQLite
                            saveItemToSQLite(location.getLatitude(),location.getLongitude(), null)l
                        } else {
                            //Hiển thị thông báo lỗi nếu như không xác định vị trí hiện tại
                            Toast.makeText(AddItemActivity.this,"Không thể xác định vị trí hiện tại",Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    //Hàm lưu thông tin đồ vật
    private void saveItemToSQLite(double latitude, double longitude, String address) {
    }

    //Phương thức kiểm tra quyền truy cập để mở camera
    private void openCameraForPhoto() {
        //Kiểm tra xem ứng dụng đã có quyền truy cập hay chưa
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            //Nếu chưa có thì yêu cầu truy cập
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},REQUEST_CAMERA_PERMISSION);
        } else {
            //Nếu có gọi hàm để mở camera
            launchCamera();
        }
    }

    //Phương thức mở camera
    private void launchCamera() {
        //Tạo 1 đối tượng ContentValues để lưu thông tin ảnh
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE, "NEW PICTURE");//Đặt tiêu đề ảnh
        contentValues.put(MediaStore.Images.Media.DESCRIPTION, "FROM CAMERA");//Đặt mô tả cho ảnh
        //Chèn thông tin ảnh vào MediaStore và lấy URI của hình ảnh
        photoUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        //Tạo Intent để mở camera
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //Gắn uri của ảnh vào intent để lưu ảnh đã chụp
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        //Kiểm tra xem ứng dụng nào có thể xử lý được Intent này
        if(cameraIntent.resolveActivity(getPackageManager()) != null){
            //Chạy camera và chờ kết quả
            cameraResultLaucher.launch(cameraIntent);
        }
    }

    //Hàm xử lý kết quả từ việc chụp ảnh
    private final ActivityResultLauncher<Intent> cameraResultLaucher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),// dùng để khởi động và nhận kết quả
            new ActivityResultCallback<ActivityResult>() { // định nghĩa callback để xử lý kết quả
                @Override
                public void onActivityResult(ActivityResult result) {
                    //Kiểm tra kết quả trả về thành công hay không
                    if (result.getResultCode() == RESULT_OK) {
                        try {
                            //Lấy hình ảnh từ URL
                            Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), photoUri);

                            //Hiển thị ảnh lên imageView
                            imageView.setImageBitmap(imageBitmap);

                        } catch (IOException e) {
                            e.printStackTrace();
                            //Hiển thị thông báo lỗi cho người dùng
                            Toast.makeText(AddItemActivity.this, "Load ảnh không thành công", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
    );


}


