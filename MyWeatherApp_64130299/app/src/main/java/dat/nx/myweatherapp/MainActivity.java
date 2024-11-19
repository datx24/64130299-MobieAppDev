package dat.nx.myweatherapp;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import dat.nx.myweatherapp.R;

public class MainActivity extends AppCompatActivity {

    private TextView weatherTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Khởi tạo TextView để hiển thị kết quả
        weatherTextView = findViewById(R.id.weatherTextView);

        // Gọi API và lấy dữ liệu thời tiết
        fetchWeatherData("Hanoi", "your_api_key"); // Thay "your_api_key" bằng API key của bạn
    }

    private void fetchWeatherData(String cityName, String apiKey) {
        WeatherApi weatherApi = RetrofitClient.getRetrofitInstance().create(WeatherApi.class);

        // Gọi API
        Call<WeatherResponse> call = weatherApi.getWeatherData(cityName, apiKey, "metric");
        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    WeatherResponse weatherResponse = response.body();
                    // Lấy thông tin thời tiết
                    String description = weatherResponse.list.get(0).weather.get(0).description;
                    float temperature = weatherResponse.list.get(0).main.temp;

                    // Hiển thị kết quả
                    String weatherInfo = "Weather: " + description + "\nTemperature: " + temperature + "°C";
                    weatherTextView.setText(weatherInfo);
                } else {
                    Toast.makeText(MainActivity.this, "Failed to get weather data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
