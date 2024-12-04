package th.nguyenxuandat.ChuyenManHinh_Fragment.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import th.nguyenxuandat.ChuyenManHinh_Fragment.R;
public class MathBottomFragment extends Fragment {
    Button btnMathFragment1,btnMathFragment2,btnMathFragment3;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_math_bottom, container, false);

        // Lấy tham chiếu các nút bấm
        btnMathFragment1 = view.findViewById(R.id.btnMathFragment1);
        btnMathFragment2 = view.findViewById(R.id.btnMathFragment2);
        btnMathFragment3 = view.findViewById(R.id.btnMathFragment3);

        // Gán sự kiện bấm nút
        btnMathFragment1.setOnClickListener(v -> replaceFragment(new MathFragment1()));
        btnMathFragment2.setOnClickListener(v -> replaceFragment(new MathFragment2()));
        btnMathFragment3.setOnClickListener(v -> replaceFragment(new MathFragment3()));

        return view;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mathFragmentContainerView, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}