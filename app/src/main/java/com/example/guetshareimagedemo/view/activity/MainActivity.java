package com.example.guetshareimagedemo.view.activity;

import androidx.annotation.NonNull;
import androidx.core.app.NavUtils;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.guetshareimagedemo.R;
import com.example.guetshareimagedemo.databinding.ActivityMainBinding;
import com.example.guetshareimagedemo.view.fragment.HomeFragment;
import com.example.guetshareimagedemo.view.fragment.AttentionFragment;
import com.example.guetshareimagedemo.view.fragment.SpaceFragment;
import com.example.guetshareimagedemo.view.fragment.UpLoadFragment;
import com.example.guetshareimagedemo.view.fragment.UserFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends BaseActivity{

    private ActivityMainBinding binding;


    private HomeFragment homeFragment;
    private SpaceFragment spaceFragment;
    private UpLoadFragment upLoadFragment;
    private UserFragment userFragment;

    private Fragment lastFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        bindView();
        initFragment();
        switchFragment(homeFragment);
        initListener();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    private void initFragment() {
        homeFragment = new HomeFragment();
        spaceFragment = new SpaceFragment();
        upLoadFragment = new UpLoadFragment();
        //attentionFragment = new AttentionFragment();
        userFragment = new UserFragment();
    }

    private void switchFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (!fragment.isAdded()) {
            transaction.add(R.id.home_view, fragment);
        }else {
            transaction.show(fragment);
        }
        if (lastFragment != null && lastFragment != fragment){
            transaction.hide(lastFragment);
        }
        lastFragment = fragment;
        transaction.commit();

    }

    private void initListener() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        switchFragment(homeFragment);
                        break;
                    case R.id.space:
                        switchFragment(spaceFragment);
                        break;
                    case R.id.upload:
                        switchFragment(upLoadFragment);
                        break;
                    /*case R.id.message:
                        switchFragment(attentionFragment);
                        break;*/
                    case R.id.user_center:
                        switchFragment(userFragment);
                        break;
                }
                return true;
            }
        });
    }

    private void bindView() {
        binding.bottomNavigation.setItemIconTintList(null);
    }




}