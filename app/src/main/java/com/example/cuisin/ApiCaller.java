package com.example.cuisin;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiCaller {
    Content content;
    Retrofit fit = new Retrofit.Builder()
            .baseUrl("https://api.edamam.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public ApiCaller(Content content) {
        this.content = content;
    }

    private interface TopRecipes {

    }
}
