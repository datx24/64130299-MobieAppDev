package th.nguyenxuandat.BotNav_Recy;

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
        return view;
    }

    private List<Transaction> getTransactionData() {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(R.drawable.ic_electricity, "Hóa đơn điện"));
        transactions.add(new Transaction(R.drawable.ic_water, "Water Bill"));
        transactions.add(new Transaction(R.drawable.ic_internet, "Internet Bill"));
        transactions.add(new Transaction(R.drawable.ic_gas, "Gas Bill"));
        return transactions;
    }
}
