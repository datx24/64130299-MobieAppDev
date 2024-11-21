package datnx.doan.timdothatlac;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.FirebaseFirestore;

public class EditItemFragment extends Fragment {

    private EditText editItemName, editDescriptionName, editItemImageUrl;
    private Button btnSaveChanges;
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    private String documentId; // ID của tài liệu Firestore
    private String currentName, currentDescription, currentImageUrl;

    public EditItemFragment(String documentId, String currentName, String currentDescription, String currentImageUrl) {
        this.documentId = documentId;
        this.currentName = currentName;
        this.currentDescription = currentDescription;
        this.currentImageUrl = currentImageUrl;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_item, container, false);

        // Liên kết các view
        editItemName = view.findViewById(R.id.editItemName);
        editDescriptionName = view.findViewById(R.id.editDescriptionName);
        editItemImageUrl = view.findViewById(R.id.editItemImageView);
        btnSaveChanges = view.findViewById(R.id.btnSaveChanges);

        // Hiển thị dữ liệu hiện tại
        editItemName.setText(currentName);
        editDescriptionName.setText(currentDescription);
        editItemImageUrl.setText(currentImageUrl);

        // Xử lý sự kiện khi bấm nút "Lưu thay đổi"
        btnSaveChanges.setOnClickListener(v -> saveChanges());

        return view;
    }

    private void saveChanges() {
        String itemName = editItemName.getText().toString().trim();
        String description = editDescriptionName.getText().toString().trim();
        String imageUrl = editItemImageUrl.getText().toString().trim();

        // Kiểm tra dữ liệu đầu vào
        if (TextUtils.isEmpty(itemName)) {
            Toast.makeText(getContext(), "Tên vật phẩm không được để trống!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(description)) {
            Toast.makeText(getContext(), "Mô tả không được để trống!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(imageUrl)) {
            Toast.makeText(getContext(), "URL ảnh không được để trống!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Cập nhật dữ liệu trong Firestore
        firestore.collection("items").document(documentId)
                .update("name", itemName, "description", description, "imageUrl", imageUrl)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(getContext(), "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                    requireActivity().onBackPressed(); // Quay lại màn hình trước đó
                })
                .addOnFailureListener(e -> Toast.makeText(getContext(), "Lỗi khi cập nhật!", Toast.LENGTH_SHORT).show());
    }
}
