package datnx.doan.timdothatlac;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.squareup.picasso.Picasso;

import android.location.LocationManager;
import android.provider.Settings;
import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

public class ItemDetailActivity extends AppCompatActivity {
    private FusedLocationProviderClient fusedLocationClient;
    private DatabaseHelper dbHelper;
    private TextView tvLatitude, tvLongitude;
    private int itemId;  // Biến lưu trữ ID món đồ

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_item_detail);

        // Nhận dữ liệu từ intent
        itemId = getIntent().getIntExtra("id", -1);
        String itemName = getIntent().getStringExtra("name");
        String itemImageUrl = getIntent().getStringExtra("image_url");
        String itemAddress = getIntent().getStringExtra("address");
        double itemLatitude = getIntent().getDoubleExtra("latitude", 0.0); // Giá trị mặc định là 0.0 nếu không có dữ liệu
        double itemLongitude = getIntent().getDoubleExtra("longitude", 0.0); // Giá trị mặc định là 0.0 nếu không có dữ liệu
        String itemDescription = getIntent().getStringExtra("description"); // Nhận mô tả đồ vật từ intent

        // Khởi tạo các đối tượng
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        dbHelper = new DatabaseHelper(this);  // Khởi tạo DatabaseHelper

        // Ánh xạ các View
        tvLatitude = findViewById(R.id.tvLatitude);
        tvLongitude = findViewById(R.id.tvLongitude);

        Button btnUpdateLocation = findViewById(R.id.btnUpdateLocation);
        btnUpdateLocation.setOnClickListener(v -> updateLocation());

        Button btnSelectGps = findViewById(R.id.btnSelectGPS);  // Ánh xạ nút chọn GPS
        btnSelectGps.setOnClickListener(v -> selectGps());

        // Hiển thị dữ liệu lên giao diện
        ImageView imageView = findViewById(R.id.imgItem);
        TextView nameTextView = findViewById(R.id.tvItemName);
        TextView addressTextView = findViewById(R.id.tvAddress);
        TextView latitudeTextView = findViewById(R.id.tvLatitude);
        TextView longitudeTextView = findViewById(R.id.tvLongitude);
        TextView tvDescription = findViewById(R.id.tvDescription);

        nameTextView.setText(itemName);
        addressTextView.setText("Address: " + itemAddress);
        latitudeTextView.setText("Latitude: " + itemLatitude);
        longitudeTextView.setText("Longitude: " + itemLongitude);
        tvDescription.setText("Mô tả: " + itemDescription);
        Picasso.get().load(itemImageUrl).into(imageView);
    }

    // Cập nhật vị trí mới
    private void updateLocation() {
        // Kiểm tra lại quyền truy cập vị trí trước khi sử dụng
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Kiểm tra xem vị trí có được trả về không
                            if (location != null) {
                                double latitude = location.getLatitude();
                                double longitude = location.getLongitude();

                                // Cập nhật UI với vị trí mới
                                tvLatitude.setText("Latitude: " + latitude);
                                tvLongitude.setText("Longitude: " + longitude);

                                // Cập nhật vào cơ sở dữ liệu
                                dbHelper.updateItemLocation(itemId, latitude, longitude);
                            }
                        }
                    });
        } else {
            Toast.makeText(this, "Permission denied. Cannot access location.", Toast.LENGTH_SHORT).show();
        }
    }

    // Phương thức để hiển thị danh sách GPS hiện đang bật
    private void selectGps() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (locationManager != null) {
            // Lấy danh sách các nhà cung cấp GPS hiện có
            List<String> providers = locationManager.getAllProviders();

            // Tạo dialog hoặc một activity mới để hiển thị các GPS
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Select GPS Provider");

            // Lọc danh sách các nhà cung cấp GPS đang bật
            final List<String> enabledProviders = new ArrayList<>();
            for (String provider : providers) {
                if (locationManager.isProviderEnabled(provider)) {
                    enabledProviders.add(provider);
                }
            }

            if (enabledProviders.isEmpty()) {
                // Nếu không có nhà cung cấp GPS nào được bật
                Toast.makeText(this, "No GPS providers are enabled.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Hiển thị danh sách các nhà cung cấp GPS đang bật
            builder.setItems(enabledProviders.toArray(new String[0]), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Khi người dùng chọn nhà cung cấp GPS
                    String selectedProvider = enabledProviders.get(which);

                    // Tiến hành xử lý với GPS đã chọn
                    // Ví dụ: Cập nhật thông tin vị trí hoặc mở thêm tính năng khác
                    handleGpsSelection(selectedProvider);
                }
            });

            builder.show();
        }
    }

    // Phương thức xử lý khi người dùng chọn một nhà cung cấp GPS
    private void handleGpsSelection(String selectedProvider) {
        // Bạn có thể thêm logic để xử lý việc chọn nhà cung cấp GPS ở đây
        // Ví dụ: Chuyển sang một hoạt động khác, hoặc sử dụng nhà cung cấp GPS đã chọn
        if (selectedProvider.equals(LocationManager.GPS_PROVIDER)) {
            // Nếu người dùng chọn GPS_PROVIDER, có thể hiển thị một màn hình với bản đồ hoặc thông tin chi tiết về vị trí
            // Ví dụ: Mở MapActivity hoặc cập nhật vị trí với GPS
            Intent intent = new Intent(ItemDetailActivity.this, MapActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(ItemDetailActivity.this, MapActivity.class);
            startActivity(intent);
        }
    }

    // Phương thức được gọi khi nhấn vào nút
    public void goToMapView(View view) {
        Intent intent = new Intent(ItemDetailActivity.this, MapActivity.class);

        // Gửi dữ liệu tới vị trí
        intent.putExtra("latitude", getIntent().getDoubleExtra("latitude", 0));
        intent.putExtra("longitude", getIntent().getDoubleExtra("longitude", 0));

        startActivity(intent);
    }
}

