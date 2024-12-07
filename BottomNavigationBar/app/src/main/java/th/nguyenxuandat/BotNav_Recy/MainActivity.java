package th.nguyenxuandat.BotNav_Recy;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);

        //Thiết lập listener cho bottomNavigationView
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_home) {
                loadFragment(new HomeFragment());
            } else if (item.getItemId() == R.id.nav_transaction) {
                loadFragment(new TransactionFragment());
            } else if (item.getItemId() == R.id.nav_notification) {
                loadFragment(new NotificationFragment());
            } else if (item.getItemId() == R.id.nav_setting) {
                loadFragment(new SettingFragment());
            }
            return true;
        });

        //Load fragment mặc định khi lần đầu mở màn hình
        loadFragment(new HomeFragment());
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
    }
}