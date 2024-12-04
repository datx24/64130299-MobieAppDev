package th.nguyenxuandat.ChuyenManHinh_Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class EnglishFragment2 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate layout XML và tạo View cho Fragment
        View view = inflater.inflate(R.layout.fragment_english2, container, false);

        // Khởi tạo các thành phần từ xml
        ImageView ivIdiom = view.findViewById(R.id.ivIdiom);
        EditText etAnswer2 = view.findViewById(R.id.etAnswer2);
        Button btnCheck2 = view.findViewById(R.id.btnCheck2);

        // Đặt hình ảnh
        ivIdiom.setImageResource(R.drawable.cat_in_bag);

        // Xử lý sự kiện khi nhấn nút "Check Answer"
        btnCheck2.setOnClickListener(v -> {
            String userAnswer = etAnswer2.getText().toString().trim(); // Lấy câu trả lời người dùng nhập
            String correctAnswer = "Let the cat out of the bag"; // Đáp án đúng

            // Kiểm tra câu trả lời
            if (userAnswer.equalsIgnoreCase(correctAnswer)) {
                Toast.makeText(getActivity(), "Correct! The idiom means revealing a secret.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Oops! Try again. Hint: It involves a cat and a secret.", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}