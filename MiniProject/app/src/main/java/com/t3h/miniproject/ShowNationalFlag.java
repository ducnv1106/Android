package com.t3h.miniproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.t3h.miniproject.R;

import com.t3h.miniproject.model.NationalFlag;

import java.util.ArrayList;

public class ShowNationalFlag extends DialogFragment implements NationalFlagAdapter.ItemNationalFlagOnClickListener {
    private RecyclerView lv_national;
    private ArrayList<NationalFlag> data=new ArrayList<>();
    private NationalFlagAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_nationalflag,container,false);
        lv_national=(RecyclerView) view.findViewById(R.id.lv_flag);
        data.add(new NationalFlag("vi",R.drawable.ic_vi));
        data.add(new NationalFlag("ms",R.drawable.ic_malaysia));
        data.add(new NationalFlag("ko",R.drawable.ic_south_korea));
        data.add(new NationalFlag("ja",R.drawable.ic_japan));
        data.add(new NationalFlag("in",R.drawable.ic_indonesia));
        data.add(new NationalFlag("fr",R.drawable.ic_france));
        data.add(new NationalFlag("de",R.drawable.ic_germany));


        data.size();
        adapter=new NationalFlagAdapter(getContext());
        adapter.setData(data);
        adapter.setOnClickListener(this);
        lv_national.setAdapter(adapter);


        return view;
    }

    public RecyclerView getLv_national() {
        return lv_national;
    }

    public void setLv_national(RecyclerView lv_national) {
        this.lv_national = lv_national;
    }

    public ArrayList<NationalFlag> getData() {
        return data;
    }

    public void setData(ArrayList<NationalFlag> data) {
        this.data = data;
    }

    public NationalFlagAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(NationalFlagAdapter adapter) {
        this.adapter = adapter;
    }


    @Override
    public void onItemNationalFlagClicked(int position) {
        MainActivity mainActivity= (MainActivity) getActivity();
        mainActivity.setItem(data.get(position).getImg());
        mainActivity.setLanguage(data.get(position).getKeylanguage());
    }
}
