package com.riahi.app.githubapiapp.utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GithubApi {

    public static final String BASE_URL = "https://api.github.com";

    private Retrofit retrofit;

    public GithubApi() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public GithubService getService() {

        return retrofit.create(GithubService.class);
    }
}
