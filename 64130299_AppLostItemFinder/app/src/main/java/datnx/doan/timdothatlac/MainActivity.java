package datnx.doan.timdothatlac;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ItemAdapter adapter;
    private List<Item> itemList;  // Đây là danh sách các đồ vật

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerViewItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Tạo danh sách đồ vật
        itemList = new ArrayList<>();
        // Giả sử bạn đã lấy dữ liệu từ Firestore và đưa vào itemList

        // Tạo và gán Adapter
        adapter = new ItemAdapter(itemList);
        recyclerView.setAdapter(adapter);
    }

    // Phương thức xử lý sự kiện khi nhấn nút thêm đồ vật
    public void goToAddNewItem(View view) {
        Intent intent = new Intent(MainActivity.this, AddItemActivity.class);
        startActivity(intent);
        // Chuyển đến Activity để thêm đồ vật mới
    }

    // Phương thức xử lý chỉnh sửa đồ vật
    public void editItem(View view) {
        // Chỉnh sửa thông tin đồ vật trong Firestore
    }

    // Phương thức xử lý xóa đồ vật
    public void deleteItem(View view) {
        // Xóa đồ vật khỏi Firestore và cập nhật lại RecyclerView
    }

}
