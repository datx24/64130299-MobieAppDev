package datnx.doan.timdothatlac;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;

public class EditItemFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int TAKE_PHOTO_REQUEST = 2;

    private TextInputEditText editItemName, editDescriptionName;
    private ImageView editItemImageView;
    private MaterialButton btnChooseFromGallery, btnTakePhoto, btnSaveChanges;

    private Uri selectedImageUri; // Lưu trữ URI ảnh được chọn
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    private String documentId;
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
        editItemImageView = view.findViewById(R.id.editItemImageView);
        btnChooseFromGallery = view.findViewById(R.id.btnChooseFromGallery);
        btnTakePhoto = view.findViewById(R.id.btnTakePhoto);
        btnSaveChanges = view.findViewById(R.id.btnSaveChanges);

        // Hiển thị dữ liệu hiện tại
        editItemName.setText(currentName);
        editDescriptionName.setText(currentDescription);
        if (!TextUtils.isEmpty(currentImageUrl)) {
            editItemImageView.setImageURI(Uri.parse(currentImageUrl));
        }

        // Xử lý chọn ảnh từ thư viện
        btnChooseFromGallery.setOnClickListener(v -> openImageChooser());

        // Xử lý chụp ảnh mới
        btnTakePhoto.setOnClickListener(v -> openCamera());

        // Xử lý lưu thay đổi
        btnSaveChanges.setOnClickListener(v -> saveChanges());

        return view;
    }

    private void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, TAKE_PHOTO_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PICK_IMAGE_REQUEST && data != null && data.getData() != null) {
                selectedImageUri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), selectedImageUri);
                    editItemImageView.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (requestCode == TAKE_PHOTO_REQUEST && data != null && data.getExtras() != null) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                editItemImageView.setImageBitmap(bitmap);
                // Ảnh từ camera không có URI, cần xử lý riêng nếu cần upload
                selectedImageUri = null;
            }
        }
    }

    private void saveChanges() {
        String itemName = editItemName.getText().toString().trim();
        String description = editDescriptionName.getText().toString().trim();
        String imageUrl = selectedImageUri != null ? selectedImageUri.toString() : currentImageUrl;

        if (TextUtils.isEmpty(itemName)) {
            Toast.makeText(getContext(), "Tên vật phẩm không được để trống!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(description)) {
            Toast.makeText(getContext(), "Mô tả không được để trống!", Toast.LENGTH_SHORT).show();
            return;
        }

        firestore.collection("items").document(documentId)
                .update("name", itemName, "description", description, "imageUrl", imageUrl)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(getContext(), "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                    requireActivity().onBackPressed(); // Quay lại màn hình trước đó
                })
                .addOnFailureListener(e -> Toast.makeText(getContext(), "Lỗi khi cập nhật!", Toast.LENGTH_SHORT).show());
    }
}
