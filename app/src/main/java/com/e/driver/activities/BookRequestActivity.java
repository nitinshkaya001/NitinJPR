package com.e.driver.activities;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.e.driver.R;
import com.e.driver.models.GetState.StateResponse;
import com.e.driver.models.TimeSlote.TimeSloteResponse;
import com.e.driver.retrofit.RestClient;
import com.e.driver.utils.SamsPrefs;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookRequestActivity extends AppCompatActivity {
    @BindView(R.id.choseCategory)
    EditText choseCategory;
    @BindView(R.id.choseService)
    EditText choseService;
    @BindView(R.id.choseDate)
    EditText choseDate;
    @BindView(R.id.choseTime)
    EditText choseTime;
    @BindView(R.id.enterName)
    EditText enterName;
    @BindView(R.id.enterMobile)
    EditText enterMobile;
    @BindView(R.id.enterEmail)
    EditText enterEmail;
    @BindView(R.id.alterMobileNum)
    EditText alterMobileNum;
    @BindView(R.id.enterLandmark)
    EditText enterLandmark;
    @BindView(R.id.enterPincode)
    EditText enterPincode;
    @BindView(R.id.choseState)
    EditText choseState;
    @BindView(R.id.choseCity)
    EditText choseCity;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    Calendar calendar;
    String SubCatName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_request);
        getSupportActionBar().hide();
        ButterKnife.bind(this);

        choseCategory.setText(SamsPrefs.getString(getApplicationContext(),"categoryName"));
        //choseCategory.setEnabled(false);
        choseService.setText(SamsPrefs.getString(getApplicationContext(),"subCategoryName"));
        //choseService.setEnabled(false);

        choseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getTimeSlot();
            }
        });

        choseState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getSelectState();

            }
        });

         calendar=Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date=new DatePickerDialog.OnDateSetListener() {
    @Override
    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth)
    {
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,monthOfYear);
        calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        updateLabel();
    }
        };
      choseDate.setOnTouchListener(new View.OnTouchListener() {
          @Override
          public boolean onTouch(View view, MotionEvent motionEvent) {

              new DatePickerDialog(BookRequestActivity.this,date,calendar.get(Calendar.YEAR),
                      calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();

              return false;
          }
      });

    }


    private void updateLabel() {

        String myFormat="MM/dd/yyyy";
        SimpleDateFormat sdf=new SimpleDateFormat(myFormat,Locale.UK);
        choseDate.setText(sdf.format(calendar.getTime()));

    }

    private void getTimeSlot() {

        RestClient.timeSlot(new Callback<TimeSloteResponse>() {
            @Override
            public void onResponse(Call<TimeSloteResponse> call, Response<TimeSloteResponse> response) {

                TimeSloteResponse timeSloteResponse=response.body();
                if (timeSloteResponse.getStatusType().equalsIgnoreCase("Success")){



                }

            }

            @Override
            public void onFailure(Call<TimeSloteResponse> call, Throwable t) {

            }
        });

    }

    private void getSelectState() {

        RestClient.selectState(new Callback<StateResponse>() {
            @Override
            public void onResponse(Call<StateResponse> call, Response<StateResponse> response) {

                StateResponse stateResponse=response.body();
                if (stateResponse.getStatusType().equalsIgnoreCase("Success")){



                }


            }

            @Override
            public void onFailure(Call<StateResponse> call, Throwable t) {

            }
        });
    }



}
