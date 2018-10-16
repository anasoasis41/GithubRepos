package com.riahi.app.githubapiapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GithubUserItem {

    @SerializedName("items")
    private List<GithubUser> items;
    public List<GithubUser> getItems() {
        return items;
    }
}
