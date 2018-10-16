package com.riahi.app.githubapiapp.utils;

import com.riahi.app.githubapiapp.models.GithubUserItem;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface GithubService {

    @GET("search/repositories")
    Call<GithubUserItem> getUserList(
            @QueryMap(encoded = false)
            Map<String, String> filter
    );
}
