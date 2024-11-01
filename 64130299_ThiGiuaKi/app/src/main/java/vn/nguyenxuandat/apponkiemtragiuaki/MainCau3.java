package vn.nguyenxuandat.apponkiemtragiuaki;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;

import vn.datnguyenxuanxuan.apponkiemtragiuaki.R;

public class MainCau3 extends AppCompatActivity {
    private ListView listViewSubjects; // Danh sách các môn học
    private Button fetchDataBtn; // Nút lấy dữ liệu
    private EditText searchEditText; // Ô tìm kiếm
    private ArrayList<String> subjectsList = new ArrayList<>(); // Danh sách môn học
    private ArrayAdapter<String> adapter; // Adapter cho ListView
    private JSONArray subjectsArray; // Mảng JSON chứa thông tin môn học
    private final String JSON_URL = "https://api.npoint.io/77d4b9939ead1fc946ca"; // Địa chỉ API JSON

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Đặt ngôn ngữ cho ứng dụng thành tiếng Việt
        Locale locale = new Locale("vi");
        Locale.setDefault(locale);
        Configuration config = getResources().getConfiguration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        setContentView(R.layout.activity_main_cau3);

        // Ánh xạ các view
        listViewSubjects = findViewById(R.id.listViewSubjects);
        fetchDataBtn = findViewById(R.id.fetchDatabtn);
        searchEditText = findViewById(R.id.searchEditText);

        // Thiết lập ArrayAdapter cho ListView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, subjectsList);
        listViewSubjects.setAdapter(adapter);

        // Lấy dữ liệu khi nhấn nút
        fetchDataBtn.setOnClickListener(view -> new FetchDataTask().execute(JSON_URL));

        // Thêm TextWatcher vào searchEditText để lọc theo thời gian thực
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Không cần hành động gì trước khi thay đổi văn bản
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Gọi filterSubjects mỗi khi văn bản thay đổi
                filterSubjects(s.toString().trim());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Không cần hành động gì sau khi thay đổi văn bản
            }
        });

        // Thiết lập listener cho việc nhấn item trong ListView
        listViewSubjects.setOnItemClickListener((parent, view, position, id) -> {
            try {
                JSONObject selectedSubject = subjectsArray.getJSONObject(position);
                Intent intent = new Intent(MainCau3.this, SubjectDetailActivity.class);
                intent.putExtra("subjectName", selectedSubject.getString("name"));
                intent.putExtra("subjectCode", selectedSubject.getString("code"));
                intent.putExtra("subjectCredits", selectedSubject.getInt("credits"));
                intent.putExtra("subjectSemester", selectedSubject.getInt("semester"));
                intent.putExtra("subjectYear", selectedSubject.getString("academicYear"));
                intent.putExtra("subjectType", selectedSubject.getString("knowledgeType"));
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private class FetchDataTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            StringBuilder result = new StringBuilder();
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line); // Lưu dữ liệu vào StringBuilder
                    }
                    reader.close();
                } else {
                    System.out.println("Error: " + conn.getResponseCode());
                }
                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result.toString();
        }

        @Override
        protected void onPostExecute(String jsonData) {
            try {
                JSONObject jsonObject = new JSONObject(jsonData);
                subjectsArray = jsonObject.getJSONArray("Subjects"); // Lấy mảng môn học
                subjectsList.clear(); // Xóa danh sách môn học cũ

                // Thêm từng môn học vào danh sách
                for (int i = 0; i < subjectsArray.length(); i++) {
                    JSONObject subject = subjectsArray.getJSONObject(i);
                    String subjectName = subject.getString("name");
                    subjectsList.add(subjectName);
                }

                adapter.notifyDataSetChanged(); // Cập nhật ListView
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void filterSubjects(String query) {
        ArrayList<String> filteredList = new ArrayList<>(); // Danh sách lọc
        if (query.isEmpty()) {
            // Nếu truy vấn rỗng, hiển thị tất cả môn học
            for (int i = 0; i < subjectsArray.length(); i++) {
                try {
                    JSONObject subject = subjectsArray.getJSONObject(i);
                    String subjectName = subject.getString("name");
                    filteredList.add(subjectName); // Thêm môn học vào danh sách lọc
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            for (int i = 0; i < subjectsArray.length(); i++) {
                try {
                    JSONObject subject = subjectsArray.getJSONObject(i);
                    String subjectName = subject.getString("name");

                    // Cải thiện tìm kiếm để xử lý dấu tiếng Việt
                    if (containsVietnamese(subjectName, query)) {
                        filteredList.add(subjectName);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        subjectsList.clear(); // Xóa danh sách cũ
        subjectsList.addAll(filteredList); // Thêm danh sách lọc vào
        adapter.notifyDataSetChanged(); // Cập nhật ListView
    }

    // Kiểm tra xem tên môn học có chứa truy vấn tìm kiếm (xem xét dấu tiếng Việt)
    private boolean containsVietnamese(String subjectName, String query) {
        return subjectName.toLowerCase().contains(query.toLowerCase()); // Kiểm tra không phân biệt chữ hoa chữ thường
    }
}
