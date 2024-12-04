package th.nguyenxuandat.ChuyenManHinh_Fragment.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import th.nguyenxuandat.ChuyenManHinh_Fragment.R;

public class EnglishFragment3 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate layout XML và tạo View cho Fragment
        View view = inflater.inflate(R.layout.fragment_english3, container, false);

        // Khởi tạo các thành phần từ xml
        TextView tvFillBlank = view.findViewById(R.id.tvFillBlank); // Câu đố
        EditText etAnswer3 = view.findViewById(R.id.etAnswer3); // Ô nhập câu trả lời
        Button btnCheck3 = view.findViewById(R.id.btnCheck3); // Nút kiểm tra

        // Xử lý sự kiện khi nhấn nút "Check Answer"
        btnCheck3.setOnClickListener(v -> {
            String userAnswer = etAnswer3.getText().toString().trim(); // Lấy câu trả lời người dùng nhập
            String correctAnswer = "worm"; // Đáp án đúng

            // Kiểm tra câu trả lời
            if (userAnswer.equalsIgnoreCase(correctAnswer)) {
                Toast.makeText(getActivity(), "Correct! Good job!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Oops! Try again. Hint: It's an animal.", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
