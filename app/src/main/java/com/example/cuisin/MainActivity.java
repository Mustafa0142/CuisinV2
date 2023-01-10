package com.example.cuisin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cuisin.Adapters.TopRecipesAdapter;
import com.example.cuisin.Api.RecipeInformation;
import com.example.cuisin.Api.Result;
import com.example.cuisin.Api.ResultsList;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements TextView.OnEditorActionListener {

    ApiCaller apiCaller;
    ArrayList<Result> list;
    TopRecipesAdapter topRecipesAdapter;
    RecyclerView recyclerView;
    EditText recipeInput;
    TextView recipesText;

    private Integer recipeAmount;
    private SharedPreferences savedValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//      override van dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        savedValues = PreferenceManager.getDefaultSharedPreferences(this);
        recipeAmount = Integer.parseInt(savedValues.getString("recipe_numbers", "5"));

//      de query van de api gaat standaard op Top Recipes staan. Dan krijgen we onmiddelijk recepten wanneer de app opstart
        apiCaller = new ApiCaller();
        apiCaller.getTopRecipes(apiListener, "Top Recipes", recipeAmount);

//      edit box in activity_main.xml
        recipeInput = (EditText) findViewById(R.id.recipeInput);
        recipeInput.setOnEditorActionListener(this);

//      hier gaan we uitvoeren wat er gaat gebeuren als je op een recept klikt. Openen van detailsactivity
        topRecipesAdapter = new TopRecipesAdapter(MainActivity.this, list, new TopRecipesAdapter.ItemClickListener() {
            @Override
            public void onItemClick(String recipeId) {
                startActivity(new Intent(MainActivity.this, DetailsActivity.class)
                        .putExtra("recipeId", recipeId));
            }
        });
    }

    private final ApiListener apiListener = new ApiListener() {
        @Override
        public void getResponse(ResultsList resultsList) {
            GridLayoutManager manager = new GridLayoutManager(MainActivity.this, 1);
            recyclerView = findViewById(R.id.tr_recycler);
            recyclerView.setHasFixedSize(true);
            manager.setOrientation(GridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(manager);
            topRecipesAdapter = new TopRecipesAdapter(MainActivity.this, resultsList.results, topRecipesAdapter.itemClickListener);
            recyclerView.setAdapter(topRecipesAdapter);
        }
        @Override
        public void getError(String message) {
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
        }
        @Override
        public void getResponse(RecipeInformation recipeInformation){}
    };

    ///////////////////////////////////////searchbar/////////////////////////////////////////////////

    //  als we iets ingeven in de edit box en op enter drukken gaan we de ingegeven text meegeven met inputText en de APi oproepen met de meegeven input.
//  zo krijgen we resultaten te zien met de ingegeven query.
    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (i == EditorInfo.IME_ACTION_SEARCH) {
            String inputText = recipeInput.getText().toString();
            apiCaller.getTopRecipes(apiListener, inputText, recipeAmount);

            recipesText = (TextView) findViewById(R.id.textViewRecipe);
            recipesText.setText(inputText + " recipes");
        }
        return true;
    }

    ///////////////////////////////////////settings activity/////////////////////////////////////////////////

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recipes_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        // get preferences
        recipeAmount = Integer.parseInt(savedValues.getString("recipe_numbers", "5"));
        apiCaller.getTopRecipes(apiListener, recipeInput.getText().toString(), recipeAmount);
    }
}