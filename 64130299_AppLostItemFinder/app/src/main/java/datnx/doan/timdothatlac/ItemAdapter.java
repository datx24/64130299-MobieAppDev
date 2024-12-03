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
        void onEditItem(Item item);
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

        holder.itemName.setText(currentItem.getName() != null ? currentItem.getName() : "No Name");

        if (currentItem.getImageUrl() != null && !currentItem.getImageUrl().isEmpty()) {
            Picasso.get().load(currentItem.getImageUrl()).placeholder(R.drawable.ic_camera_placeholder).into(holder.itemImage);
        } else {
            holder.itemImage.setImageResource(R.drawable.ic_camera_placeholder);
        }

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

        holder.itemView.setOnClickListener(view -> goToItemDetailActivity(view, currentItem));
    }

    private void goToItemDetailActivity(View view, Item currentItem) {
        Context context = view.getContext();
        Intent intent = new Intent(context, ItemDetailActivity.class);
        intent.putExtra("name", currentItem.getName());
        intent.putExtra("imageUrl", currentItem.getImageUrl());
        context.startActivity(intent);
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
}


