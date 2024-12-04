package th.nguyenxuandat.ChuyenManHinh_Fragment.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import th.nguyenxuandat.ChuyenManHinh_Fragment.R;
public class EnglishBottomFragment extends Fragment {
    private Button btnPrevFragment,btnNextFragment;
    private int currentFramentIndex = 0; //Theo dõi bị trí fragment hiện tại

    public EnglishBottomFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_english_bottom, container, false);

        //Tạo các nút từ layout
        btnPrevFragment = view.findViewById(R.id.btnPrevFragment);
        btnNextFragment = view.findViewById(R.id.btnNextFragment);

        //Thiết lập sự kiện cho nút btnNext và btnPrev
        btnPrevFragment.setOnClickListener(v -> navigateToFragment(false));
        btnNextFragment.setOnClickListener(v -> navigateToFragment(true));
        
        return view;
    }
}