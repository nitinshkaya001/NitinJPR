package com.e.driver.retrofit;


import com.e.driver.models.Category.ServiceResponse;
import com.e.driver.models.LoginEmail.LoginEmailResponse;
import com.e.driver.models.LoginMobile.LoginMobileNumberResponse;
import com.e.driver.models.SubCategory.SubCategoryResponse;
import com.e.driver.models.submit_otp.LoginMobileOtpResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {


    @GET("Account/LoginByMobile")
     Call<LoginMobileNumberResponse>otpLogin(@Query("PhoneNumber") String mobileNumber);

    @GET("http://samarthansapi.the-sams.com/api/Account/EnterOtp")
    Call<LoginMobileOtpResponse>otpSubmit(@Query("PhoneNumber") String phonenumber, @Query("Otp") String otp);


    @Headers({"AuthToken: 12345", "LoginID: 1", "MobNo:  8586818454"})
    @POST( "Customer/GetCategories")
     Call<ServiceResponse>getService();

    @GET("Customer/GetService")
    Call<SubCategoryResponse>getSubCategory(@Query("category_id") String category_id);


    @GET("http://samarthansapi.the-sams.com/api/Account/Login")
    Call<LoginEmailResponse>loginEmail(@Query("email") String email, @Query("password") String password);


}