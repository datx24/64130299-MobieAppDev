package datnx.doan.timdothatlac;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationRequest;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.util.Locale;

public class MapActivity extends AppCompatActivity {
    private MapView mapView;
    private Marker currentMarker; // Marker duy nhất cho vị trí hiện tại
    private FusedLocationProviderClient fusedLocationProviderClient;
    private GeoPoint currentPoint; // Vị trí hiện tại
    private TextToSpeech textToSpeech;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;


    private Handler handler = new Handler(); // Dùng Handler để tạo độ trễ 10 giây

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // Thiết lập tool bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Thiết lập nút quay lại
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);//Hiển thị nút quay lại
            getSupportActionBar().setDisplayShowTitleEnabled(true);//Hiển thị tiêu đề toolbar
        }

        // Nhận dữ liệu từ Intent
        double latitude = getIntent().getDoubleExtra("latitude", 0);
        double longitude = getIntent().getDoubleExtra("longitude", 0);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // Khởi tạo LocationRequest để yêu cầu cập nhật vị trí
        locationRequest = new LocationRequest.Builder(5000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setMinUpdateIntervalMillis(5000)
                .build();

        // Khởi tạo LocationCallback để nhận cập nhật vị trí
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                if (locationResult != null) {
                    for (Location location : locationResult.getLocations()) {
                        if (location != null) {
                            // Cập nhật vị trí mới
                            currentPoint = new GeoPoint(location.getLatitude(), location.getLongitude());

                            // Cập nhật vị trí của marker
                            updateMarkerPosition(currentPoint);
                            // Tính và phát âm khoảng cách mỗi lần vị trí cập nhật
                            calculateAndSpeakDistance(currentPoint, latitude, longitude);
                        }
                    }
                }
            }
        };

        // Kiểm tra quyền truy cập vị trí và yêu cầu cấp quyền nếu chưa có
        if (ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            startLocationUpdates();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{ACCESS_FINE_LOCATION}, 1);
        }

        // Cài đặt TextToSpeech
        textToSpeech = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                textToSpeech.setLanguage(new Locale("vi", "VN"));
            }
        });

        // Khởi tạo bản đồ OpenStreetMap
        mapView = findViewById(R.id.osmMapView);
        mapView.setTileSource(TileSourceFactory.OpenTopo);
        mapView.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.ALWAYS);
        mapView.setMultiTouchControls(true);


        // Di chuyển camera đến vị trí đồ vật
        GeoPoint itemLocation = new GeoPoint(latitude, longitude);
        IMapController mapController = mapView.getController();
        mapController.setCenter(itemLocation);
        mapController.setZoom(15.0); // Đặt mức zoom cho bản đồ khi mới vào

        // Tạo marker cho vị trí đồ vật và thêm vào bản đồ
        Marker itemMarker = new Marker(mapView);
        itemMarker.setPosition(itemLocation);
        itemMarker.setTitle("Vị trí đồ vật");
        mapView.getOverlays().add(itemMarker);
    }

    // Xử lý sự kiện khi người dùng nhấn nút quay lại trên Toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Khi nhấn nút "Quay lại", chuyển về ItemDetailActivity
                Intent returnIntent = new Intent(MapActivity.this, MainActivity.class);
                startActivity(returnIntent); // Quay lại ItemDetailActivity mà không thay đổi dữ liệu
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Phương thức tính và phát âm khoảng cách, cùng yêu cầu mở camera nếu khoảng cách nhỏ hơn 5m
    private void calculateAndSpeakDistance(GeoPoint currentLocation, double itemLatitude, double itemLongitude) {
        if (currentLocation != null) {
            // Tính khoảng cách giữa vị trí hiện tại và vị trí cần nhận diện
            float distance = calculateDistance(currentLocation, new GeoPoint(itemLatitude, itemLongitude));

            // Kiểm tra khoảng cách và phát thông báo
            if (distance < 5) {
                // Nếu khoảng cách dưới 5m, yêu cầu người dùng mở camera
                speakAndPromptForCamera();
            } else {
                // Nếu không, chỉ phát thông báo khoảng cách
                speakDistance(distance);
            }
        }
    }

    // Phương thức phát âm khoảng cách
    private void speakDistance(float distance) {
        String distanceText;
        if (distance < 1000) {
            distanceText = String.format(Locale.getDefault(), "Bạn cách vị trí đồ vật %.0f mét", distance);
        } else {
            distanceText = String.format(Locale.getDefault(), "Bạn cách vị trí đồ vật %.0f km", distance / 1000);
        }

        // Dùng Handler để tạo độ trễ 10 giây trước khi nói
        handler.postDelayed(() -> textToSpeech.speak(distanceText, TextToSpeech.QUEUE_FLUSH, null, null), 10000);
    }

    // Phương thức yêu cầu người dùng mở camera để nhận diện
    private void speakAndPromptForCamera() {
        String promptText = "Bạn đang rất gần vị trí đồ vật, hãy mở camera để tìm kiếm và nhận diện.";
        textToSpeech.speak(promptText, TextToSpeech.QUEUE_FLUSH, null, null);
    }

    // Phương thức lấy vị trí hiện tại
    private void fetchCurrentLocation() {
        if (ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            try {
                fusedLocationProviderClient.getLastLocation().addOnSuccessListener(location -> {
                    if (location != null) {
                        double lat = location.getLatitude();
                        double lon = location.getLongitude();
                        currentPoint = new GeoPoint(lat, lon);

                        // Tạo marker cho vị trí hiện tại nếu chưa có
                        if (currentMarker == null) {
                            currentMarker = new Marker(mapView);
                            currentMarker.setPosition(currentPoint);
                            currentMarker.setTitle("Vị trí của bạn");
                            mapView.getOverlays().add(currentMarker);
                        }

                        // Cập nhật lại vị trí của marker
                        updateMarkerPosition(currentPoint);

                        // Cập nhật lại bản đồ
                        mapView.invalidate();
                    } else {
                        Toast.makeText(this, "Không thể lấy vị trí hiện tại.", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (SecurityException e) {
                e.printStackTrace();
                Toast.makeText(this, "Quyền vị trí chưa được cấp.", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Yêu cầu quyền nếu chưa được cấp
            ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Quyền đã được cấp, gọi lại fetchCurrentLocation
                fetchCurrentLocation();
            } else {
                // Quyền bị từ chối
                Toast.makeText(this, "Bạn cần cấp quyền vị trí để sử dụng tính năng này.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Phương thức tính khoảng cách 2 điểm
    private float calculateDistance(GeoPoint point1, GeoPoint point2) {
        float[] result = new float[1];
        Location.distanceBetween(point1.getLatitude(), point1.getLongitude(), point2.getLatitude(),
                point2.getLongitude(), result);
        return result[0]; // khoảng cách tính bằng mét
    }

    // Phương thức bắt đầu cập nhật vị trí
    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }

    // Phương thức dừng cập nhật vị trí khi không còn cần thiết
    private void stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

    // Phương thức cập nhật vị trí của marker
    private void updateMarkerPosition(GeoPoint newLocation) {
        if (currentMarker != null) {
            currentMarker.setPosition(newLocation);
        } else {
            currentMarker = new Marker(mapView);
            currentMarker.setPosition(newLocation);
            mapView.getOverlays().add(currentMarker);
        }
        mapView.invalidate();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Bắt đầu cập nhật vị trí
        startLocationUpdates();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Dừng cập nhật vị trí
        stopLocationUpdates();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }
}
