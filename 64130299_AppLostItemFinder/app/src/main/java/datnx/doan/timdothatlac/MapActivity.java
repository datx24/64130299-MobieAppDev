package datnx.doan.timdothatlac;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import android.content.pm.PackageManager;
import android.os.Bundle;

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

public class MapActivity extends AppCompatActivity {
    private MapView mapView;
    private double latitude;
    private double longitude;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private GeoPoint currentPoint; //vị trí hiện tại
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        //Yêu cầu quyền truy cập vị trí nếu chưa cấp
        if(ContextCompat.checkSelfPermission(this,ACCESS_FINE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION},1);
        }else{
            //Lấy vị trí hiện tại
            fetchCurrentLocation();
        }

        //Nhận dữ liệu từ Intent
        latitude = getIntent().getDoubleExtra("latitude",0);
        longitude = getIntent().getDoubleExtra("longitude",0);

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
    }
}