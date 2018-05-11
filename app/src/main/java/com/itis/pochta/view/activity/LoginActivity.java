package com.itis.pochta.view.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.itis.pochta.App;
import com.itis.pochta.R;
import com.itis.pochta.databinding.ActivityLoginBinding;
import com.itis.pochta.model.request.LoginForm;
import com.itis.pochta.repository.UserRepository;

import javax.inject.Inject;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Inject
    UserRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        App.getComponent().inject(this);

        binding.actionLogin.setOnClickListener(v -> {
            repository.logIn(getForm());
        });

        repository.isLoggedIn().observe(this, isLogged -> {
            if (isLogged != null && isLogged){
                MainActivity.start(this, true);
                finish();
            }
        });

        repository.getStatusLiveData().observe(this, statuses -> {
            switch (statuses){
                case HANDLE:
                    binding.loginProgress.setVisibility(View.GONE);
                    break;
                case LOADING:
                    binding.loginProgress.setVisibility(View.VISIBLE);
                    break;
            }
        });

        repository.getErrorLiveData().observe(this, s -> {
            if (s != null) {
                Log.e("LOGIN", s);
                Toast.makeText(this, s, Toast.LENGTH_LONG).show();
            }
        });
    }

    private LoginForm getForm(){
        return new LoginForm(binding.login.getText().toString(), binding.password.getText().toString());
    }

}

