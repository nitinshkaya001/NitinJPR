package com.e.driver.retrofit;

import com.e.driver.models.Category.ServiceResponse;
import com.e.driver.models.GetState.StateResponse;
import com.e.driver.models.LoginEmail.LoginEmailResponse;
import com.e.driver.models.LoginMobile.LoginMobileNumberResponse;
import com.e.driver.models.SubCategory.SubCategoryResponse;
import com.e.driver.models.TimeSlote.TimeSloteResponse;
import com.e.driver.models.cities.CityListResponce;
import com.e.driver.models.submit_otp.LoginMobileOtpResponse;

import retrofit2.Callback;

public class RestClient {
    private static final String TAG = "RestClient";


   public  static void getService(Callback<ServiceResponse>callback) {
        RetrofitClient.getClient().getService().enqueue(callback);
}
    public static void getSubCategory(String category_id,Callback<SubCategoryResponse> callback){
       com.e.driver.retrofit.RetrofitClient.getClient().getSubCategory(category_id).enqueue(callback);
    }

    public static void otpLogin(String mobileNumber, Callback<LoginMobileNumberResponse> callback){
       RetrofitClient.getClient().otpLogin(mobileNumber).enqueue(callback);


    }
    public static void enterOtpSubmit(String phonenumber,String otp ,Callback<LoginMobileOtpResponse> callback) {
        RetrofitClient.getClient().otpSubmit(phonenumber,otp).enqueue(callback);
    }

    public static void enterEmailId(String email,String password ,Callback<LoginEmailResponse> callback) {
        RetrofitClient.getClient().loginEmail(email,password).enqueue(callback);
    }

    public static void timeSlot(Callback<TimeSloteResponse>callback){
                RetrofitClient.getClient().getTime().enqueue(callback);
    }

    public static void selectState(Callback<StateResponse>callback){

       RetrofitClient.getClient().getState().enqueue(callback);
    }

    public static void selectCity(String st_id,Callback<CityListResponce>callback){

       RetrofitClient.getClient().getCity(st_id).enqueue(callback);
    }



}
