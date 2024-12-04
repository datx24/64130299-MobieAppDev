package th.nguyenxuandat.ChuyenManHinh_Fragment.activity;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import th.nguyenxuandat.ChuyenManHinh_Fragment.fragment.EnglishFragment1;
import th.nguyenxuandat.ChuyenManHinh_Fragment.fragment.EnglishFragment2;
import th.nguyenxuandat.ChuyenManHinh_Fragment.fragment.EnglishFragment3;
import th.nguyenxuandat.ChuyenManHinh_Fragment.R;

public class EnglishActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_english);

        Button btnFragment1 = findViewById(R.id.btnEnglishFragment1);
        Button btnFragment2 = findViewById(R.id.btnEnglishFragment2);
        Button btnFragment3 = findViewById(R.id.btnEnglishFragment3);

        //Hiển thị frament 1 làm mặc định
        loadFragment(new EnglishFragment1());

        //Chuyển đến Frament 1
        btnFragment1.setOnClickListener(v -> loadFragment(new EnglishFragment1()));

        //Chuyển đến Fragment 2
        btnFragment2.setOnClickListener(v -> loadFragment(new EnglishFragment2()));

        //Chuyển đến Fragment 3
        btnFragment3.setOnClickListener(v -> loadFragment(new EnglishFragment3()));
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.englishFragmentContainerView, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}