package datnx.doan.timdothatlac;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NominatimApi {
    @GET("reverse")
    Call<Item> getLocation(
            @Query("lat") double latitude,
            @Query("lon") double longitude,
            @Query("format") String format
    );
}

