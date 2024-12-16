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

        // Find the views
        toolbarTitle = findViewById(R.id.toolbar_title);
        searchView = findViewById(R.id.searchView);

        // Set up the SearchView to expand when clicked
        searchView.setOnSearchClickListener(v -> {
            // Hide title when the search icon is clicked
            toolbarTitle.setVisibility(View.GONE);
            searchView.setVisibility(View.VISIBLE);  // Ensure SearchView is visible
        });

        // Set up the SearchView listener to collapse and show title when search is closed
        searchView.setOnCloseListener(() -> {
            toolbarTitle.setVisibility(View.VISIBLE);  // Show title when search is closed
            return false;
        });

        //Tạo RecycleView và thiết lập LayoutManager
        recyclerView = findViewById(R.id.recyclerViewItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Khởi tạo databaseHelper để tương tác với cơ sở dữ liệu
        dbHelper = new DatabaseHelper(this);

        //Lấy toàn bộ item từ cơ sở dữ liệu
        itemList = dbHelper.getAllItems();

        //Tạo và gán Adapter cho RecyclerView
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

    //Phương thức chuyển sang màn hình thêm đồ vật
    public void goToAddNewItem(View view) {
        Intent intent = new Intent(MainActivity.this, AddItemActivity.class);
        startActivity(intent);
        // Chuyển đến Activity để thêm đồ vật mới
    }

    

}
