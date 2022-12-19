package com.example.cuisin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.cuisin.Api.ExtendedIngredient;
import com.example.cuisin.Api.RecipeInformation;
import com.example.cuisin.Api.ResultsList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {
    GridView ingredients_list;
    TextView recipe_title, recipe_source, recipe_likes,
             recipe_servings, recipe_score, recipe_prep;
    Button recipe_button;
    ImageView recipe_image;

    ApiCaller apiCaller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
//      om bar te verbergen van boven
        this.getSupportActionBar().hide();

        int recipeId = Integer.parseInt(getIntent().getStringExtra("recipeId"));

        recipe_title = findViewById(R.id.recipeTitle);
        recipe_source = findViewById(R.id.recipeSource);
        recipe_likes = findViewById(R.id.recipeLikes);
        recipe_servings = findViewById(R.id.recipeServings);
        recipe_score = findViewById(R.id.recipeScore);
        recipe_prep = findViewById(R.id.recipePrep);
        recipe_button = findViewById(R.id.recipeButton);
        recipe_image = findViewById(R.id.recipeImage);
        ingredients_list = findViewById(R.id.ingredients_listview);

        apiCaller = new ApiCaller();
        apiCaller.getRecipeInformation(detailsListener, recipeId);
    }

    private final ApiListener detailsListener = new ApiListener() {
        @Override
        public void getResponse(RecipeInformation recipeInformation) {
            recipe_title.setText(recipeInformation.title);
            recipe_source.setText(recipeInformation.sourceName);
            recipe_likes.setText(recipeInformation.aggregateLikes + " likes");
            recipe_servings.setText(recipeInformation.servings + " servings!");
            recipe_score.setText("Health score: " + recipeInformation.healthScore + "/100");
            recipe_prep.setText("Ready in " + recipeInformation.readyInMinutes + " minutes!");
            Picasso.get().load(recipeInformation.image).into(recipe_image);

            recipe_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri uriUrl = Uri.parse(recipeInformation.spoonacularSourceUrl);
                    Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                    startActivity(launchBrowser);
                }
            });

            ///ingredienten tonen in detailspage
            List<ExtendedIngredient> items = recipeInformation.extendedIngredients;

            // create a List of Map<String, ?> objects
            ArrayList<HashMap<String, String>> data =
                    new ArrayList<HashMap<String, String>>();
            for (ExtendedIngredient item : items) {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("original", item.original);
                data.add(map);
            }

            // create the resource, from, and to variables
            int resource = R.layout.recipe_ingredients;
            String[] from = {"original"};
            int[] to = {R.id.ingredient_name};

            // create and set the adapter
            SimpleAdapter adapter =
                    new SimpleAdapter(DetailsActivity.this, data, resource, from, to);
            ingredients_list.setAdapter(adapter);
        }

        @Override
        public void getError(String message) {
            getError(message);
        }

        @Override
        public void getResponse(ResultsList resultsList) {}
    };
}