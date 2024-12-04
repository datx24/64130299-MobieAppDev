package th.nguyenxuandat.ChuyenManHinh_Fragment.activity;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import th.nguyenxuandat.ChuyenManHinh_Fragment.fragment.MathFragment1;
import th.nguyenxuandat.ChuyenManHinh_Fragment.fragment.MathFragment2;
import th.nguyenxuandat.ChuyenManHinh_Fragment.fragment.MathFragment3;
import th.nguyenxuandat.ChuyenManHinh_Fragment.R;

public class MathActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math);

        Button btnFragment1 = findViewById(R.id.btnMathFragment1);
        Button btnFragment2 = findViewById(R.id.btnMathFragment2);
        Button btnFragment3 = findViewById(R.id.btnMathFragment3);

        //Hiển thị frament 1 làm mặc định
        loadFragment(new MathFragment1());

        //Chuyển đến Frament 1
        btnFragment1.setOnClickListener(v -> loadFragment(new MathFragment1()));
        
        //Chuyển đến Fragment 2
        btnFragment2.setOnClickListener(v -> loadFragment(new MathFragment2()));

        //Chuyển đến Fragment 3
        btnFragment3.setOnClickListener(v -> loadFragment(new MathFragment3()));
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mathFragmentContainerView, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}