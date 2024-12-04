package th.nguyenxuandat.ChuyenManHinh_Fragment.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import th.nguyenxuandat.ChuyenManHinh_Fragment.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EnglishTopFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EnglishTopFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_english_top, container, false);
    }
}