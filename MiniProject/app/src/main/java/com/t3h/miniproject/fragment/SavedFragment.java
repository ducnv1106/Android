package com.t3h.miniproject.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.t3h.miniproject.NewsAdapter;
import com.t3h.miniproject.R;
import com.t3h.miniproject.model.News;

import java.util.ArrayList;

public class SavedFragment extends BaseFragment {
    public SavedFragment() {
    }

    public SavedFragment(RecyclerView lv_news, ArrayList<News> data, NewsAdapter adapter) {
        super(lv_news, data, adapter);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_saved;
    }

    @Override
    public String getTitle() {
        return "Saved";
    }



    @Override
    protected int getRecycleview() {
        return R.id.lv_saved;
    }

    @Override
    public void onItemLongClicked(final int position, View v) {
        PopupMenu menu_saved=new PopupMenu(getContext(),v);
        menu_saved.getMenuInflater().inflate(R.menu.menu_saved,menu_saved.getMenu());
        menu_saved.show();
        menu_saved.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                getData().remove(position);
                getAdapter().setData(getData());
                return false;
            }
        });
    }
}
