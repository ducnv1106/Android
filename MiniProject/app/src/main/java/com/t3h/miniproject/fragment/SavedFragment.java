package com.t3h.miniproject.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.t3h.miniproject.Adapter.NewsSavedAdapter;
import com.t3h.miniproject.R;
import com.t3h.miniproject.WebActivity;
import com.t3h.miniproject.WebViewSaved;
import com.t3h.miniproject.dao.DataBaseSaved;
import com.t3h.miniproject.model.Constant;
import com.t3h.miniproject.model.NewsSaved;

import java.util.ArrayList;

public class SavedFragment extends BaseFragment implements NewsSavedAdapter.ItemNewsSavedClickListener {

    private ArrayList<NewsSaved> data=new ArrayList<>();
    private NewsSavedAdapter adapter;
    private RecyclerView lv_saved;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutID(), container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        adapter = new NewsSavedAdapter(getContext());
        adapter.setData(data);
        lv_saved = getActivity().findViewById(getRecycleview());
        lv_saved.setAdapter(adapter);
        adapter.setListener(this);
    }

    @Override
    protected int getRecycleview() {
        return R.id.lv_saved;
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
    public void onItemNewsSavedClicked(int position) {
        Intent intent=new Intent(getContext(), WebViewSaved.class);
        intent.putExtra(Constant.EXTRA_URL,data.get(position).getPath());
        startActivity(intent);
    }

    @Override
    public void onItemNewsSavedLongClicked(final int position, View v) {
        PopupMenu menu=new PopupMenu(getContext(),v);
        menu.getMenuInflater().inflate(R.menu.menu_saved,menu.getMenu());
        menu.show();
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                DataBaseSaved.getInstance(getContext()).getNewsSavedDao().delete(data.get(position));
                data.remove(position);
                adapter.setData(data);
                return false;
            }
        });
    }

    public ArrayList<NewsSaved> getdata() {
        return data;
    }


    public NewsSavedAdapter getAdapterSaved() {
        return adapter;
    }
}
