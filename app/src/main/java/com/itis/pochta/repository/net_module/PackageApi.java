package com.itis.pochta.repository.net_module;

import com.itis.pochta.model.base.City;
import com.itis.pochta.model.base.MyPackage;
import com.itis.pochta.model.request.PackageForm;
import com.itis.pochta.model.response.BaseResponse;
import com.itis.pochta.model.response.CitiesResponse;
import com.itis.pochta.model.response.PackageResponse;
import com.itis.pochta.model.response.StoragesResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PackageApi {

    @GET("/city")
    Observable<BaseResponse<CitiesResponse>> getCities();

    @GET("/storage")
    Observable<BaseResponse<StoragesResponse>> getStorages(
            @Query("city_id") String country, @Query("city") String city);

    @POST("package")
    Observable<BaseResponse<PackageResponse>> issuePackage(
            @Header("token") String token, @Body PackageForm packageForm);

    @GET("/package")
    Observable<BaseResponse<MyPackage>> getPackage(@Query("ticket") String ticket);
}
