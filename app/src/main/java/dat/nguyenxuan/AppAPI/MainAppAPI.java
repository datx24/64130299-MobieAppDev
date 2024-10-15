package dat.nguyenxuan.AppAPI;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import dat.nguyenxuan.viduhello.R;

public class MainAppAPI extends AppCompatActivity {
    private TextView resultTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_api);

        resultTextView = findViewById(R.id.resultTextView);

        //Gọi API với url từ web movie database
        String apiURL = "https://api.themoviedb.org/3/movie/popular?api_key=8cbf2fd082d901334cb0ce318f9a1948";
        new FetchDataTask().execute(apiURL);
    }
    private class FetchDataTask extends AsyncTask<String,Void,String> {
        String result = "";
        @Override
        protected String doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");

                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine())!= null) {
                    content.append(inputLine);
                }
                in.close();
                urlConnection.disconnect();
                result = content.toString();
            } catch (Exception e) {
                e.printStackTrace();
                result = "Error fetching data";
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            //Hiển thị kết quả trong text view
            resultTextView.setText(result);
        }
    }
}