package com.example.zizoj.newser;

import com.example.zizoj.newser.Responses.Response;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Service {
    String BaseURL = "https://newsapi.org/";

    @GET("v2/top-headlines?country=eg&category=business&apiKey=6776a5708d5f47819e25a81bf0a0ba5a")
    Call<Response>methods1();
}
