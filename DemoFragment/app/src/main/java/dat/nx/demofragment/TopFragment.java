package dat.nx.demofragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import dat.nx.demofragment.R;

public class TopFragment extends Fragment {
    private CardView cardView;
    public interface OnColorChangeListener {
        void onColorChange(int color);
    }
    private OnColorChangeListener colorChangeListener;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // Đảm bảo rằng Activity chứa fragment này đã implement interface
        if (context instanceof OnColorChangeListener) {
            colorChangeListener = (OnColorChangeListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnColorChangeListener");
        }
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top, container, false);
        TextView textView = view.findViewById(R.id.text_top);
        textView.setText("DEMO FRAGMENT");
        cardView = view.findViewById(R.id.card_view);
        // Đặt màu mặc định cho CardView
        cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF")); // Màu trắng mặc định
        // Xử lý sự kiện chạm vào CardView
        cardView.setOnClickListener(v -> {
            // Khi chạm vào CardView, đổi lại màu mặc định (màu trắng)
            cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
        });

        return view;
    }

    public void changeCardColor(int color) {
        cardView.setCardBackgroundColor(color);
    }
}
