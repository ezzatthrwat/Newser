package com.example.zizoj.newser;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.zizoj.newser.NewsListAdapter.NewsAdapter;
import com.example.zizoj.newser.Responses.Article;
import com.example.zizoj.newser.Responses.Response;
import com.example.zizoj.newser.Utils.UniversalImageLoader;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private String API_KEY = "6776a5708d5f47819e25a81bf0a0ba5a";
    private String country = "eg";
    private String category = "business";

    RecyclerView recyclerView;
    NewsAdapter newsAdapter;

    ArrayList<Article> NewsList = new ArrayList<Article>();
    SwipeRefreshLayout swipeRefreshLayout;

    RelativeLayout failerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {

            @Override
            public void run() {

                swipeRefreshLayout.setRefreshing(true);

                // Fetching data from server
                retro(Service.BaseURL);
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.News_RecyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        initImageLoader();


        failerLayout = (RelativeLayout)findViewById(R.id.nointernetlayout);

    }


    private void initImageLoader() {
        UniversalImageLoader universalImageLoader = new UniversalImageLoader(MainActivity.this);
        ImageLoader.getInstance().init(universalImageLoader.getConfiguration());
    }

    public void retro(String BaseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();

        Service service = retrofit.create(Service.class);
        Call<Response> Call = service.methods1();

        Call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {

                failerLayout.setVisibility(View.GONE);
//                Toast.makeText(MainActivity.this,response.body().getArticles().get(0).getSource().getName(), Toast.LENGTH_SHORT).show();

                Article article = new Article();

                NewsList.addAll(response.body().getArticles());

                newsAdapter = new NewsAdapter(MainActivity.this, NewsList);
                recyclerView.setAdapter(newsAdapter);
                swipeRefreshLayout.setRefreshing(false);

            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Toast.makeText(MainActivity.this, "No Internet connection", Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(true);
                failerLayout.setVisibility(View.VISIBLE);
            }
        });


    }


    @Override
    public void onRefresh() {

        NewsList.clear();

        retro(Service.BaseURL);
    }
}



