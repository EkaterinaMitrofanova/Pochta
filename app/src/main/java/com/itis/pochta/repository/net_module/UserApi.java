package com.itis.pochta.repository.net_module;

import com.itis.pochta.model.base.Acceptor;
import com.itis.pochta.model.base.Driver;
import com.itis.pochta.model.request.LoginForm;
import com.itis.pochta.model.response.BaseResponse;
import com.itis.pochta.model.response.LoginResponseBody;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserApi {

    @POST("login")
    Observable<BaseResponse<LoginResponseBody>> login(@Body LoginForm loginForm);

    @GET("/user/{id}") //14
    Observable<BaseResponse<Acceptor>> getAcceptor(
            @Header("token") String token, @Path("id") long id);

    @GET("/user/{id}") //14
    Observable<BaseResponse<Driver>> getDriver(
            @Header("token") String token, @Path("id") long id);
}
