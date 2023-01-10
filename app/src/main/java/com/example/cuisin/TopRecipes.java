package com.example.cuisin;

import com.example.cuisin.Api.ResultsList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TopRecipes {
    @GET("recipes/complexSearch")
    Call<ResultsList> getTopRecipes(
            @Query("number") Integer number,
            @Query("addRecipeInformation") Boolean addRecipeInformation,
            @Query("instructionsRequired") Boolean instructionsRequired,
            @Query("apiKey") String apiKey,
            @Query("query") String query
    );
}
