package dat.nx.myfirebase_64130299;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private TextView welcomeMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        welcomeMessage = findViewById(R.id.welcomeMessage);

        String userId = getIntent().getStringExtra("USER_ID");
        if (userId != null) {
            loadUserData(userId);
        }
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


}
