package com.e.driver.retrofit;


import com.e.driver.models.Category.ServiceResponse;
import com.e.driver.models.GetState.StateResponse;
import com.e.driver.models.LoginEmail.LoginEmailResponse;
import com.e.driver.models.LoginMobile.LoginMobileNumberResponse;
import com.e.driver.models.SubCategory.SubCategoryResponse;
import com.e.driver.models.TimeSlote.TimeSloteResponse;
import com.e.driver.models.bookings.Bookings;
import com.e.driver.models.cities.CityListResponce;
import com.e.driver.models.submit_otp.LoginMobileOtpResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {


    @GET("api/Account/LoginByMobile")
    Call<LoginMobileNumberResponse> otpLogin(@Query("PhoneNumber") String mobileNumber);

    @GET("api/Account/EnterOtp")
    Call<LoginMobileOtpResponse> otpSubmit(@Query("PhoneNumber") String phonenumber, @Query("Otp") String otp);


    @Headers({"AuthToken: 12345", "LoginID: 1", "MobNo:  8586818454"})
    @POST("api/Customer/GetCategories")
    Call<ServiceResponse> getService();

    @GET("api/Customer/GetService")
    Call<SubCategoryResponse> getSubCategory(@Query("category_id") String category_id);


    @GET("api/Account/Login")
    Call<LoginEmailResponse> loginEmail(@Query("email") String email, @Query("password") String password);


    @GET("api/Booking/BookingHistory")
    Call<Bookings> getCustomerBookings(@Query("id") String id);

    @GET("api/Customer/GetTimeSlot")
    Call<TimeSloteResponse> getTime();

    @GET("api/Customer/GetState")
    Call<StateResponse> getState();

    @GET("api/Customer/GetCity")
    Call<CityListResponce> getCity(@Query("state_id") String st_id);

    @GET("api/Booking/BookService")
    Call<ResponseBody> bookNewService(@Query("cust_id") String cust_id,
                                      @Query("cust_name") String cust_name,
                                      @Query("cust_email") String cust_email,
                                      @Query("cust_login_mob") String cust_login_mob,
                                      @Query("cust_alter_mob") String cust_alter_mob,
                                      @Query("ctype_id") String ctype_id,
                                      @Query("cust_address") String cust_address,
                                      @Query("cust_landmark") String cust_landmark,
                                      @Query("cust_pincode") String cust_pincode,
                                      @Query("city_id") String city_id,
                                      @Query("state_id") String state_id,
                                      @Query("price") String price,
                                      @Query("prime_member_discount") String prime_member_discount,
                                      @Query("booking_date") String booking_date,
                                      @Query("time_slot_id") String time_slot_id,
                                      @Query("created_by") String created_by,
                                      @Query("modified_by") String modified_by,
                                      @Query("service_category_id") String service_category_id,
                                      @Query("service_list_id") String service_list_id);

}