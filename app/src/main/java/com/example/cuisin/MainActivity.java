package com.example.cuisin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cuisin.Adapters.TopRecipesAdapter;
import com.example.cuisin.Api.ResultsList;

public class MainActivity extends AppCompatActivity implements TextView.OnEditorActionListener {

    ApiCaller apiCaller;
    TopRecipesAdapter topRecipesAdapter;
    RecyclerView recyclerView;
    EditText recipeInput;
    TextView recipesText;

    //for number of recipes shown
    private Integer recipeAmount;
    // define the SharedPreferences object
    private SharedPreferences savedValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//      override van dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);

//      set the default values for the preferences
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
//      get default SharedPreferences object
        savedValues = PreferenceManager.getDefaultSharedPreferences(this);
//      get preferences
        recipeAmount = Integer.parseInt(savedValues.getString("recipe_numbers", "5"));

//      de query van de api gaat standaard op Top Recipes staan. Dan krijgen we onmiddelijk recepten wanneer de app opstart
        apiCaller = new ApiCaller(this);
        apiCaller.getTopRecipes(apiListener, "Top Recipes", recipeAmount);

//      edit box in activity_main.xml
        recipeInput = (EditText) findViewById(R.id.recipeInput);
        recipeInput.setOnEditorActionListener(this);
    }

    private final ApiListener apiListener = new ApiListener() {

        //      wanneer we een response krijgen van de API gaan we onze adapter waar een layoutinflater in zit binden aan onze recycler view
//      zo kunnen we elk recept die we krijgen tonen in een layout
        @Override
        public void getResponse(ResultsList resultsList, String message) {
            GridLayoutManager manager = new GridLayoutManager(MainActivity.this, 1);
            recyclerView = findViewById(R.id.tr_recycler);
            recyclerView.setHasFixedSize(true);
            manager.setOrientation(GridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(manager);
            topRecipesAdapter = new TopRecipesAdapter(MainActivity.this, resultsList.results, recipeDetails);

//            for (int i = 0; i < resultsList.results.size(); i++) {
//                Log.d("results", resultsList.results.get(i).title);
//            }

            recyclerView.setAdapter(topRecipesAdapter);
        }
        @Override
        public void getError(String message) {
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };

    //hier gaan we uitvoeren wat er gaat gebeuren als je op een recept klikt.
    private final TopRecipesAdapter.RecipeDetails recipeDetails = new TopRecipesAdapter.RecipeDetails() {
        @Override
        public void onRecipeClick(String spoonacularSourceUrl) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(spoonacularSourceUrl));
            startActivity(i);
        }
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
    protected void onResume() {
        super.onResume();

        // get preferences
        recipeAmount = Integer.parseInt(savedValues.getString("recipe_numbers", "5"));
        apiCaller.getTopRecipes(apiListener, recipeInput.getText().toString(), recipeAmount);
    }
}