package vn.nguyenxuandat.apponkiemtragiuaki;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import vn.datnguyenxuanxuan.apponkiemtragiuaki.R;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    private List<FoodItem> foodItems;
    private Context context;

    public FoodAdapter(List<FoodItem> foodItems, Context context) {
        this.foodItems = foodItems;
        this.context = context;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        FoodItem foodItem = foodItems.get(position);
        holder.foodName.setText(foodItem.getName()); // Đặt tên món ăn vào TextView

        // Tải ảnh vào ImageView
        Glide.with(context)
                .load(foodItem.getImage())
                .into(holder.foodImage);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, FoodDetailActivity.class);
            intent.putExtra("foodItem", foodItem); // Gửi FoodItem đến Activity chi tiết
            context.startActivity(intent);
        });
    }



    @Override
    public int getItemCount() {
        return foodItems.size();
    }

    public void updateList(List<FoodItem> filteredList) {
        this.foodItems = filteredList;
        notifyDataSetChanged(); // Cập nhật RecyclerView
    }

    static class FoodViewHolder extends RecyclerView.ViewHolder {
        TextView foodName;
        ImageView foodImage;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            foodName = itemView.findViewById(R.id.foodNameTextView);
            foodImage = itemView.findViewById(R.id.foodImageView);
        }
    }
}
