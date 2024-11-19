package dat.nx.demofragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import dat.nx.demofragment.R;

public class BottomFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bottom, container, false);
        // Ánh xạ các nút
        MaterialButton buttonBlue = view.findViewById(R.id.button_one);
        MaterialButton buttonGreen = view.findViewById(R.id.button_two);
        MaterialButton buttonRed = view.findViewById(R.id.button_three);

        // Thiết lập sự kiện click cho các nút
        buttonBlue.setOnClickListener(v -> {
            if (getActivity() instanceof TopFragment.OnColorChangeListener) {
                ((TopFragment.OnColorChangeListener) getActivity()).onColorChange(0x3F51B5);
            }
        });

        buttonGreen.setOnClickListener(v -> {
            if (getActivity() instanceof TopFragment.OnColorChangeListener) {
                ((TopFragment.OnColorChangeListener) getActivity()).onColorChange(0xFF03DAC5); // Green
            }
        });

        buttonRed.setOnClickListener(v -> {
            if (getActivity() instanceof TopFragment.OnColorChangeListener) {
                ((TopFragment.OnColorChangeListener) getActivity()).onColorChange(0xFFFF5722); // Red
            }
        });
        return view;
    }
}
