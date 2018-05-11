package com.itis.pochta.repository.net_module;

import com.itis.pochta.model.request.PackageForm;
import com.itis.pochta.model.response.BaseResponse;
import com.itis.pochta.model.response.PackageResponse;
import com.itis.pochta.model.response.StoragesResponseBody;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PackageApi {

    @GET("/storage")
    Observable<BaseResponse<StoragesResponseBody>> getStorages(
            @Query("country") String country, @Query("city") String city);

    @POST("package")
    Observable<BaseResponse<PackageResponse>> issuePackage(
            @Header("token") String token, @Body PackageForm packageForm);

}
