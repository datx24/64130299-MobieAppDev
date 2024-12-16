package th.nguyenxuandat.quizappgui2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import pl.droidsonroids.gif.GifImageView;

public class PreloadingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preloading);

        GifImageView gifImageView = findViewById(R.id.loadingGif);
        gifImageView.setVisibility(View.VISIBLE);

        int LOADING_DURATION = 3000;

        new Handler().postDelayed(() -> {
            gifImageView.setVisibility(View.GONE);

            findViewById(R.id.appIcon).setVisibility(View.GONE);

            new Handler().postDelayed(() -> {
                Intent intent = new Intent(PreloadingActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }, 500);
        }, LOADING_DURATION);
    }
}
