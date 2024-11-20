package dat.nx.myweatherapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText cityEditText;
    private TextView weatherDescriptionTextView, temperatureTextView;
    private ImageView weatherIconImageView;
    private FloatingActionButton searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityEditText = findViewById(R.id.cityEditText);
        weatherDescriptionTextView = findViewById(R.id.weatherDescriptionTextView);
        temperatureTextView = findViewById(R.id.temperatureTextView);
        searchButton = findViewById(R.id.searchButton);

        // Handle the search button click
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cityName = cityEditText.getText().toString().trim();
                if (!cityName.isEmpty()) {
                    fetchWeatherData(cityName);
                } else {
                    Toast.makeText(MainActivity.this, "Please enter a city name", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void fetchWeatherData(String cityName) {
        WeatherApi weatherApi = RetrofitClient.getRetrofitInstance().create(WeatherApi.class);
        Call<WeatherResponse> call = weatherApi.getWeatherData(cityName, "9a5d69949e8907e3312061eaf78d19ea", "metric");

        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    WeatherResponse weatherResponse = response.body();
                    String description = weatherResponse.weather.get(0).description;
                    float temperature = weatherResponse.main.temp;
                    String iconCode = weatherResponse.weather.get(0).icon;
                    // Update UI
                    weatherDescriptionTextView.setText(description);
                    temperatureTextView.setText("Temperature: " + temperature + "°C");

                    // Load weather icon dynamically based on icon code (you can modify the logic)
                    String iconUrl = "http://openweathermap.org/img/wn/" + iconCode + "@2x.png";
                    Glide.with(MainActivity.this).load(iconUrl).into(weatherIconImageView);
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
