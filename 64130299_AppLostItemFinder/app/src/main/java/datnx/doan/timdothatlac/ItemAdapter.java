package datnx.doan.timdothatlac;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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

    private List<Item> itemList;
    private Context context;
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;

    public ItemAdapter(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);//Khởi tạo database
        db = dbHelper.getWritableDatabase();//Lấy cơ sở dữ liệu để ghi
        this.itemList = dbHelper.getAllItems();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Tạo ViewHolder cho từng item trong RecyclerView
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
                    .setPositiveButton("Có", (dialog, which) -> deleteItemFromDatabase(currentItem, position))
                    .setNegativeButton("Không", null)
                    .show();
        });
        //Xử lý sự kiện bấm vào item để chuyển đến màn hình chi tiết
        holder.itemView.setOnClickListener(view -> {
            goToItemDetailActivity(view,currentItem);
        });
    }

    private void goToItemDetailActivity(View view, Item currentItem) {
        Context context = view.getContext();
        Intent intent = new Intent(context, ItemDetailActivity.class);

        //Truyền thông tin vật phẩm vào intent
        intent.putExtra("name",currentItem.getName());
        intent.putExtra("imageUrl",currentItem.getImageUrl());

        //Khởi động acticity
        context.startActivity(intent);
    }

    private void deleteItemFromDatabase(Item item, int position) {
        //Xác định item cần xóa
        String selection = DatabaseHelper.COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(item.getId())};
        //Thực hiện xóa item ra khỏi cơ sở dữ liệu
        int deleteRow = db.delete(DatabaseHelper.TABLE_NAME,selection, selectionArgs);
        if(deleteRow > 0) {
            //Xóa thành công sẽ cập nhật lại danh sách
            itemList.remove(position);
            notifyItemRemoved(position);
        }
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

