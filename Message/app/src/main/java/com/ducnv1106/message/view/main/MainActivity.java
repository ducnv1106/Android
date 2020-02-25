package com.ducnv1106.message.view;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.SnapHelper;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ducnv1106.message.Constants;
import com.ducnv1106.message.R;
import com.ducnv1106.message.databinding.ActivityMainBinding;
import com.ducnv1106.message.model.Friend;
import com.ducnv1106.message.model.Message;
import com.ducnv1106.message.model.User;
import com.ducnv1106.message.view.acitivity.BaseActivity;
import com.ducnv1106.message.view.acitivity.StartActivity;
import com.ducnv1106.message.view.acitivity.profile.ProfileActivity;
import com.ducnv1106.message.view.fragment.PagerAdapter;
import com.ducnv1106.message.view.fragment.chat.FragmentChat;
import com.ducnv1106.message.view.fragment.friend.FragmentFriend;
import com.ducnv1106.message.view.fragment.profile.FragmentProfile;
import com.ducnv1106.message.view.fragment.user.FragmentUser;
import com.ducnv1106.message.view.main.MainActivityListener;
import com.ducnv1106.message.viewmodel.MyViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import static com.ducnv1106.message.R.string.app_name;

public class MainActivity extends BaseActivity<ActivityMainBinding> implements NavigationView.OnNavigationItemSelectedListener, MainActivityListener {

    private PagerAdapter adapter;

    private MyViewModel myViewModel;

    private FragmentChat fragmentChat = new FragmentChat();
    private FragmentFriend fragmentFriend = new FragmentFriend();
    private FragmentUser fragmentUser = new FragmentUser();
    private FragmentProfile fragmentProfile = new FragmentProfile();

    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;

    private MenuItem menuItem;


    @Override
    protected int sytleId() {
        return R.style.Base_Theme_DesignDemo;
    }

    @Override
    protected void initView() {

        final Intent intent = getIntent();
        User user = (User) intent.getSerializableExtra(Constants.EXTRA_USER);


        Log.e("Main", user.getCoverimage() + "");
        binding.listViewpager.setUser(user);
        binding.listViewpager.setListener(this);

        myViewModel = ViewModelProviders.of(this).get(MyViewModel.class);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        reloadUser();

        adapter = new PagerAdapter(getSupportFragmentManager(), fragmentChat, fragmentFriend, fragmentUser, fragmentProfile);
        binding.listViewpager.viewPager.setAdapter(adapter);

        // start 4 page when the activity start
        binding.listViewpager.viewPager.setOffscreenPageLimit(4);
        binding.listViewpager.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (menuItem != null) {
                    menuItem.setChecked(false);
                } else {
                    binding.listViewpager.bottomNavi.getMenu().getItem(0).setChecked(true);
                }
                binding.listViewpager.bottomNavi.getMenu().getItem(position).setChecked(true);
                menuItem = binding.listViewpager.bottomNavi.getMenu().getItem(position);
            }


            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        countUnReadMessage();
        countUnAddFriend();

        binding.listViewpager.bottomNavi.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_chat: {
                        binding.listViewpager.viewPager.setCurrentItem(0);
                        Integer index = null;
                        for (Integer i : fragmentChat.getHashSet()) {
                            index = i;
                            Log.e("FragmentChat", index + "a");
                            break;
                        }
                        if (index != null) {
                            fragmentChat.getBinding().lvUserchat.scrollToPosition(index);
                        }


                        break;
                    }
                    case R.id.item_friend: {
                        binding.listViewpager.viewPager.setCurrentItem(1);
                        break;
                    }
                    case R.id.item_user: {
                        binding.listViewpager.viewPager.setCurrentItem(2);
                        break;
                    }
                    case R.id.item_profile: {
                        binding.listViewpager.viewPager.setCurrentItem(3);
                        break;
                    }
                }
                return false;
            }
        });
        binding.navigationView.setNavigationItemSelectedListener(this);


        Toolbar toolbar = binding.listViewpager.toobar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_toolbar);
//        getSupportActionBar().setHomeButtonEnabled(true);


    }

    @Override
    protected int layoutId() {
        return R.layout.activity_main;
    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//
//        switch (item.getItemId()) {
//            case android.R.id.home: {
//                binding.drawerLayout.openDrawer(GravityCompat.START);
//                return true;
//            }
//
//        }
//        return super.onOptionsItemSelected(item);
//    }


    public void reloadUser() {
        myViewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                binding.listViewpager.setUser(user);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_profile: {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                intent.putExtra(Constants.EXTRA_USER, myViewModel.getUser().getValue());
                startActivity(intent);
                break;
            }
            case R.id.logout: {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(this, StartActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
                break;
            }
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START);

        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("Main", "onResume");


    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("Main", "onStop");

    }

    @Override
    protected void onDestroy() {
        updateStatusUser("offline");
        super.onDestroy();
        Log.e("Main", "onDestroy");

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("Main", "onStart");
        updateStatusUser("online");


    }

    @Override
    protected void onPause() {
        super.onPause();


    }


    protected void updateStatusUser(String statusUser) {
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("status", statusUser);
        databaseReference.updateChildren(hashMap);
    }

    @Override
    public void onAvatarClicked() {
        binding.drawerLayout.openDrawer(GravityCompat.START);
    }

    public void countUnReadMessage() {
        databaseReference = FirebaseDatabase.getInstance().getReference("Message");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HashSet<String> hashSet = new HashSet<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Message message = snapshot.getValue(Message.class);

                    if (message.getReceiver().equals(firebaseUser.getUid()) && !message.getIsseen()) {
                        hashSet.add(message.getSender());
                    }
                }

                if (hashSet.size() > 0) {
                    binding.listViewpager.bottomNavi.getOrCreateBadge(R.id.item_chat).setNumber(hashSet.size());
                } else {
                    binding.listViewpager.bottomNavi.removeBadge(R.id.item_chat);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void countUnAddFriend() {
        databaseReference = FirebaseDatabase.getInstance().getReference("Friend");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HashSet<String> hashSet = new HashSet<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Friend friend = snapshot.getValue(Friend.class);
                    if (friend.getReceiver().equals(firebaseUser.getUid()) && !friend.isFriend()) {
                        hashSet.add(friend.getSender());
                    }
                }


                if (hashSet.size() != 0) {
                    binding.listViewpager.bottomNavi.getOrCreateBadge(R.id.item_friend).setNumber(hashSet.size());
                } else {
                    binding.listViewpager.bottomNavi.removeBadge(R.id.item_friend);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
