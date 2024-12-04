package th.nguyenxuandat.ChuyenManHinh_Fragment.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import th.nguyenxuandat.ChuyenManHinh_Fragment.R;

public class ProgrammingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programming);

        TextView tipsContent = findViewById(R.id.tvTipsContent);

        // Nội dung mẹo lập trình
        String tips = "1. Chia nhỏ vấn đề: Hãy chia một vấn đề lớn thành các phần nhỏ để dễ giải quyết.\n\n" +
                "2. Đọc tài liệu: Luôn đọc và hiểu tài liệu chính thức của công nghệ bạn đang sử dụng.\n\n" +
                "3. Viết mã rõ ràng: Sử dụng tên biến, tên hàm rõ ràng và có ý nghĩa.\n\n" +
                "4. Sử dụng công cụ quản lý mã: Git là một công cụ cần thiết để quản lý mã nguồn.\n\n" +
                "5. Học hỏi từ lỗi: Kiểm tra log và lỗi thường xuyên để cải thiện kỹ năng.\n\n" +
                "6. Tận dụng cộng đồng: Tham gia các diễn đàn và cộng đồng lập trình để tìm kiếm sự hỗ trợ.\n\n" +
                "7. Luôn kiểm thử: Đảm bảo mã của bạn được kiểm thử kỹ trước khi triển khai.\n\n" +
                "8. Đọc mã của người khác: Học từ các dự án mã nguồn mở và mã của đồng nghiệp.\n\n" +
                "9. Sử dụng các mẫu thiết kế: Áp dụng các mẫu thiết kế phần mềm để tối ưu hóa cấu trúc mã.\n\n" +
                "10. Nghỉ ngơi và làm việc thông minh: Nghỉ ngơi đầy đủ giúp tăng hiệu suất làm việc.";

        // Cập nhật nội dung cho TextView
        tipsContent.setText(tips);
    }
}