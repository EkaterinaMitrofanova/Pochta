package com.itis.pochta.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.itis.pochta.R;
import com.itis.pochta.databinding.ActivityLoginBinding;
import com.itis.pochta.model.request.LoginForm;

public class LoginActivity extends Activity {

    private ActivityLoginBinding binding;
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
    }

    public void login(View view) {
        //todo: Login
        startActivity(new Intent(this, MainActivity.class));
    }

    public LoginForm getForm(){
        return new LoginForm(binding.login.getText().toString(), binding.password.getText().toString());
    }

}

