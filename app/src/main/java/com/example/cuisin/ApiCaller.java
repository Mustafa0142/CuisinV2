package com.example.cuisin;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.cuisin.Api.RecipeInformation;
import com.example.cuisin.Api.ResultsList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


// ik maak hier gebruik van retrofit om http requests makkelijk te handlen, deze heeft ingebouwde functies
public class ApiCaller {
    private final String API_KEY = "c4887d7664494b67bca752f492cbd59f";
    private final String DOCUMENTATION = "https://spoonacular.com/food-api/docs";

    Retrofit fit = new Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    //inputText dient voor de searchbar (ingegeven query is standaard Top Recipes, staat in onCreate functie)
    public void getTopRecipes(ApiListener apiListener, String inputText, Integer recipeAmount){
        TopRecipes topRecipes = fit.create(TopRecipes.class);
        Call<ResultsList> resultsListCall = topRecipes.getTopRecipes(
                recipeAmount,
                true,
                true,
                API_KEY,
                inputText
        );

//      we gaan asynchronisch een request verzenden
        resultsListCall.enqueue(new Callback<ResultsList>() {
            @Override
            public void onResponse(@NonNull Call<ResultsList> resultsListCall, @NonNull Response<ResultsList> response) {
                apiListener.getResponse((ResultsList) response.body());

                //Log.d("responsebody", response.body().toString());
            }

            @Override
            public void onFailure(@NonNull Call<ResultsList> resultsListCall, Throwable t) {
                apiListener.getError(t.getMessage());
            }
        });
    }

    public void getRecipeInformation(ApiListener apiListener, int recipeId){
        RecipeDetails recipeDetails = fit.create(RecipeDetails.class);
        Call<RecipeInformation> recipeInformationCall = recipeDetails.getRecipeInformation(
                recipeId,
                false,
                API_KEY
        );

        recipeInformationCall.enqueue(new Callback<RecipeInformation>() {
            @Override
            public void onResponse(@NonNull Call<RecipeInformation> recipeInformationCall, @NonNull Response<RecipeInformation> response) {
                apiListener.getResponse((RecipeInformation) response.body());
            }

            @Override
            public void onFailure(@NonNull Call<RecipeInformation> recipeInformationCall, Throwable t) {
                apiListener.getError(t.getMessage());
            }
        });
    }
}
