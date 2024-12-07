package th.nguyenxuandat.BotNav_Recy.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import th.nguyenxuandat.BotNav_Recy.R;
import th.nguyenxuandat.BotNav_Recy.adapter.NotificationAdapter;
import th.nguyenxuandat.BotNav_Recy.models.entity.Notification;

public class NotificationFragment extends Fragment {

    private RecyclerView recyclerView;
    private Button btnPrevious, btnNext;
    private NotificationAdapter adapter;
    private List<Notification> notificationList;
    private int currentPosition = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        recyclerView = view.findViewById(R.id.recyclerNotification);
        btnPrevious = view.findViewById(R.id.btnPrevious);
        btnNext = view.findViewById(R.id.btnNext);

        // Dữ liệu thông báo mẫu
        notificationList = new ArrayList<>();
        notificationList.add(new Notification("Thông báo nội dung thi"));
        notificationList.add(new Notification("Thông báo lịch thi cuối kì"));
        notificationList.add(new Notification("Thông báo lịch học tuần này"));
        notificationList.add(new Notification("Thông báo kết quả học tập"));
        notificationList.add(new Notification("Thông báo kết quả điểm rèn luyện"));

        // Cài đặt RecyclerView
        adapter = new NotificationAdapter(notificationList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);

        // Xử lý nút "Previous" và "Next"
        btnPrevious.setOnClickListener(v -> {
            if (currentPosition > 0) {
                currentPosition--;
                recyclerView.smoothScrollToPosition(currentPosition);
            }
        });

        btnNext.setOnClickListener(v -> {
            if (currentPosition < notificationList.size() - 1) {
                currentPosition++;
                recyclerView.smoothScrollToPosition(currentPosition);
            }
        });

        return view;
    }
}
