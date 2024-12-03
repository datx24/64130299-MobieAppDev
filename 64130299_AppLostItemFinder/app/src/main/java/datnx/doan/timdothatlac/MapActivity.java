package datnx.doan.timdothatlac;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.util.Locale;

public class MapActivity extends AppCompatActivity {
    private MapView mapView;
    private double latitude;
    private double longitude;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private GeoPoint currentPoint; //vị trí hiện tại
    private TextToSpeech textToSpeech;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        //Nhận dữ liệu từ Intent
        latitude = getIntent().getDoubleExtra("latitude",0);
        longitude = getIntent().getDoubleExtra("longitude",0);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        //Yêu cầu quyền truy cập vị trí nếu chưa cấp
        if(ContextCompat.checkSelfPermission(this,ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION},1);
        }else{
            //Lấy vị trí hiện tại
            fetchCurrentLocation();
        }

        textToSpeech = new TextToSpeech(this,status -> {
            if(status == TextToSpeech.SUCCESS) {
                textToSpeech.setLanguage(new Locale("vi","VN"));//Cài tiếng việt
            }
        });

        //Khởi tạo bản đồ
        mapView = findViewById(R.id.osmMapView);
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.ALWAYS);
        mapView.setMultiTouchControls(true);

        //Tùy chọn ẩn điều khển zoom sau 1 thòi gian không tương tác
        mapView.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.ALWAYS);

        //Di chuyển camera đến vị trí đồ vật
        GeoPoint itemLocation = new GeoPoint(latitude,longitude);
        IMapController mapController = mapView.getController();
        mapController.setCenter(itemLocation);
        mapController.setZoom(15.0);

        //Tạo marker và thêm vào bản đồ
        Marker marker = new Marker(mapView);
        marker.setPosition(itemLocation);
        marker.setTitle("Vị trí đồ vật");
        mapView.getOverlays().add(marker);

        //Đọc khoảng cách sau khi tính
        mapView.postDelayed(() -> {
            if(currentPoint != null) {
                float distance = calculateDistance(currentPoint,itemLocation);
                speakDistance(distance);
            }
        },1000);
    }

    //Phương thức lấy vị trí hiện tại
    private void fetchCurrentLocation() {
        if (ContextCompat.checkSelfPermission(this,ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            try {
                fusedLocationProviderClient.getLastLocation().addOnSuccessListener(location -> {
                    if (location != null) {
                        double lat = location.getLatitude();
                        double lon = location.getLongitude();
                        currentPoint = new GeoPoint(lat, lon);

                        // Thêm marker cho vị trí hiện tại
                        Marker currentMarker = new Marker(mapView);
                        currentMarker.setPosition(currentPoint);
                        currentMarker.setTitle("Vị trí của bạn");
                        mapView.getOverlays().add(currentMarker);

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

    //Phương thức tính khoảng cách 2 điểm
    private float calculateDistance(GeoPoint point1,GeoPoint point2) {
        float[] result = new float[1];
        Location.distanceBetween(point1.getLatitude(),point1.getLongitude(),point2.getLatitude(),
                                    point2.getLongitude(),result);
        return result[0];//khoảng cách tính bằng mét
    }
}