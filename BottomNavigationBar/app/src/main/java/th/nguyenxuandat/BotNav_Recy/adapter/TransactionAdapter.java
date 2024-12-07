package th.nguyenxuandat.BotNav_Recy.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import th.nguyenxuandat.BotNav_Recy.R;
import th.nguyenxuandat.BotNav_Recy.models.entity.Transaction;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {

    private List<Transaction> transactions;

    public TransactionAdapter(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.transaction_item, parent, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        Transaction transaction = transactions.get(position);
        holder.imgTransaction.setImageResource(transaction.getImage());
        holder.tvTransaction.setText(transaction.getName());
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public static class TransactionViewHolder extends RecyclerView.ViewHolder {
        ImageView imgTransaction;
        TextView tvTransaction;

        public TransactionViewHolder(View view) {
            super(view);
            imgTransaction = view.findViewById(R.id.imgTransaction);
            tvTransaction = view.findViewById(R.id.tvTransaction);
        }
    }
}
