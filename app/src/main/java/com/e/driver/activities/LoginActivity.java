package com.e.driver.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.e.driver.R;
import com.e.driver.models.LoginMobile.OtpLoginResponse;
import com.e.driver.retrofit.RestClient;
import com.e.driver.retrofit.RetrofitClient;
import com.e.driver.utils.Constants;
import com.e.driver.utils.SamsPrefs;
import com.e.driver.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.edit_mobileNum)
    EditText edtMobNumber;
    @BindView(R.id.btn_otp)
    Button btnOtp;
    String mobileNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        btnOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginOtp();
            }
        });
    }

    private void LoginOtp() {
        mobileNo = edtMobNumber.getText().toString().trim();

        if (TextUtils.isEmpty(mobileNo.trim()) && mobileNo.length() <= 10) {

            Utils.displayToast(getApplicationContext(), "Please Enter Valid Mobile Number");
        }

        if (Utils.isInternetConnected(this)) {

            RestClient.otpLogin(mobileNo, new Callback<OtpLoginResponse>() {
                @Override
                public void onResponse(Call<OtpLoginResponse> call, Response<OtpLoginResponse> response) {

                    OtpLoginResponse otpLoginResponse = response.body();

                    if (otpLoginResponse != null) {

                        Intent intent = new Intent(LoginActivity.this, SubmitOtpActivity.class);
                        SamsPrefs.putString(getApplicationContext(), "mobilenumber", mobileNo);
                        startActivity(intent);
                    }

                }

                @Override
                public void onFailure(Call<OtpLoginResponse> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, "Failure", Toast.LENGTH_SHORT).show();

                }


            });


        }


    }
}
