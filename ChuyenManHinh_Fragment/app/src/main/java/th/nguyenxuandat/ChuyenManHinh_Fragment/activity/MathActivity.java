package th.nguyenxuandat.ChuyenManHinh_Fragment.activity;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import th.nguyenxuandat.ChuyenManHinh_Fragment.fragment.MathBottomFragment;
import th.nguyenxuandat.ChuyenManHinh_Fragment.fragment.MathFragment1;
import th.nguyenxuandat.ChuyenManHinh_Fragment.fragment.MathFragment2;
import th.nguyenxuandat.ChuyenManHinh_Fragment.fragment.MathFragment3;
import th.nguyenxuandat.ChuyenManHinh_Fragment.R;

public class MathActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math);


        //Hiển thị frament 1 làm mặc định
        loadFragment(new MathFragment1());
        loadBottomFramment();
    }

    private void loadBottomFramment() {
        // Load Bottom Fragment
        FragmentTransaction bottomTransaction = getSupportFragmentManager().beginTransaction();
        bottomTransaction.replace(R.id.bottomMathFragment, new MathBottomFragment());
        bottomTransaction.commit();
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mathFragmentContainerView, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}