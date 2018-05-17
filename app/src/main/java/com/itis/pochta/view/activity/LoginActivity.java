package com.itis.pochta.view.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.itis.pochta.App;
import com.itis.pochta.R;
import com.itis.pochta.databinding.ActivityLoginBinding;
import com.itis.pochta.model.request.LoginForm;
import com.itis.pochta.repository.UserRepository;
import com.itis.pochta.repository.utils.ResponseLiveData;
import com.itis.pochta.util.DialogGenerator;

import javax.inject.Inject;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    private DialogGenerator dialogGenerator;

    @Inject
    UserRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getComponent().inject(this);

        dialogGenerator = new DialogGenerator(this);

        //before setContentView `cause may not show if logged in
        repository.isLoggedIn().observe(this, isLogged -> {
            if (isLogged != null && isLogged) {
                MainActivity.start(this, true);
                finish();
            }
        });

        //show layout if not logged in
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        binding.actionLogin.setOnClickListener(v -> {
            repository.getLoginResponse(getForm()).observe(
                    this,
                    responseBody -> {
                        //nothing, because handle it in isLoggedIn
                    },
                    status -> startLoading(
                            status == ResponseLiveData.Status.LOADING
                    ),
                    throwable ->
                            Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show()
            );
        });

    }

    private void startLoading(boolean start){
        if (start){
            dialogGenerator.showProgressDialog();
        } else {
            dialogGenerator.hideProgressDialog();
        }
    }

    private LoginForm getForm() {
        return new LoginForm(binding.login.getText().toString(), binding.password.getText().toString());
    }

}

