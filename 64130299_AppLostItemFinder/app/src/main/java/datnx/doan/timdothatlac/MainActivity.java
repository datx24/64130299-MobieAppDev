package datnx.doan.timdothatlac;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ItemAdapter adapter;
    private List<Item> itemList;  // Đây là danh sách các đồ vật
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerViewItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        firestore = FirebaseFirestore.getInstance();
        
        // Tạo danh sách đồ vật
        itemList = new ArrayList<>();
        // Giả sử bạn đã lấy dữ liệu từ Firestore và đưa vào itemList

        // Tạo và gán Adapter
        adapter = new ItemAdapter(itemList);
        recyclerView.setAdapter(adapter);

        // Lấy dữ liệu từ Firestore
        loadItems();
    }

    private void loadItems() {
        // Lấy tham chiếu tới collection 'items' trong Firestore
        CollectionReference itemsRef = firestore.collection("items");

        // Thực hiện truy vấn để lấy dữ liệu
        itemsRef.get().addOnSuccessListener(queryDocumentSnapshots -> {
            if (!queryDocumentSnapshots.isEmpty()) {
                // Xóa tất cả dữ liệu cũ trong danh sách item
                itemList.clear();

                // Duyệt qua các document trong collection
                for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                    // Lấy đối tượng Item từ Firestore
                    Item item = document.toObject(Item.class);
                    if (item != null) {
                        // Thêm item vào danh sách
                        itemList.add(item);
                    }
                }

                // Thông báo cho adapter để cập nhật dữ liệu
                adapter.notifyDataSetChanged();
            } else {
                // Nếu không có dữ liệu nào trong Firestore
                Toast.makeText(MainActivity.this, "Không có dữ liệu trong firestore", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e -> {
            // Xử lý lỗi khi truy vấn không thành công
            Toast.makeText(MainActivity.this, "Lỗi truy vấn dữ liệu", Toast.LENGTH_SHORT).show();
        });
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
