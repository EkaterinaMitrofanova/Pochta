package com.itis.pochta.view.activity;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;


import com.itis.pochta.R;
import com.itis.pochta.databinding.ActivityMainBinding;
import com.itis.pochta.databinding.ToolbarBinding;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    public static final String TAG_CREATE_ORDER = "com.itis.pochta.view.fragment.create_order";

    private ActivityMainBinding binding;
    private ToolbarBinding toolbarBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        setSupportActionBar(toolbarBinding.toolbar);
        getSupportActionBar().setTitle(R.string.title);

        binding.bottomNavigation.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_tracking: {
                break;
            }
            case R.id.action_profile: {
                break;
            }
            case R.id.action_create_order: {
                break;
            }
        }
        return false;
    }
}
