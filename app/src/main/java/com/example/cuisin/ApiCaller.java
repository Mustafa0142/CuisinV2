package com.example.cuisin;

import android.content.Context;

import com.example.cuisin.Api.ResultsList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;


// ik maak hier gebruik van retrofit om http requests makkelijk te handlen, deze heeft ingebouwde functies
public class ApiCaller {
    Context ctx;
    Retrofit fit = new Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public ApiCaller(Context ctx) {
        this.ctx = ctx;
    }

    private interface TopRecipes {
        //we gaan hier de lijst met results ophalen o.b.v. deze properties van query
        @GET("recipes/complexSearch")
        Call<ResultsList> getTopRecipes(
                @Query("number") Integer number,
                @Query("addRecipeInformation") Boolean addRecipeInformation,
                @Query("apiKey") String apiKey,
                @Query("query") String query
        );
    }

    //inputText dient voor de searchbar (ingegeven query is standaard Top Recipes, staat in onCreate functie)
    public void getTopRecipes(ApiListener apiListener, String inputText){
        TopRecipes topRecipes = fit.create(TopRecipes.class);
        Call<ResultsList> resultsListCall = topRecipes.getTopRecipes(
                10,
                true,
                ctx.getString(R.string.api_key),
                inputText
        );

        resultsListCall.enqueue(new Callback<ResultsList>() {
            @Override
            public void onResponse(Call<ResultsList> resultsListCall, Response<ResultsList> response) {
                apiListener.getResponse((ResultsList) response.body(), response.message());

                //Log.d("responsebody", response.body().toString());
            }

            @Override
            public void onFailure(Call<ResultsList> resultsListCall, Throwable t) {
                apiListener.getError(t.getMessage());
            }
        });
    }
}
