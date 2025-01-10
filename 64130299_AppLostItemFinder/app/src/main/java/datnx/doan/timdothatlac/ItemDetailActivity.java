package datnx.doan.timdothatlac;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class ItemDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_item_detail);

        //Nhận dữ liệu từ intent
        String itemName = getIntent().getStringExtra("name");
        String itemImageUrl = getIntent().getStringExtra("image_url");
        String itemAddress = getIntent().getStringExtra("address");
        double itemLatitude = getIntent().getDoubleExtra("latitude", 0.0); // Giá trị mặc định là 0.0 nếu không có dữ liệu
        double itemLongitude = getIntent().getDoubleExtra("longitude", 0.0); // Giá trị mặc định là 0.0 nếu không có dữ liệu
        String itemDescription = getIntent().getStringExtra("description"); // Nhận mô tả đồ vật từ intent

        //Hiển thị dữ liệu lên giao diện
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

    // Phương thức được gọi khi nhấn vào nút
    public void goToMapView(View view) {
        Intent intent = new Intent(ItemDetailActivity.this, MapActivity.class);

        //Gửi dữ liệu tới vị trí
        intent.putExtra("latitude",getIntent().getDoubleExtra("latitude",0));
        intent.putExtra("longitude", getIntent().getDoubleExtra("longitude",0));

        startActivity(intent);
    }
}