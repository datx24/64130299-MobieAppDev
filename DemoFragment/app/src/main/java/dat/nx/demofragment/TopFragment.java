package dat.nx.demofragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import dat.nx.demofragment.R;

public class TopFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top, container, false);
        TextView textView = view.findViewById(R.id.text_top);
        textView.setText("Phần trên: 4/5 màn hình");
        return view;
    }
}
