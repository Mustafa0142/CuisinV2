package com.example.cuisin;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.cuisin.Api.RecipeInformation;
import com.example.cuisin.Api.ResultsList;

public class DetailsActivity extends AppCompatActivity {
    TextView recipe_instructions;
    Button recipe_button;
    ApiCaller apiCaller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        int recipeId = Integer.parseInt(getIntent().getStringExtra("recipeId"));

        recipe_instructions = findViewById(R.id.recipeInstructions);
        recipe_button = findViewById(R.id.recipeButton);

        apiCaller = new ApiCaller(this);
        apiCaller.getRecipeInformation(detailsListener, recipeId);
    }

    private final ApiListener detailsListener = new ApiListener() {
        @Override
        public void getResponse(RecipeInformation recipeInformation) {
            recipe_instructions.setText(recipeInformation.instructions);

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
        public void getResponse(ResultsList resultsList) {

        }
    };
}