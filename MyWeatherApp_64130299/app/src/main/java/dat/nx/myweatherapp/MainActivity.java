package dat.nx.myweatherapp;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextView weatherDescriptionTextView, temperatureTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ các thành phần giao diện
        weatherDescriptionTextView = findViewById(R.id.weatherDescriptionTextView);
        temperatureTextView = findViewById(R.id.temperatureTextView);

        // Lấy dữ liệu thời tiết cho một thành phố cụ thể
        String cityName = "Hanoi"; // Thay thế bằng tên thành phố mong muốn
        fetchWeatherData(cityName);
    }

    private void fetchWeatherData(String cityName) {
        WeatherApi weatherApi = RetrofitClient.getRetrofitInstance().create(WeatherApi.class);
        Call<WeatherResponse> call = weatherApi.getWeatherData(cityName, "9a5d69949e8907e3312061eaf78d19ea", "metric");

        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Lấy thông tin từ phản hồi API
                    WeatherResponse weatherResponse = response.body();
                    String description = weatherResponse.weather.get(0).description; // Mô tả thời tiết
                    float temperature = weatherResponse.main.temp; // Nhiệt độ

                    // Cập nhật giao diện
                    weatherDescriptionTextView.setText(description);
                    temperatureTextView.setText(String.format("%.1f°C", temperature));
                } else {
                    Toast.makeText(MainActivity.this, "Không thể lấy dữ liệu thời tiết", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
