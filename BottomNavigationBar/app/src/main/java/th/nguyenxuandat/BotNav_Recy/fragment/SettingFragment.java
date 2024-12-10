package th.nguyenxuandat.BotNav_Recy.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import th.nguyenxuandat.BotNav_Recy.R;
import th.nguyenxuandat.BotNav_Recy.adapter.SettingAdapter;
import th.nguyenxuandat.BotNav_Recy.models.entity.Notification;

public class SettingFragment extends Fragment {
    private RecyclerView recyclerView;
    private SettingAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewSetting);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3)); // 3 cột

        List<Notification> items = new ArrayList<>();
        items.add(new Notification("Modern", R.mipmap.ic_phone_call_foreground));
        items.add(new Notification("Hỗ trợ", R.mipmap.ic_phone_call_foreground));
        items.add(new Notification("Thanh toán", R.mipmap.ic_phone_call_foreground));
        items.add(new Notification("FPT TV", R.mipmap.ic_phone_call_foreground));
        items.add(new Notification("Hợp đồng", R.mipmap.ic_phone_call_foreground));
        items.add(new Notification("Dịch vụ", R.mipmap.ic_phone_call_foreground));

        adapter = new SettingAdapter(getContext(), items);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
