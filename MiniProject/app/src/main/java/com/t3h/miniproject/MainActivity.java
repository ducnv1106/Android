package com.t3h.miniproject;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerTabStrip;
import androidx.viewpager.widget.ViewPager;

import com.t3h.miniproject.api.ApiBuilder;
import com.t3h.miniproject.databinding.ActivityMainBinding;
import com.t3h.miniproject.fragment.FavoriteFragment;
import com.t3h.miniproject.fragment.NewsFragment;
import com.t3h.miniproject.fragment.SavedFragment;
import com.t3h.miniproject.model.News;
import com.t3h.miniproject.model.NewsReponsive;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements Callback<NewsReponsive> {
    private SavedFragment saved = new SavedFragment();
    private FavoriteFragment favorite = new FavoriteFragment();
    private NewsFragment news = new NewsFragment();
    private PageNewsAdapter pageadapter;

    private ProgressDialog progressDialog;

    private RecyclerView lvNews;
    private ActivityMainBinding binding;
    private ActionBarDrawerToggle toggle;
    private ArrayList<News> data = new ArrayList<>();
    private NewsAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // nút báck trên thanh actionbar
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        toggle = new ActionBarDrawerToggle(this,
//                binding.drawer,
//                R.string.app_name,
//                R.string.app_name);
//        binding.drawer.addDrawerListener(toggle);
//        toggle.syncState();
        initView();

    }


    private void initView() {
//        editSearch=findViewById(R.id.edit_search);
//        btnsearch=findViewById(R.id.btn_search);
//        btnsearch.setOnClickListener(this);ư
//        adapter = new NewsAdapter(this);
//        lvNews = findViewById(R.id.lv_news);
//        lvNews.setAdapter(adapter);

        pageadapter = new PageNewsAdapter(getSupportFragmentManager(), news, saved, favorite);
        binding.pager.setAdapter(pageadapter);


    }

    @Override
    public void onResponse(Call<NewsReponsive> call, Response<NewsReponsive> response) {
        data = response.body().getNews();
        adapter.setData(data);
        progressDialog.dismiss();


    }

    @Override
    public void onFailure(Call<NewsReponsive> call, Throwable t) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       if(toggle.onOptionsItemSelected(item)){
           return true;
       }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchview, menu);
        final SearchView searchView = (SearchView) menu.findItem(R.id.menusearch).getActionView();
        searchView.setQueryHint("Search...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String apiKey = "7623cab638d64674883e0ba2efdef40a";
                String language = "vi";
                ApiBuilder.getInstance().getNews(query,
                        apiKey,
                        language).enqueue(MainActivity.this);
                data.clear();
                if (data.size() == 0) {
                    progressDialog = new ProgressDialog(MainActivity.this);
                    progressDialog.setMessage("Loading...");
                    progressDialog.show();
                }
                ;
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }


}
