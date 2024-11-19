package dat.nx.myweatherapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {

    @GET("weather")
    Call<WeatherResponse> getWeatherData(
            @Query("q") String cityName, // Tên thành phố
            @Query("appid") String apiKey, // API Key của bạn
            @Query("units") String units // Đơn vị nhiệt độ
    );
}
