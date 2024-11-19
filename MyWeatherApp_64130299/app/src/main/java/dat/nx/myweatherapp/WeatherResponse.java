package dat.nx.myweatherapp;

import java.util.List;  // Thêm import cho List của Java

public class WeatherResponse {
    public List<WeatherData> list;

    public class WeatherData {  // Đổi tên từ 'List' thành 'WeatherData'
        public Main main;
        public List<Weather> weather;
        public String dt_txt;

        public class Main {
            public float temp;
            public float humidity;
        }

        public class Weather {
            public String description;
        }
    }
}
