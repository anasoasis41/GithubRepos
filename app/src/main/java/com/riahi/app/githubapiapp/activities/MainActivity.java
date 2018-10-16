package com.riahi.app.githubapiapp.activities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.riahi.app.githubapiapp.R;
import com.riahi.app.githubapiapp.models.GithubUserItem;
import com.riahi.app.githubapiapp.utils.GithubApi;
import com.riahi.app.githubapiapp.utils.GithubService;
import com.riahi.app.githubapiapp.views.GithubUserAdapter;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.recyclerview_users)
    RecyclerView recyclerView = null;

    @BindView(R.id.coordinator)
    CoordinatorLayout coordinatorLayout;

    @BindView(R.id.progress_repo)
    ProgressBar progressBar;

    public GithubUserAdapter adapter;
    GithubUserItem githubUserItem;

    private static final int PAGE_START = 1;
    private int currentPage = PAGE_START;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration); // Add divider between items
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if (dy > 0) {
                    if (!recyclerView.canScrollVertically(RecyclerView.FOCUS_DOWN)) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                currentPage+=1;
                                loadMoreRepoData();
                                progressBar.setVisibility(View.VISIBLE);
                            }
                        }, 1000);

                    }
                    progressBar.setVisibility(View.INVISIBLE);

                }
            }
        });


        //checking for network connectivity
        if (!isNetworkAvailable()) {
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "No Network connection", Snackbar.LENGTH_LONG)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            loadRepoData();
                        }
                    });

            snackbar.show();
        } else {
            loadRepoData();
        }

    }

    private void loadRepoData() {
        Map<String, String> data = new HashMap<>();
        data.put("q", "created:>2018-09-16");
        data.put("sort", "stars");
        data.put("order", "desc");
        GithubService apiService = new GithubApi().getService();
        Call<GithubUserItem> githubUserItemCall = apiService.getUserList(data);
        githubUserItemCall.enqueue(new Callback<GithubUserItem>() {
            @Override
            public void onResponse(Call<GithubUserItem> call, Response<GithubUserItem> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this,
                            "Sucess",
                            Toast.LENGTH_SHORT).show();
                    githubUserItem = response.body();
                    updateUI(githubUserItem);

                } else {

                    //APIError error = ErrorUtils.parseError(response);
                    Log.d("error message", "error");
                }
            }

            @Override
            public void onFailure(Call<GithubUserItem> call, Throwable t) {
                Toast.makeText(MainActivity.this,
                        "Request failed. Check your internet connection",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadMoreRepoData() {

        Map<String, String> data = new HashMap<>();
        data.put("q", "created:>2018-09-16");
        data.put("sort", "stars");
        data.put("order", "desc");
        data.put("page", String.valueOf(currentPage));
        GithubService apiService = new GithubApi().getService();
        Call<GithubUserItem> githubUserItemCall = apiService.getUserList(data);
        githubUserItemCall.enqueue(new Callback<GithubUserItem>() {
            @Override
            public void onResponse(Call<GithubUserItem> call, Response<GithubUserItem> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this,
                            "Loading ...",
                            Toast.LENGTH_SHORT).show();
                    GithubUserItem githubUserItemNext = response.body();
                    githubUserItem.getItems().addAll(githubUserItemNext.getItems());
                    Log.d("new size ",githubUserItem.getItems().size()+"");
                    adapter.notifyDataSetChanged();


                } else {

                    //APIError error = ErrorUtils.parseError(response);
                    Log.d("error message", "error");
                }
            }

            @Override
            public void onFailure(Call<GithubUserItem> call, Throwable t) {
                Toast.makeText(MainActivity.this,
                        "Request failed. Check your internet connection",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUI(GithubUserItem githubUserItem) {
        adapter = new GithubUserAdapter(githubUserItem.getItems());
        recyclerView.setAdapter(adapter);
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    public void onResume() {
        super.onResume();
        recyclerView.setAdapter(adapter);
    }
}
