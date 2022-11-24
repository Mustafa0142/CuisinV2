package com.example.cuisin;

import com.example.cuisin.Api.RecipeInformation;
import com.example.cuisin.Api.ResultsList;

public interface ApiListener {

    void getResponse(ResultsList resultsList);

    void getResponse (RecipeInformation recipeInformation);

    void getError(String message);

}
