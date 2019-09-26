package com.e.driver.activities;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.e.driver.R;
import com.e.driver.adapters.CategoryBookingAdapter;
import com.e.driver.adapters.CityBookingAdapter;
import com.e.driver.adapters.StateBookingAdapter;
import com.e.driver.adapters.SubCatBookingAdapter;
import com.e.driver.adapters.TimeSlotBookingAdapter;
import com.e.driver.models.Category.ServiceResponse;
import com.e.driver.models.GetState.StateResponse;
import com.e.driver.models.SubCategory.SubCategoryResponse;
import com.e.driver.models.TimeSlote.TimeSloteResponse;
import com.e.driver.models.cities.CityListResponce;
import com.e.driver.retrofit.RestClient;
import com.e.driver.utils.SamsPrefs;
import com.e.driver.utils.Utils;

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
    Spinner choseCategory;
    @BindView(R.id.choseService)
    Spinner choseService;
    @BindView(R.id.choseDate)
    EditText choseDate;
    @BindView(R.id.choseTime)
    Spinner choseTime;
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
    Spinner choseState;
    @BindView(R.id.choseCity)
    Spinner choseCity;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    Calendar calendar;
    String SubCatName;
    ServiceResponse serviceResponse;
    private String categoryName, cat_id;
    SubCategoryResponse subCategoryResponse;
    private String subCategoryName, sub_cat_id;
    private String stateName, state_id;
    private String cityName, city_id;
    private String slotName, slot_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_request);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        getAllcategories();

        getSelectState();
        getTimeSlot();

        calendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };
        choseDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                new DatePickerDialog(BookRequestActivity.this, date, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();

                return false;
            }
        });

    }


    private void updateLabel() {

        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);
        choseDate.setText(sdf.format(calendar.getTime()));

    }

    private void getTimeSlot() {

        RestClient.timeSlot(new Callback<TimeSloteResponse>() {
            @Override
            public void onResponse(Call<TimeSloteResponse> call, Response<TimeSloteResponse> response) {

                TimeSloteResponse timeSloteResponse = response.body();
                if (timeSloteResponse.getStatusType().equalsIgnoreCase("Success")) {
                    final TimeSloteResponse stateResponse = response.body();
                    if (stateResponse.getStatusType().equalsIgnoreCase("Success") && stateResponse.getData() != null
                            && stateResponse.getData().getTimeSlots() != null
                            && stateResponse.getData().getTimeSlots().size() > 0) {
                        TimeSlotBookingAdapter timeSlotBookingAdapter = new TimeSlotBookingAdapter(BookRequestActivity.this);
                        timeSlotBookingAdapter.setTimeSlots(stateResponse.getData().getTimeSlots());
                        choseTime.setAdapter(timeSlotBookingAdapter);
                        choseTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                slotName = stateResponse.getData().getTimeSlots().get(position).getTimeSlotName();
                                slot_id = stateResponse.getData().getTimeSlots().get(position).getTimeSlotId();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                    }

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

                final StateResponse stateResponse = response.body();
                if (stateResponse.getStatusType().equalsIgnoreCase("Success") && stateResponse.getData() != null && stateResponse.getData().getStates() != null && stateResponse.getData().getStates().size() > 0) {
                    StateBookingAdapter stateBookingAdapter = new StateBookingAdapter(BookRequestActivity.this);
                    stateBookingAdapter.setCatList(stateResponse.getData().getStates());
                    choseState.setAdapter(stateBookingAdapter);
                    choseState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            stateName = stateResponse.getData().getStates().get(position).getStateName();
                            state_id = stateResponse.getData().getStates().get(position).getStateId();
                            getCities(state_id);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                }


            }

            @Override
            public void onFailure(Call<StateResponse> call, Throwable t) {

            }
        });
    }

    private void getCities(String state_id) {

        RestClient.selectCity(state_id, new Callback<CityListResponce>() {
            @Override
            public void onResponse(Call<CityListResponce> call, Response<CityListResponce> response) {

                final CityListResponce cityListResponce = response.body();
                if (cityListResponce.getStatusType().equalsIgnoreCase("Success")
                        && cityListResponce.getData() != null && cityListResponce.getData().getCityList() != null
                        && cityListResponce.getData().getCityList().size() > 0) {
                    CityBookingAdapter stateBookingAdapter = new CityBookingAdapter(BookRequestActivity.this);
                    stateBookingAdapter.setCityList(cityListResponce.getData().getCityList());
                    choseCity.setAdapter(stateBookingAdapter);
                    choseCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            cityName = cityListResponce.getData().getCityList().get(position).getCityName();
                            city_id = cityListResponce.getData().getCityList().get(position).getCityId();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                }


            }

            @Override
            public void onFailure(Call<CityListResponce> call, Throwable t) {

            }
        });
    }

    private void getAllcategories() {
        RestClient.getService(new Callback<ServiceResponse>() {
            @Override
            public void onResponse(Call<ServiceResponse> call, Response<ServiceResponse> response) {
                Utils.dismissProgressDialog();
                if (response.code() == 200) {
                    serviceResponse = response.body();
                    if (serviceResponse.getStatusType().equalsIgnoreCase("Success") && serviceResponse.getData().getCategories() != null) {
                        CategoryBookingAdapter categoryBookingAdapter = new CategoryBookingAdapter(BookRequestActivity.this);
                        categoryBookingAdapter.setCatList(serviceResponse.getData().getCategories());
                        choseCategory.setAdapter(categoryBookingAdapter);
                        choseCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                categoryName = serviceResponse.getData().getCategories().get(position).getCategoryName();
                                cat_id = serviceResponse.getData().getCategories().get(position).getCategoryId();
                                getSubCategory(cat_id);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<ServiceResponse> call, Throwable t) {
                Utils.dismissProgressDialog();
            }
        });

    }


    private void getSubCategory(String cat_id) {
        RestClient.getSubCategory(cat_id, new Callback<SubCategoryResponse>() {
            @Override
            public void onResponse(Call<SubCategoryResponse> call, Response<SubCategoryResponse> response) {
                if (response.code() == 200) {
                    Utils.dismissProgressDialog();
                    subCategoryResponse = response.body();

                    if (subCategoryResponse != null && subCategoryResponse.getData().getServiceList() != null) {
                        SubCatBookingAdapter subCatBookingAdapter = new SubCatBookingAdapter(BookRequestActivity.this);
                        subCatBookingAdapter.setSubCatList(subCategoryResponse.getData().getServiceList());
                        choseService.setAdapter(subCatBookingAdapter);
                        choseService.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                subCategoryName = subCategoryResponse.getData().getServiceList().get(position).getServiceName();
                                sub_cat_id = subCategoryResponse.getData().getServiceList().get(position).getServiceListId();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<SubCategoryResponse> call, Throwable t) {
                Utils.dismissProgressDialog();
            }
        });

    }


}
