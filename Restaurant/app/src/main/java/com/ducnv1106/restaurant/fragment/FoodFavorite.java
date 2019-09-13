package com.ducnv1106.restaurant.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.ducnv1106.restaurant.R;
import com.ducnv1106.restaurant.base.BaseAdapter;
import com.ducnv1106.restaurant.base.BaseFragment;
import com.ducnv1106.restaurant.databinding.FoodfavoriteBinding;
import com.ducnv1106.restaurant.model.Food;

import java.util.ArrayList;

public class FoodFavorite extends BaseFragment<FoodfavoriteBinding> {

    private BaseAdapter<Food> adapter;
    private ArrayList<Food> data;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new BaseAdapter<>(getContext(), R.layout.item_favoritefood);
        data=new ArrayList<>();

        data.add(new Food("Bánh Cua","hello",10000,R.drawable.banhcanh));
        data.add(new Food("Bánh Cua","hello",10000,R.drawable.banhcanh));
        adapter.setData(data);
        binding.listFavo.setAdapter(adapter);
    }

    @Override
    protected int layoutID() {
        return R.layout.foodfavorite;
    }
}
