package com.itis.pochta.view.activity;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.itis.pochta.App;
import com.itis.pochta.R;
import com.itis.pochta.databinding.ActivityMainBinding;
import com.itis.pochta.repository.UserRepository;
import com.itis.pochta.util.DialogGenerator;
import com.itis.pochta.view.ViewListener;
import com.itis.pochta.view.fragment.TrackingFragment;
import com.itis.pochta.view.fragment.PackageFragment;
import com.itis.pochta.view.fragment.ProfileFragment;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements ViewListener,
        BottomNavigationView.OnNavigationItemSelectedListener {

    public static final String TAG_CREATE_ORDER = "com.itis.pochta.view.fragment.create_order";
    public static final String TAG_PROFILE = "com.itis.pochta.view.fragment.profile";
    public static final String TAG_TRACKING = "com.itis.pochta.view.fragment.tracking";

    private static final String KEY_CURRENT_FRAGMENT = "com.itis.pochta.view.curr_fragment";
    private String currentFragment = null;

    private DialogGenerator dialogGenerator;

    private ActivityMainBinding binding;

    @Inject
    UserRepository repository;

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

        App.getComponent().inject(this);

        initViews();

        repository.getLoginResponse(null).observe(
                this,
                loginResponseBody -> fillBottomNavigation(loginResponseBody.getRole()),
                status -> {},
                throwable -> {});

        binding.bottomNavigation.setOnNavigationItemSelectedListener(this);

        if (savedInstanceState != null){
            currentFragment = savedInstanceState.getString(KEY_CURRENT_FRAGMENT);
        }
    }

    private void initViews(){
        setSupportActionBar(binding.toolbarLayout.toolbar);
        getSupportActionBar().setTitle(R.string.title);

        dialogGenerator = new DialogGenerator(this);
    }

    private void fillBottomNavigation(String role){
        if (role.equals("ACCEPTOR")){
            binding.bottomNavigation.inflateMenu(R.menu.menu_acceptor);
        } else {
            binding.bottomNavigation.inflateMenu(R.menu.menu_driver);
        }
        if (currentFragment == null){
            startProfile();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_tracking: {
                startTracking();
                return true;
            }
            case R.id.nav_profile: {
                startProfile();
                return true;
            }
            case R.id.nav_create_order: {
                startPackage();
                return true;
            }
        }
        return false;
    }

    private void startTracking() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        TrackingFragment fragment = (TrackingFragment) getFragmentManager().findFragmentByTag(TAG_TRACKING);
        if (fragment != null){
            if (currentFragment.equals(TAG_TRACKING)){
                return;
            }
        } else {
            fragment = new TrackingFragment();
        }
        transaction.replace(R.id.fragment_container, fragment, TAG_TRACKING).addToBackStack(TAG_TRACKING);
        transaction.commit();
    }

    private void startPackage(){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        PackageFragment fragment = (PackageFragment) getFragmentManager().findFragmentByTag(TAG_CREATE_ORDER);
        if (fragment != null){
            if (currentFragment.equals(TAG_CREATE_ORDER)){
                return;
            }
        } else {
            fragment = new PackageFragment();
        }
        transaction.replace(R.id.fragment_container, fragment, TAG_CREATE_ORDER).addToBackStack(TAG_CREATE_ORDER);
        transaction.commit();
    }

    private void startProfile(){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        ProfileFragment fragment = (ProfileFragment) getFragmentManager().findFragmentByTag(TAG_PROFILE);
        if (fragment != null){
            if (currentFragment.equals(TAG_PROFILE)){
                return;
            }
        } else {
            fragment = new ProfileFragment();
        }
        transaction.replace(R.id.fragment_container, fragment, TAG_PROFILE).addToBackStack(TAG_PROFILE);
        transaction.commit();
    }

    @Override
    public void setFragment(String tag) {
        currentFragment = tag;
        setToolbarViews();
    }

    @Override
    public void setTitle(int titleId) {
        getSupportActionBar().setTitle(titleId);
    }

    @Override
    public void startLoading(boolean start){
        if (start){
            dialogGenerator.showProgressDialog();
        } else {
            dialogGenerator.hideProgressDialog();
        }
    }

    private void setToolbarViews(){
        switch (currentFragment){
            case TAG_CREATE_ORDER: {
                binding.bottomNavigation.setSelectedItemId(R.id.nav_create_order);
                break;
            }
            case TAG_PROFILE: {
                binding.bottomNavigation.setSelectedItemId(R.id.nav_profile);
                break;
            }
            case TAG_TRACKING: {
                binding.bottomNavigation.setSelectedItemId(R.id.nav_tracking);
                break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        int count = getFragmentManager().getBackStackEntryCount();
        System.out.println("-----onBackPressed()------ COUNT = " + count);
        if (count == 1){
            dialogGenerator.showExitDialog();
            return;
        }
        super.onBackPressed();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_CURRENT_FRAGMENT, currentFragment);
    }
}
