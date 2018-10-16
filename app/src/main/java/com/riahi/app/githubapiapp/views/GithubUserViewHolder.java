package com.riahi.app.githubapiapp.views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.riahi.app.githubapiapp.R;
import com.riahi.app.githubapiapp.models.GithubUser;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GithubUserViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.description)
    TextView description;
    @BindView(R.id.owner_name)
    TextView ownerName;
    @BindView(R.id.stars)
    TextView stars;

    @BindView(R.id.avatar)
    ImageView imgAvatar;


    public GithubUserViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void updateWithGithubUser(GithubUser githubUser){
        this.name.setText(githubUser.getmName());
        this.description.setText(githubUser.getmDescription());
        this.ownerName.setText(githubUser.getOwner().getLogin());

        float numberStars = githubUser.getmStarsNumber();
        if (numberStars > 1000){
            numberStars /= 1000;
        }
        this.stars.setText(new DecimalFormat("##.#").format(numberStars) + "k");

        Glide.with(itemView.getContext())
                .load(githubUser.getOwner().getAvatar_url())
                .apply(RequestOptions.circleCropTransform())
                .into(imgAvatar);
        
    }
}
