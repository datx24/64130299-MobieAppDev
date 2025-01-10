package datnx.doan.timdothatlac;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView; //khai báo RecycleView hiển thị danh sách đồ vật
    ItemAdapter adapter;
    List<Item> itemList;
    DatabaseHelper dbHelper; // khai báo đối tượng để tương tác với cơ sở dữ liệu
    private TextView toolbarTitle;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchView = findViewById(R.id.search_view);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchItems(query); // Gọi phương thức tìm kiếm khi nhấn Enter
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchItems(newText); // Gọi phương thức tìm kiếm khi thay đổi từ khóa
                return true;
            }
        });

        recyclerView = findViewById(R.id.recyclerViewItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new DatabaseHelper(this);

        itemList = dbHelper.getAllItems();

        adapter = new ItemAdapter(this,itemList, new ItemAdapter.OnItemActionListener() {
            @Override
            public void onDeleteItem(Item item, int position) {

            }

            @Override
            public void onEditItem(Item item) {

            }
        });
        recyclerView.setAdapter(adapter);

        //Kiểm tra xem danh sách có rỗng không và hiển thị thông báo nếu rỗng
        if(itemList.isEmpty()) {
            Toast.makeText(this,"Không có danh sách đồ vật trong cơ sở dữ liệu !",Toast.LENGTH_SHORT).show();
        }
    }

    private void searchItems(String query) {
        List<Item> filteredList = dbHelper.searchItemsByName(query);

        if (filteredList.isEmpty()) {
            Toast.makeText(this, "Không tìm thấy đồ vật phù hợp!", Toast.LENGTH_SHORT).show();
        }

        // Cập nhật RecyclerView với danh sách mới
        adapter.updateData(filteredList);
    }

    //Phương thức chuyển sang màn hình thêm đồ vật
    public void goToAddNewItem(View view) {
        Intent intent = new Intent(MainActivity.this, AddItemActivity.class);
        startActivity(intent);
        // Chuyển đến Activity để thêm đồ vật mới
    }
}
