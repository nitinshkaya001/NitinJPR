package com.e.driver.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.e.driver.R;
import com.e.driver.models.LoginEmail.Customer;
import com.e.driver.models.LoginEmail.LoginEmailResponse;
import com.e.driver.models.LoginMobile.LoginMobileNumberResponse;
import com.e.driver.retrofit.RestClient;
import com.e.driver.utils.SamsPrefs;
import com.e.driver.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.edit_mobileNum)
    EditText edtMobNumber;
    @BindView(R.id.btn_otp)
    Button btnOtp;
    String mobileNo;

    @BindView(R.id.emailid)
    EditText emailId;
    @BindView(R.id.password)
    EditText password;

    @BindView(R.id.loginBtn)
    Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        getSupportActionBar().hide();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginWithEmail();

            }
        });

        btnOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginOtp();
            }
        });
    }

    public boolean LoginOtp() {

        mobileNo = edtMobNumber.getText().toString().trim();

        if (TextUtils.isEmpty(mobileNo.trim()) && edtMobNumber.length() <= 10) {
            edtMobNumber.setError("Please Enter Valid Mobile Number");

            return false;
        }
        if (true) {
            if (Utils.isInternetConnected(this)) {

                Utils.showProgressDialog(this);
                RestClient.otpLogin(mobileNo, new Callback<LoginMobileNumberResponse>() {
                    @Override
                    public void onResponse(Call<LoginMobileNumberResponse> call, Response<LoginMobileNumberResponse> response) {
                        Utils.dismissProgressDialog();


                        if (response.body().getStatusType().equalsIgnoreCase("Success")) {
                            Intent intent = new Intent(LoginActivity.this, SubmitOtpActivity.class);
                            SamsPrefs.putString(getApplicationContext(), "mobileNumber", mobileNo);
                            startActivity(intent);


                        } else  {
                            Toast.makeText(LoginActivity.this, "eter valid otp", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<LoginMobileNumberResponse> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "Failure", Toast.LENGTH_SHORT).show();

                    }


                });


            }
        }

        return false;
    }

    public boolean loginWithEmail() {
        String email = emailId.getText().toString().trim();
        String passwordd = password.getText().toString().trim();

        if (TextUtils.isEmpty(email.trim())) {
            emailId.setError("Please Enter Valid Mobile Number");

            return false;
        }
        if (TextUtils.isEmpty(passwordd.trim()) && password.length() <= 6) {
            password.setError("Please Enter Valid Mobile Number");

            return false;
        }
        if (true) {
            if (email != null && passwordd != null) {
                Utils.showProgressDialog(this);
                RestClient.enterEmailId(email, passwordd, new Callback<LoginEmailResponse>() {
                    @Override
                    public void onResponse(Call<LoginEmailResponse> call, Response<LoginEmailResponse> response) {
                        Utils.dismissProgressDialog();
                        if (response.body() != null) {




                            Customer customer = response.body().getData().getCustomer();
                            if (customer.getRoleID().equalsIgnoreCase("3") || (customer.getRoleID().equalsIgnoreCase("1"))) {
                                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                                startActivity(intent);
                                String Mobile=customer.getMobileNo();
                                String Email=customer.getUserName();

                                SamsPrefs.putString(getApplicationContext(),"mobileNumber",Mobile);
                                SamsPrefs.putString(getApplicationContext(),"emailId",Email);
                            }
                            else if (customer.getRoleID().equalsIgnoreCase("2")){

                                Intent intent = new Intent(LoginActivity.this, BookRequestActivity.class);
                                startActivity(intent);
                            }


                        }

                    }


                    @Override
                    public void onFailure(Call<LoginEmailResponse> call, Throwable t) {

                    }
                });


            }


        }
        return true;
    }
}
