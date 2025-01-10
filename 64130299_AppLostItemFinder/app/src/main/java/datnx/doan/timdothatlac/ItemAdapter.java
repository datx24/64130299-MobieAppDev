package datnx.doan.timdothatlac;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private List<Item> itemList = new ArrayList<>();
    private Context context;
    private OnItemActionListener listener;

    public interface OnItemActionListener {
        void onDeleteItem(Item item, int position);
    }

    public ItemAdapter(Context context, List<Item> itemList, OnItemActionListener listener) {
        this.context = context;
        this.itemList = itemList;
        this.listener = listener;
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

        // Gán tên đồ vật vào TextView
        holder.itemName.setText(currentItem.getName());

        // Gán ảnh vào ImageView sử dụng Picasso (hoặc Glide)
        if (currentItem.getImageUrl() != null && !currentItem.getImageUrl().isEmpty()) {
            Picasso.get().load(currentItem.getImageUrl()).into(holder.itemImage);
        } else {
            // Nếu không có ảnh, có thể đặt ảnh mặc định
            holder.itemImage.setImageResource(R.drawable.ic_camera_placeholder);
        }

        // Các sự kiện click khác nếu cần
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ItemDetailActivity.class);
            intent.putExtra("id", currentItem.getId());
            intent.putExtra("name", currentItem.getName());
            intent.putExtra("image_url", currentItem.getImageUrl());
            intent.putExtra("address", currentItem.getAddress());
            intent.putExtra("latitude", currentItem.getLatitude());
            intent.putExtra("longitude", currentItem.getLongitude());
            intent.putExtra("description", currentItem.getDescription());
            context.startActivity(intent);
        });

        holder.deleteItem.setOnClickListener(view -> {
            new AlertDialog.Builder(view.getContext())
                    .setTitle("Xác nhận xóa")
                    .setMessage("Bạn có chắc chắn muốn xóa vật phẩm này không?")
                    .setPositiveButton("Có", (dialog, which) -> {
                        if (listener != null) {
                            listener.onDeleteItem(currentItem, position);
                        }
                    })
                    .setNegativeButton("Không", null)
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return itemList != null ? itemList.size() : 0;
    }
    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;
        ImageView itemImage, deleteItem;

        public ItemViewHolder(View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemName);
            itemImage = itemView.findViewById(R.id.itemImage);
            deleteItem = itemView.findViewById(R.id.deleteItem);
        }
    }

    public void updateData(List<Item> newItems) {
        this.itemList = newItems; // Cập nhật danh sách
        notifyDataSetChanged();   // Làm mới RecyclerView
    }
}


