package vn.nguyenxuandat.apponkiemtragiuaki;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import vn.datnguyenxuanxuan.apponkiemtragiuaki.R;

public class FoodDetailActivity extends AppCompatActivity {
    private ImageView foodImageView;
    private TextView foodNameTextView;
    private TextView foodRegionTextView;
    private TextView foodDescriptionTextView;
    private TextView foodIngredientsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        foodImageView = findViewById(R.id.foodImageView);
        foodNameTextView = findViewById(R.id.foodNameTextView);
        foodRegionTextView = findViewById(R.id.foodRegionTextView);
        foodDescriptionTextView = findViewById(R.id.foodDescriptionTextView);
        foodIngredientsTextView = findViewById(R.id.foodIngredientsTextView);

        // Nhận dữ liệu từ Intent
        FoodItem foodItem = (FoodItem) getIntent().getSerializableExtra("foodItem");
        if (foodItem != null) {
            // Cập nhật giao diện với thông tin món ăn
            foodNameTextView.setText(foodItem.getName());
            foodRegionTextView.setText(foodItem.getRegion());
            foodDescriptionTextView.setText(foodItem.getDescription());
            foodIngredientsTextView.setText(TextUtils.join(", ", foodItem.getIngredients()));

            // Tải ảnh từ URL
            Glide.with(this)
                    .load(foodItem.getImage())
                    .into(foodImageView);
        }
    }
}

