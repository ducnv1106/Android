package com.ducnv1106.restaurant.activitys;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentFactory;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.ducnv1106.restaurant.R;
import com.ducnv1106.restaurant.base.BaseActivity;
import com.ducnv1106.restaurant.databinding.ActivityMainBinding;
import com.ducnv1106.restaurant.databinding.FragmentRestaurantBinding;
import com.ducnv1106.restaurant.fragment.FoodFavorite;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends BaseActivity<ActivityMainBinding> implements NavigationView.OnNavigationItemSelectedListener {

    private FoodFavorite foodFavorite = new FoodFavorite();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding.naviView.setNavigationItemSelectedListener(this);
        initFragment();
    }

    protected void initFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.panel, foodFavorite);
        transaction.commit();
    }


    @Override
    protected int layoutID() {
        return R.layout.activity_main;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_com: {

                break;
            }
            case R.id.item_dangsoi: {
                break;
            }
            case R.id.item_monanthuongngay: {
                break;
            }case R.id.item_xoi:{
                break;
            }case R.id.item_danglong:{
                break;

            }
            case R.id.item_moncuon:{
                break;
            }
            case R.id.item_banhman:{
                break;
            }case R.id.item_banhngot:{
                break;
            }
            case R.id.item_monankhac:{
                break;
            }
            case R.id.item_dacsandiaphuong:{
                break;
            }
        }
        return false;
    }
}
