package com.riahi.app.githubapiapp.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.riahi.app.githubapiapp.R;
import com.riahi.app.githubapiapp.models.GithubUser;

import java.util.List;

public class GithubUserAdapter extends RecyclerView.Adapter<GithubUserViewHolder> {

    private List<GithubUser> githubUsers;

    // CONSTRUCTOR
    public GithubUserAdapter(List<GithubUser> githubUsers) {
        this.githubUsers = githubUsers;
    }

    @Override
    public GithubUserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // CREATE VIEW HOLDER AND INFLATING ITS XML LAYOUT
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.user_main_item, parent, false);

        return new GithubUserViewHolder(view);
    }

    // UPDATE VIEW HOLDER WITH A GITHUBUSER
    @Override
    public void onBindViewHolder(GithubUserViewHolder viewHolder, int position) {
        viewHolder.updateWithGithubUser(this.githubUsers.get(position));
    }

    // RETURN THE TOTAL COUNT OF ITEMS IN THE LIST
    @Override
    public int getItemCount() {
        return this.githubUsers.size();
    }

    public GithubUser getUser(int position){
        return this.githubUsers.get(position);
    }
}
