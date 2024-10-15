package dat.nguyenxuan.DanhSachBaiHat;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

import dat.nguyenxuan.viduhello.R;

public class MainDanhSachBaiHat extends AppCompatActivity {
    // Nguồn dữ liwwuj
    ArrayList<String> dsBaiHat;
    ArrayAdapter<String> adapterBaiHat;

    ListView lvBaiHat;
    Button btnthemBaiHat;
    EditText editTextTenBaiHat;
    void getControl() {
        lvBaiHat = findViewById(R.id.lvBH);
        btnthemBaiHat = findViewById(R.id.buttonThem);
        editTextTenBaiHat = findViewById(R.id.editTenBaiHat);
    }

    ArrayList<String> getData()
    {
        ArrayList<String> lstTenBaiHat = new ArrayList<String>();
        lstTenBaiHat.add("Cơn mưa ngang qua.");
        lstTenBaiHat.add("Nắng ấm xa dần");
        lstTenBaiHat.add("Chúng ta không thuộc về nhau");
        lstTenBaiHat.add("Đừng làm trái tim anh đau");
        return lstTenBaiHat;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_danh_sach_bai_hat);
        getControl();
        dsBaiHat = getData();
        ArrayAdapter<String> adapterBaiHat;
        adapterBaiHat = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,dsBaiHat);
        lvBaiHat.setAdapter(adapterBaiHat);

        lvBaiHat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                XuLyKhiNhanVaoViTriThu(position);
            }

        });
        btnthemBaiHat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Lấy tên bài hát
                String tenBaiHat = editTextTenBaiHat.getText().toString();
                //Thêm vào nguồn
                dsBaiHat.add(tenBaiHat);
                //Báo cho adapter cập nhật lại view
                adapterBaiHat.notifyDataSetChanged();
            }
        });
    }//end create
    void XuLyKhiNhanVaoViTriThu(int pos) {
        // Lấy phần tử
        String item = dsBaiHat.get(pos);
        Toast.makeText(getApplicationContext(), item, Toast.LENGTH_SHORT).show();
    }
}