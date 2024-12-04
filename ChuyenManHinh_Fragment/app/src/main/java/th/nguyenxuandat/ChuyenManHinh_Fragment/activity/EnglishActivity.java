package th.nguyenxuandat.ChuyenManHinh_Fragment.activity;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import th.nguyenxuandat.ChuyenManHinh_Fragment.fragment.EnglishBottomFragment;
import th.nguyenxuandat.ChuyenManHinh_Fragment.fragment.EnglishFragment1;
import th.nguyenxuandat.ChuyenManHinh_Fragment.fragment.EnglishFragment2;
import th.nguyenxuandat.ChuyenManHinh_Fragment.fragment.EnglishFragment3;
import th.nguyenxuandat.ChuyenManHinh_Fragment.R;
import th.nguyenxuandat.ChuyenManHinh_Fragment.fragment.EnglishTopFragment;

public class EnglishActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_english);

        // Hiển thị Top và Bottom Fragments
        loadTopAndBottomFragments();

        //Hiển thị frament 1 làm mặc định
        loadFragment(new EnglishFragment1());
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.englishFragmentContainerView, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void loadTopAndBottomFragments() {
        // Load Top Fragment
        FragmentTransaction topTransaction = getSupportFragmentManager().beginTransaction();
        topTransaction.replace(R.id.topEnglishFragment, new EnglishTopFragment());
        topTransaction.commit();

        // Load Bottom Fragment
        FragmentTransaction bottomTransaction = getSupportFragmentManager().beginTransaction();
        bottomTransaction.replace(R.id.bottomEnglishFragment, new EnglishBottomFragment());
        bottomTransaction.commit();
    }
}