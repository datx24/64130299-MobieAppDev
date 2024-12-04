package th.nguyenxuandat.ChuyenManHinh_Fragment.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import th.nguyenxuandat.ChuyenManHinh_Fragment.R;
public class EnglishBottomFragment extends Fragment {
    private ImageButton btnPrevFragment,btnNextFragment;
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

    //Phương thức xác định vị trí fragment để chuyển trang
    private void navigateToFragment(boolean isNext) {
        if(getActivity() != null) {
            if(isNext) {
                currentFramentIndex = (currentFramentIndex + 1) % 3 ; //Tăng chỉ sổ
            } else {
                currentFramentIndex = (currentFramentIndex - 1) + 3; //Giảm chỉ số
            }

            Fragment selectedFragment; // Khai báo fragment được chọn
            switch (currentFramentIndex) {
                case 1 :
                    selectedFragment = new EnglishFragment2();
                    break;
                case 2 :
                    selectedFragment = new EnglishFragment3();
                    break;
                default :
                    selectedFragment = new EnglishFragment1();
            }

            // Tải fragment được chọn vào fragment giữa
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.englishFragmentContainerView,selectedFragment)
                    .commit(); // thay thế fragment hiện tại và xác nhận
        }
    }
}