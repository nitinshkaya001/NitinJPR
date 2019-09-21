package com.e.driver.retrofit;


import com.e.driver.models.Category.ServiceResponse;
import com.e.driver.models.LoginMobile.OtpLoginResponse;
import com.e.driver.models.SubCategory.SubCategoryResponse;
import com.e.driver.models.SubmitOtp.SubmitOtpResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiInterface {


    @GET("Account/LoginByMobile")
     Call<OtpLoginResponse>otpLogin(@Query("PhoneNumber") String mobileNumber);

    @GET("Account/EnterOtp")
    Call<SubmitOtpResponse>submitOtp(@Query("PhoneNumber") String phoneNumber,@Query("Otp") String otp);


    @GET(value = "Customer/GetCategories")
     Call<ServiceResponse>getService();

    @GET("Customer/GetService")
    Call<SubCategoryResponse>getSubCategory(@Query("category_id")String category_id);

}