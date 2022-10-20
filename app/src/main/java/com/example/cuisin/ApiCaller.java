package com.example.cuisin;

import android.content.Context;

import com.example.cuisin.Api.Root;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

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
        @GET("recipes/complexSearch")
        Call<Root> getTopRecipes(
                @Query("number") String number,
                @Query("addRecipeInformation") Boolean addRecipeInformation,
                @Query("apiKey") String apiKey,
                @Query("query") String query
        );
    }

    public void getTopRecipes(ApiListener apiListener){
        TopRecipes topRecipes = fit.create(TopRecipes.class);
        Call<Root> rootCall = topRecipes.getTopRecipes(
                "5",
                true,
                ctx.getString(R.string.api_key),
                ctx.getString(R.string.query)
        );

        rootCall.enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                apiListener.getResponse((ApiListener) response.body(), response.message());
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                apiListener.getError(t.getMessage());
            }
        });
    }
}
