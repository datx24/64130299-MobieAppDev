package th.nguyenxuandat.BotNav_Recy.adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import th.nguyenxuandat.BotNav_Recy.R;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

    private final List<String> cardTitles;

    public CardAdapter(List<String> cardTitles) {
        this.cardTitles = cardTitles;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_notification, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        holder.tvCardTitle.setText(cardTitles.get(position));
    }

    @Override
    public int getItemCount() {
        return cardTitles.size();
    }

    static class CardViewHolder extends RecyclerView.ViewHolder {
        TextView tvCardTitle;
        CardView cardView;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCardTitle = itemView.findViewById(R.id.tvCardTitle);
            cardView = (CardView) itemView;
        }
    }
}
