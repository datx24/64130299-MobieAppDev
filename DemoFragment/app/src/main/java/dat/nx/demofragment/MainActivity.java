package dat.nx.demofragment;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Add fragments programmatically
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.top_fragment, new TopFragment())
                .replace(R.id.bottom_fragment, new BottomFragment())
                .commit();
    }
}