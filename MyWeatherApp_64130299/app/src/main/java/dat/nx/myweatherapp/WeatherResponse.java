package dat.nx.myweatherapp;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class WeatherResponse {

    public Coord coord;
    public Main main;
    public Wind wind;
    public Clouds clouds;
    public Sys sys;
    public String name;
    public List<Weather> weather;

    public class Coord {
        public float lon;
        public float lat;
    }

    public class Main {
        public float temp;
        public float feels_like;
        public float temp_min;
        public float temp_max;
        public int pressure;
        public int humidity;
    }

    public class Wind {
        public float speed;
        public int deg;
        public float gust;
    }

    public class Clouds {
        public int all;
    }

    public class Sys {
        public String country;
        public long sunrise;
        public long sunset;
    }

    public class Weather {
        public int id;
        public String main;
        public String description;
        public String icon;
    }
}

