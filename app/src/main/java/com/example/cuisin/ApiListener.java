package com.example.cuisin;

import com.example.cuisin.Api.ResultsList;

public interface ApiListener {

    void getResponse(ResultsList resultsList, String message);

    void getError(String message);
}
