package dat.nx.myfirebase_64130299;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private TextView welcomeMessage;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        welcomeMessage = findViewById(R.id.welcomeMessage);
        // Khởi tạo FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        Button logoutButton = findViewById(R.id.logoutButton);  // Nút Đăng xuất
        String userId = getIntent().getStringExtra("USER_ID");
        if (userId != null) {
            loadUserData(userId);
        }
        // Tìm nút đăng xuất
        logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(v -> logoutUser());  // Đăng xuất khi nhấn nút
    }

    private void loadUserData(String userId) {
        mDatabase.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String email = snapshot.child("email").getValue(String.class);
                welcomeMessage.setText("Chào mừng người dùng " + email + " đến với màn hình chính !");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                welcomeMessage.setText("Failed to load user data.");
            }
        });
    }
    private void logoutUser() {
        // Đăng xuất khỏi Firebase
        mAuth.signOut();

        // Thông báo đăng xuất thành công
        Toast.makeText(HomeActivity.this, "Đăng xuất thành công!", Toast.LENGTH_SHORT).show();

        // Chuyển lại về màn hình đăng nhập (MainActivity)
        Intent intent = new Intent(HomeActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
