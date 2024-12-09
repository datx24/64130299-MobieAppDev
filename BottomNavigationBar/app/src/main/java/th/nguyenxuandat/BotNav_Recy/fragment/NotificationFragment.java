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
import java.util.Arrays;
import java.util.List;

import th.nguyenxuandat.BotNav_Recy.R;
import th.nguyenxuandat.BotNav_Recy.adapter.CardAdapter;

public class NotificationFragment extends Fragment {
    private RecyclerView recyclerView;
    private CardAdapter adapter;
    private LinearLayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        List<String> cardTitles = Arrays.asList("Thông báo 1", "Thông báo 2", "Thông báo 3");

        recyclerView = view.findViewById(R.id.recyclerNotificationView);
        layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CardAdapter(cardTitles);
        recyclerView.setAdapter(adapter);

        view.findViewById(R.id.btn1).setOnClickListener(v -> scrollToPosition(0));
        view.findViewById(R.id.btn2).setOnClickListener(v -> scrollToPosition(1));
        view.findViewById(R.id.btn3).setOnClickListener(v -> scrollToPosition(2));

        return view;
    }

    private void scrollToPosition(int position) {
        recyclerView.smoothScrollToPosition(position);
    }
}

