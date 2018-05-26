package com.itis.pochta.repository.net_module;

import com.itis.pochta.model.base.MyPackage;
import com.itis.pochta.model.request.DeliverForm;
import com.itis.pochta.model.request.PackageForm;
import com.itis.pochta.model.request.PickUpForm;
import com.itis.pochta.model.response.BaseResponse;
import com.itis.pochta.model.response.CitiesResponse;
import com.itis.pochta.model.response.DeliverResponse;
import com.itis.pochta.model.response.OrdersResponse;
import com.itis.pochta.model.response.PackageResponse;
import com.itis.pochta.model.response.PackagesListResponse;
import com.itis.pochta.model.response.StoragesResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PackageApi {

    @GET("/city")
    Observable<BaseResponse<CitiesResponse>> getCities();

    @GET("/storage?type=0")
    Observable<BaseResponse<StoragesResponse>> getStorages(
            @Query("city_id") long country, @Query("city") String city);

    @GET("/storage?type=2")
    Observable<BaseResponse<StoragesResponse>> getStoragesForDriver(
            @Query("city_id") String country);

    @POST("package")
    Observable<BaseResponse<PackageResponse>> issuePackage(
            @Header("token") String token, @Body PackageForm packageForm);

    @GET("/package")
    Observable<BaseResponse<MyPackage>> getPackage(@Query("ticket") String ticket);

    @GET("/package/by_phone")
    Observable<BaseResponse<PackagesListResponse>> getPackagesByPhone(
            @Query("phone") String phone, @Query("storage") long storageId);

    @GET("/package/pick_up")
    Observable<BaseResponse<OrdersResponse>> getOrders(
            @Header("token") String token, @Query("storage") long storageId);

    @POST("package/pick_up")
    Observable<BaseResponse<Void>> pickUp(
            @Header("token") String token, @Body PickUpForm pickUpForm, @Query("storage") long storage);

    @POST("/package/accept")
    Observable<BaseResponse<Void>> acceptPackage(@Header("token") String token, @Query("ticket") String ticket);

    @GET("/package/driver")
    Observable<BaseResponse<OrdersResponse>> getDriverOrders(@Header("token") String token);

    @POST("package/deliver")
    Observable<BaseResponse<DeliverResponse>> deliver(@Header("token") String token, @Body DeliverForm deliverForm);

}
