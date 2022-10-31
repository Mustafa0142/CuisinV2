package com.example.cuisin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiCaller = new ApiCaller(this);

//      de query van het api gaat standaard op Top Recipes staan. Dan krijgen we onmiddelijk recepten wanneer de app opstart
        apiCaller.getTopRecipes(apiListener, "Top Recipes");

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
            topRecipesAdapter = new TopRecipesAdapter(MainActivity.this, resultsList.results);

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

//    als we iets ingeven in de edit box en op enter drukken gaan we de ingegeven text meegeven met inputText en de APi oproepen met de meegeven input.
//    zo krijgen we resultaten te zien met de ingegeven query.
    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (i == EditorInfo.IME_ACTION_SEARCH) {
            String inputText = recipeInput.getText().toString();
            apiCaller.getTopRecipes(apiListener, inputText);

            recipesText = (TextView) findViewById(R.id.textViewRecipe);
            recipesText.setText(inputText + " recipes");
        }
        return true;
    }
}