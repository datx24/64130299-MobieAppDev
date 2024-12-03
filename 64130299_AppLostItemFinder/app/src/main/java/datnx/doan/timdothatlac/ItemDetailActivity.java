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

        //Hiển thị dữ liệu lên giao diện
        ImageView imageView = findViewById(R.id.imgItem);
        TextView nameTextView = findViewById(R.id.tvItemName);

        nameTextView.setText(itemName);
        Picasso.get().load(itemImageUrl).into(imageView);
    }

    // Phương thức được gọi khi nhấn vào nút
    public void goToMapView(View view) {
        Intent intent = new Intent(ItemDetailActivity.this, MapActivity.class);
        startActivity(intent);
    }
}