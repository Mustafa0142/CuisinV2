package com.example.cuisin;

public interface ApiListener {

    void getResponse(ApiListener apiListener, String message);

    void getError(String message);
}
