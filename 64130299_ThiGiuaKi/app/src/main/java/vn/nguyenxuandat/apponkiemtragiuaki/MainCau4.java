package vn.nguyenxuandat.apponkiemtragiuaki;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import vn.datnguyenxuanxuan.apponkiemtragiuaki.R;

public class MainCau4 extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FoodAdapter adapter;
    private List<FoodItem> foodItems; // Lưu trữ danh sách món ăn gốc
    private List<FoodItem> filteredList; // Lưu trữ danh sách món ăn đã lọc
    private EditText searchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_cau4);

        recyclerView = findViewById(R.id.foodRecyclerView);
        searchEditText = findViewById(R.id.searchEditText);

        // Thiết lập LayoutManager cho RecyclerView với 2 cột
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        // Lấy danh sách món ăn từ API
        new FetchFoodItemsTask().execute("https://api.npoint.io/1f68b285f1f38b1a34a6"); // Thay thế với URL API của bạn

        // Thêm TextWatcher để lọc danh sách món ăn theo từ khóa
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString().toLowerCase();
                filterFoodItems(query); // Lọc danh sách món ăn khi người dùng gõ
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private class FetchFoodItemsTask extends AsyncTask<String, Void, List<FoodItem>> {
        @Override
        protected List<FoodItem> doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStreamReader in = new InputStreamReader(urlConnection.getInputStream());
                Gson gson = new Gson();
                ApiResponse response = gson.fromJson(in, ApiResponse.class);
                return response.getVietnameseDelicacies();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<FoodItem> foodItems) {
            if (foodItems != null) {
                MainCau4.this.foodItems = foodItems; // Lưu danh sách món ăn gốc
                adapter = new FoodAdapter(foodItems, MainCau4.this);
                recyclerView.setAdapter(adapter);
            }
        }
    }

    private void filterFoodItems(String query) {
        filteredList = new ArrayList<>();

        // Kiểm tra nếu truy vấn rỗng
        if (query.isEmpty()) {
            filteredList.addAll(foodItems); // Hiện tất cả món ăn nếu truy vấn rỗng
        } else {
            // Lọc món ăn dựa trên tên
            for (FoodItem item : foodItems) {
                if (item.getName().toLowerCase().contains(query)) {
                    filteredList.add(item);
                }
            }
        }
        adapter.updateList(filteredList); // Cập nhật danh sách món ăn đã lọc
    }
}
