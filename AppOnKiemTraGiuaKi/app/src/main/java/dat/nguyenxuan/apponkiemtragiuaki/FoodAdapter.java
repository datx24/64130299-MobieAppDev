package dat.nguyenxuan.apponkiemtragiuaki;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

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
        View view = LayoutInflater.from(context).inflate(R.layout.item_food, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        FoodItem foodItem = foodItems.get(position);
        holder.foodNameTextView.setText(foodItem.getName());
        Glide.with(context)
                .load(foodItem.getImage())
                .into(holder.foodImageView);
    }

    @Override
    public int getItemCount() {
        return foodItems.size();
    }
    // Method to update the list and notify the adapter
    public void updateList(List<FoodItem> newList) {
        this.foodItems = newList;
        notifyDataSetChanged(); // Notify adapter of data changes
    }

    static class FoodViewHolder extends RecyclerView.ViewHolder {
        TextView foodNameTextView;
        ImageView foodImageView;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            foodNameTextView = itemView.findViewById(R.id.foodNameTextView);
            foodImageView = itemView.findViewById(R.id.foodImageView);
        }
    }
}
