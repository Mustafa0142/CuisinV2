package com.example.cuisin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.cuisin.Adapters.TopRecipesAdapter;
import com.example.cuisin.Api.ResultsList;

public class MainActivity extends AppCompatActivity {

    ApiCaller apiCaller;
    TopRecipesAdapter topRecipesAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiCaller = new ApiCaller(this);
        apiCaller.getTopRecipes(apiListener);

    }

    private final ApiListener apiListener = new ApiListener() {

        //wanneer we een response krijgen van de API gaan we onze adapter waar een layoutinflater in zit binden aan onze recycler view
        //zo kunnen we elk recept die we krijgen tonen in een layout
        @Override
        public void getResponse(ResultsList resultsList, String message) {
            GridLayoutManager manager = new GridLayoutManager(MainActivity.this, 1);
            recyclerView = findViewById(R.id.tr_recycler);
            recyclerView.setHasFixedSize(true);
            manager.setOrientation(GridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(manager);
            topRecipesAdapter = new TopRecipesAdapter(MainActivity.this, resultsList.results);

            for (int i = 0; i < resultsList.results.size(); i++) {
                Log.d("results", resultsList.results.get(i).title);
            }

            recyclerView.setAdapter(topRecipesAdapter);
        }

        @Override
        public void getError(String message) {
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };
}