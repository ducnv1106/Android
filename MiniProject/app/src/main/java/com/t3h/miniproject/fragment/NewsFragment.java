package com.t3h.miniproject.fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.t3h.miniproject.dowlnoad.DownloadAsync;
import com.t3h.miniproject.MainActivity;
import com.t3h.miniproject.Adapter.NewsAdapter;
import com.t3h.miniproject.R;
import com.t3h.miniproject.dao.DataBaseFavorite;
import com.t3h.miniproject.dao.DataBaseSaved;
import com.t3h.miniproject.model.News;
import com.t3h.miniproject.model.NewsSaved;

import java.util.ArrayList;

public class NewsFragment extends BaseFragment implements NewsAdapter.ItemClickListener {
    private NewsSaved newsSaved;
    public NewsFragment() {
    }

    public NewsFragment(RecyclerView lv_news, ArrayList<News> data, NewsAdapter adapter) {
        super(lv_news, data, adapter);

    }

    @Override
    protected int getRecycleview() {
        return R.id.lv_news;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_news;
    }

    @Override
    public String getTitle() {
        return "News";
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onItemLongClicked(final int position, View v) {
        PopupMenu menu_news=new PopupMenu(getContext(),v);
        menu_news.getMenuInflater().inflate(R.menu.menu_news,menu_news.getMenu());
        menu_news.show();
        menu_news.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                MainActivity main= (MainActivity) getActivity();
                switch (item.getItemId()){
                    case R.id.item_menu_news_saved:
                        DownloadAsync async=new DownloadAsync(main);
                        async.execute(main.getNews().getData().get(position).getUrl());


                        int id=main.getNews().getData().get(position).getId();
                        String title=main.getNews().getData().get(position).getTitle();
                        String desc=main.getNews().getData().get(position).getDesc();
                        String url=main.getNews().getData().get(position).getUrl();
                        String img=main.getNews().getData().get(position).getImg();
                        String date=main.getNews().getData().get(position).getDate();
                        String path=main.getPath();
                        newsSaved=new NewsSaved(id,title,desc,url,img,date,null);

//                        main.getSaved().getdata().add(news);
//                        main.getSaved().getAdapter().setData(main.getSaved().getData());

                        break;
                    case R.id.item_menu_news_favorite:
                        DataBaseFavorite.getInstance(getContext()).getNewsDao().insert(main.getNews().getData().get(position));
                        main.getFavorite().getData().add(main.getNews().getData().get(position));

                        break;
                }
                return false;
            }
        });
    }



    public NewsSaved getNewsSaved() {
        return newsSaved;
    }
}

