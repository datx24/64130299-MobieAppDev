package dat.nguyenxuan.apponkiemtragiuaki;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainCau4 extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FoodAdapter adapter;
    private List<FoodItem> foodItems; // Store the original food items
    private List<FoodItem> filteredList; // Store filtered food items
    private EditText searchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_cau4);

        recyclerView = findViewById(R.id.foodRecyclerView);
        searchEditText = findViewById(R.id.searchEditText);

        // Set LayoutManager to GridLayoutManager with 2 columns
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        // Fetch food items from the API
        new FetchFoodItemsTask().execute("https://api.npoint.io/1f68b285f1f38b1a34a6"); // Replace with your API URL

        // Add TextWatcher for dynamic search functionality
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString().toLowerCase();
                filterFoodItems(query); // Filter as the user types
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private class FetchFoodItemsTask extends AsyncTask<String, Void, List<FoodItem>> {
        @Override
        protected List<FoodItem> doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStreamReader in = new InputStreamReader(urlConnection.getInputStream());
                Gson gson = new Gson();
                ApiResponse response = gson.fromJson(in, ApiResponse.class);
                return response.getVietnameseDelicacies();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<FoodItem> foodItems) {
            if (foodItems != null) {
                MainCau4.this.foodItems = foodItems; // Save original food items
                adapter = new FoodAdapter(foodItems, MainCau4.this);
                recyclerView.setAdapter(adapter);
            }
        }
    }

    private void filterFoodItems(String query) {
        filteredList = new ArrayList<>();

        // Check if the query is empty
        if (query.isEmpty()) {
            filteredList.addAll(foodItems); // Show all items if query is empty
        } else {
            // Filter items based on whether the name starts with the query
            for (FoodItem item : foodItems) {
                if (item.getName().toLowerCase().contains(query)) {
                    filteredList.add(item);
                }
            }
        }
        adapter.updateList(filteredList); // Update the adapter with the filtered list
    }

}
