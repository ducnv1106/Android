package com.t3h.miniproject.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.t3h.miniproject.MainActivity;
import com.t3h.miniproject.NewsAdapter;
import com.t3h.miniproject.R;
import com.t3h.miniproject.WebActivity;
import com.t3h.miniproject.model.Constant;
import com.t3h.miniproject.model.News;

import java.util.ArrayList;

public class NewsFragment extends BaseFragment implements NewsAdapter.ItemClickListener {

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
        PopupMenu menu_save=new PopupMenu(getContext(),v);
        menu_save.getMenuInflater().inflate(R.menu.menu_save,menu_save.getMenu());
        menu_save.show();
        menu_save.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.item_luu:
                        MainActivity main= (MainActivity) getActivity();
                        main.getSaved().addData(main.getNews().getData().get(position));
                        main.getSaved().getAdapter().setData(main.getSaved().getData());

                        break;
                }
                return true;
            }
        });
    }

}

