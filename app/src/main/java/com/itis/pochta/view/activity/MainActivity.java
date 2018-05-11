package com.itis.pochta.view.activity;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;


import com.itis.pochta.R;
import com.itis.pochta.databinding.ActivityMainBinding;
import com.itis.pochta.view.fragment.PackageFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    public static final String TAG_CREATE_ORDER = "com.itis.pochta.view.fragment.create_order";

    private ActivityMainBinding binding;

    public static void start(Context context, boolean clearTop) {
        Intent starter = new Intent(context, MainActivity.class);
        if (clearTop){
            starter.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(binding.toolbarLayout.toolbar);
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
                startPackage();
                break;
            }
        }
        return false;
    }

    private void startPackage(){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        PackageFragment fragment = (PackageFragment) getFragmentManager().findFragmentByTag(TAG_CREATE_ORDER);
        if (fragment != null){
            return;
        }
        fragment = new PackageFragment();
        transaction.replace(R.id.fragment_container, fragment, TAG_CREATE_ORDER).addToBackStack(TAG_CREATE_ORDER);
        transaction.commit();
        getSupportActionBar().setTitle(R.string.title_package);
    }
}
