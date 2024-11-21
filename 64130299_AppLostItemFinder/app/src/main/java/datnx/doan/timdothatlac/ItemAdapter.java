package datnx.doan.timdothatlac;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private List<Item> itemList;
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    public ItemAdapter(List<Item> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lost_item, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item currentItem = itemList.get(position);

        // Set data cho từng item
        holder.itemName.setText(currentItem.getName());
        // Cập nhật ảnh của item nếu có
        Picasso.get().load(currentItem.getImageUrl()).into(holder.itemImage);
        // Xử lý sự kiện xóa
        holder.deleteItem.setOnClickListener(view -> {
            new AlertDialog.Builder(view.getContext())
                    .setTitle("Xác nhận xóa")
                    .setMessage("Bạn có chắc chắn muốn xóa vật phẩm này không?")
                    .setPositiveButton("Có", (dialog, which) -> deleteItemFromFirestore(currentItem, position))
                    .setNegativeButton("Không", null)
                    .show();
        });
    }

    private void deleteItemFromFirestore(Item item, int position) {
        firestore.collection("items")
                .whereEqualTo("name", item.getName()) // Điều kiện tìm kiếm
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        // Xóa từng tài liệu phù hợp
                        for (DocumentSnapshot document : queryDocumentSnapshots) {
                            document.getReference().delete()
                                    .addOnSuccessListener(aVoid -> {
                                        // Xóa item khỏi danh sách và cập nhật RecyclerView
                                        itemList.remove(position);
                                        notifyItemRemoved(position);
                                        Log.d("Firestore", "Vật phẩm đã được xóa thành công!");
                                    })
                                    .addOnFailureListener(e -> Log.e("Firestore", "Lỗi khi xóa tài liệu", e));
                        }
                    } else {
                        Log.d("Firestore", "Không tìm thấy vật phẩm để xóa.");
                    }
                })
                .addOnFailureListener(e -> Log.e("Firestore", "Lỗi khi truy vấn", e));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView itemName;
        ImageView itemImage, editItem, deleteItem;

        public ItemViewHolder(View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemName);
            itemImage = itemView.findViewById(R.id.itemImage);
            editItem = itemView.findViewById(R.id.editItem);
            deleteItem = itemView.findViewById(R.id.deleteItem);
        }
    }
}

