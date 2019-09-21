package com.e.driver.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.e.driver.R;
import com.e.driver.models.SubmitOtp.SubmitOtpResponse;
import com.e.driver.retrofit.RestClient;
import com.e.driver.utils.SamsPrefs;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubmitOtpActivity extends AppCompatActivity {

    String mobileNumber;
   @BindView(R.id.submitOtp)
    EditText submitOtp;
   @BindView(R.id.otp_submit)
    Button submitOtpBtn;

   String otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_otp);
        ButterKnife.bind(this);
        mobileNumber= SamsPrefs.getString(getApplicationContext(),"mobilenumber");

        otp=submitOtp.getText().toString().trim();

        submitOtpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RestClient.submitSamOtp(mobileNumber, otp, new Callback<SubmitOtpResponse>() {
                    @Override
                    public void onResponse(Call<SubmitOtpResponse> call, Response<SubmitOtpResponse> response) {
                        Toast.makeText(SubmitOtpActivity.this, "success", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(Call<SubmitOtpResponse> call, Throwable t) {
                        Toast.makeText(SubmitOtpActivity.this, "fail", Toast.LENGTH_SHORT).show();

                    }
                });


            }
        });




    }
}
