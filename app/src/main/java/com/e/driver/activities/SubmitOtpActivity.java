package com.e.driver.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.e.driver.R;
import com.e.driver.models.submit_otp.LoginMobileOtpResponse;
import com.e.driver.retrofit.RestClient;
import com.e.driver.utils.Constants;
import com.e.driver.utils.SamsPrefs;
import com.e.driver.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubmitOtpActivity extends AppCompatActivity {

    String mobileNumber;
    @BindView(R.id.enterOtp)
    EditText submitOtp;
    @BindView(R.id.otp_submit)
    Button submitOtpBtn;
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_otp);
        ButterKnife.bind(this);
        getSupportActionBar().hide();


        submitOtpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                submitOtpApi();
            }
        });

    }

    private boolean submitOtpApi() {
        mobileNumber = SamsPrefs.getString(getApplicationContext(), Constants.MOBILE_NUMBER);
        String newOtp = submitOtp.getText().toString().trim();


        if (TextUtils.isEmpty(newOtp.trim()) || newOtp.length() < 6) {
            submitOtp.setError("Please Enter Valid OTP");
            return false;
        }
        if (!TextUtils.isEmpty(mobileNumber) && !TextUtils.isEmpty(newOtp)) {
            Utils.showProgressDialog(this);
            RestClient.enterOtpSubmit(mobileNumber, newOtp, new Callback<LoginMobileOtpResponse>() {

                @Override
                public void onResponse(Call<LoginMobileOtpResponse> call, Response<LoginMobileOtpResponse> response) {
                    Utils.dismissProgressDialog();
                    LoginMobileOtpResponse loginMobileOtpResponse = response.body();

                    intent = new Intent(SubmitOtpActivity.this, DashBoardNewactivity.class);
                    SamsPrefs.putBoolean(SubmitOtpActivity.this, Constants.LOGGEDIN, true);
                    SamsPrefs.putString(SubmitOtpActivity.this, Constants.MOBILE_NUMBER, loginMobileOtpResponse.getData().getCustomer().getMobileNo());
                    SamsPrefs.putString(SubmitOtpActivity.this, Constants.ROLE, loginMobileOtpResponse.getData().getCustomer().getRoleID());
                    SamsPrefs.putString(getApplicationContext(), Constants.CTYPE_ID, loginMobileOtpResponse.getData().getCustomer().getCustomerTypeID());
                    SamsPrefs.putString(getApplicationContext(), Constants.NAME, loginMobileOtpResponse.getData().getCustomer().getUserName());
                    startActivity(intent);

                }

                @Override
                public void onFailure(Call<LoginMobileOtpResponse> call, Throwable t) {
                    Toast.makeText(SubmitOtpActivity.this, "fail", Toast.LENGTH_SHORT).show();

                }
            });
        } else {
            Toast.makeText(SubmitOtpActivity.this, "Unable to login, try again later!", Toast.LENGTH_LONG).show();
        }

        return true;


    }
}