package com.example.cuisin;

import com.example.cuisin.Api.RecipeInformation;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RecipeDetails {
    @GET("recipes/{id}/information")
    Call<RecipeInformation> getRecipeInformation(
            //omdat de id in de url een path is gaan we een path implementeren en deze declareren aan int id
            @Path("id") int recipeId,
            @Query("includeNutrition") Boolean includeNutrition,
            @Query("apiKey") String apiKey
    );
}
