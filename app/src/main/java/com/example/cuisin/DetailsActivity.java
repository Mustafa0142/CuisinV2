package com.example.cuisin;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.cuisin.Api.RecipeInformation;
import com.example.cuisin.Api.ResultsList;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {
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

        apiCaller = new ApiCaller(this);
        apiCaller.getRecipeInformation(detailsListener, recipeId);
    }

    private final ApiListener detailsListener = new ApiListener() {
        @Override
        public void getResponse(RecipeInformation recipeInformation) {
            recipe_title.setText(recipeInformation.title);
            recipe_source.setText(recipeInformation.sourceName);
            recipe_likes.setText(recipeInformation.aggregateLikes + " likes");
            recipe_servings.setText(recipeInformation.servings + " servings!");
            recipe_score.setText("Score: " + recipeInformation.spoonacularScore + "/100");
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
        }

        @Override
        public void getError(String message) {
            getError(message);
        }

        @Override
        public void getResponse(ResultsList resultsList) {}
    };
}