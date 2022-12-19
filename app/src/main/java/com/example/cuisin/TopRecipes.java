package com.example.cuisin;

import com.example.cuisin.Api.ResultsList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TopRecipes {
    //we gaan hier de lijst met results ophalen o.b.v. deze properties van query
    @GET("recipes/complexSearch")
    Call<ResultsList> getTopRecipes(
            @Query("number") Integer number,
            @Query("addRecipeInformation") Boolean addRecipeInformation,
            @Query("instructionsRequired") Boolean instructionsRequired,
            @Query("apiKey") String apiKey,
            @Query("query") String query
    );
}
