package com.itis.pochta.repository.net_module;

import com.itis.pochta.model.request.LoginForm;
import com.itis.pochta.model.response.BaseResponse;
import com.itis.pochta.model.response.LoginResponseBody;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserApi {

    @POST("login")
    Observable<BaseResponse<LoginResponseBody>> login(@Body LoginForm loginForm);
}
