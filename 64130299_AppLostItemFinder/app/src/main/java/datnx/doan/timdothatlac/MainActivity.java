package datnx.doan.timdothatlac;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView; //khai báo RecycleView hiển thị danh sách đồ vật
    private ItemAdapter adapter;
    private List<Item> itemList;
    private DatabaseHelper dbHelper; // khai báo đối tượng để tương tác với cơ sở dữ liệu

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
