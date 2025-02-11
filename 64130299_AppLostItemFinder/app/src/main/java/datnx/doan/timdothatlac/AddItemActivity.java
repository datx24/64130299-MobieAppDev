package datnx.doan.timdothatlac;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import java.io.IOException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

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

        // Thiết lập tool bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Thiết lập nút quay lại
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);//Hiển thị nút quay lại
        }

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
                            saveItemToSQLite(location.getLatitude(),location.getLongitude(), null);
                        } else {
                            //Hiển thị thông báo lỗi nếu như không xác định vị trí hiện tại
                            Toast.makeText(AddItemActivity.this,"Không thể xác định vị trí hiện tại",Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    //Hàm lưu thông tin đồ vật
    private void saveItemToSQLite(double latitude, double longitude, String address) {
        //Lấy tên và mô tả từ các ô nhập
        String itemName = etItemName.getText().toString().trim();
        String itemDescription = etItemDescription.getText().toString().trim();
        //Lấy url hình ảnh nếu có
        String imageUrl = (photoUri != null) ? photoUri.toString() : "";
        //Kiểm tra xem tên,mô tả và hình ảnh có rỗng hay không
        if(itemName.isEmpty() || itemDescription.isEmpty() || imageUrl.isEmpty()) {
            //Hiển thị thông báo lỗi
            Toast.makeText(this, "Các ô nhập không được rỗng !",Toast.LENGTH_SHORT).show();
            return;
        }

        //Tạo đối tượng ContentValues để lưu thông tin items
        ContentValues values = new ContentValues();
        values.put("name",itemName);
        values.put("description",itemDescription);
        values.put("image_url",imageUrl);
        values.put("address",address);
        values.put("latitude",latitude);
        values.put("longitude",longitude);
        //Lấy thời gian hiện tại và thêm vào ContentValues
        long timestamp = System.currentTimeMillis();
        values.put("timestamp",timestamp);

        //Chèn thông tin vào cơ sở dữ liệu và ID của hàng mới
        long newRowId = db.insert("items", null, values);

        //Kiểm tra việc chèn dữ liệu có thành công hay không
        if(newRowId != -1) {
            Toast.makeText(this,"Lưu thành công", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this,"Xảy ra lỗi khị lưu", Toast.LENGTH_SHORT).show();
        }
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


