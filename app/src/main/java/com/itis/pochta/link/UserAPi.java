package com.itis.pochta.link;

import com.itis.pochta.model.request.LoginForm;
import com.itis.pochta.model.response.BaseResponse;
import com.itis.pochta.model.response.LoginResponseBody;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserAPi {

    @POST("login")
    Call<BaseResponse<LoginResponseBody>> login(@Body LoginForm loginForm);
}
