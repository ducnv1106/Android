package com.t3h.miniproject.fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.t3h.miniproject.MainActivity;
import com.t3h.miniproject.Adapter.NewsAdapter;
import com.t3h.miniproject.R;
import com.t3h.miniproject.model.News;

import java.util.ArrayList;

public class NewsFragment extends BaseFragment implements NewsAdapter.ItemClickListener {
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

                        main.getSaved().addData(main.getNews().getData().get(position));
                        main.getSaved().getAdapter().setData(main.getSaved().getData());
                        break;
                    case R.id.item_menu_news_favorite:

                       main.getFavorite().addData(main.getNews().getData().get(position));

                        break;
                }
                return false;
            }
        });
    }

}

