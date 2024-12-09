package th.nguyenxuandat.BotNav_Recy.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import th.nguyenxuandat.BotNav_Recy.R;
import th.nguyenxuandat.BotNav_Recy.adapter.TransactionAdapter;
import th.nguyenxuandat.BotNav_Recy.models.entity.Transaction;

public class TransactionFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transaction, container, false);
        // Khởi tạo RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewTransaction);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        // Dữ liệu mẫu cho Transaction
        List<Transaction> transactions = getTransactionData();
        // Gắn adapter
        TransactionAdapter adapter = new TransactionAdapter(transactions);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return view;
    }



    private List<Transaction> getTransactionData() {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(R.mipmap.ic_bill1, "Hóa đơn điện"));
        transactions.add(new Transaction(R.mipmap.ic_bill1, "Hóa đơn nước"));
        transactions.add(new Transaction(R.mipmap.ic_bill1, "Hóa đơn Internet"));
        transactions.add(new Transaction(R.mipmap.ic_bill1, "Hóa đơn Gas"));
        return transactions;
    }
}
