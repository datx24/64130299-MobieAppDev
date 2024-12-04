package th.nguyenxuandat.ChuyenManHinh_Fragment;

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

public class EnglishFragment1 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_english1, container, false);
        // Khởi tạo các thành phần giao diện từ layout XML
        TextView tvScrambledWord = view.findViewById(R.id.tvScrambledWord); // TextView hiển thị từ bị xáo trộn
        EditText etAnswer1 = view.findViewById(R.id.etAnswer1); // EditText cho phép người dùng nhập câu trả lời
        Button btnCheck1 = view.findViewById(R.id.btnCheck1); // Nút để kiểm tra câu trả lời
        // Đặt câu đố (từ bị xáo trộn) hiển thị trên TextView
        tvScrambledWord.setText("Rearrange the letters: ETRTA"); // Câu đố: Sắp xếp lại các chữ cái thành một từ có nghĩa
        // Xử lý sự kiện khi người dùng nhấn nút "Check Answer"
        btnCheck1.setOnClickListener(v -> {
            // Lấy câu trả lời người dùng nhập vào từ EditText
            String userAnswer = etAnswer1.getText().toString().trim();
            // Đáp án đúng
            String correctAnswer = "TREAT";
            // Kiểm tra câu trả lời
            if (userAnswer.equalsIgnoreCase(correctAnswer)) {
                // Nếu đúng, hiển thị thông báo chúc mừng
                Toast.makeText(getActivity(), "Correct! Well done!", Toast.LENGTH_SHORT).show();
            } else {
                // Nếu sai, hiển thị thông báo yêu cầu thử lại
                Toast.makeText(getActivity(), "Oops! Try again.", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
