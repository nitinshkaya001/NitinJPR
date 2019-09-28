package com.e.driver.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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
import com.e.driver.utils.Constants;
import com.e.driver.utils.SamsPrefs;
import com.e.driver.utils.Utils;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
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

    @BindView(R.id.toolbar)
    Toolbar toolbar;
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
    private DatePickerDialog picker;

    String cust_id;
    String cust_name;
    String cust_email;
    String cust_login_mob;
    String cust_alter_mob;
    String ctype_id;
    String cust_address;
    String cust_landmark;
    String cust_pincode;
    String price;
    String prime_member_discount = "NO";
    String booking_date;
    String time_slot_id;
    String created_by = "";//blank
    String modified_by = "";//blank
    String service_category_id;
    String service_list_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_request);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        prepopulateData();


        getAllcategories();
        getSelectState();
        getTimeSlot();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInputs()) {
                    bookService();
                }
            }
        });
        choseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(BookRequestActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                choseDate.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);
                                booking_date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                            }
                        }, year, month, day);
                picker.getDatePicker().setMinDate(System.currentTimeMillis());
                picker.show();
            }
        });
    }

    private boolean validateInputs() {
        cust_login_mob = enterMobile.getText().toString();
        cust_alter_mob = alterMobileNum.getText().toString();
        booking_date = choseDate.getText().toString();
        cust_email = enterEmail.getText().toString();
        cust_name = enterName.getText().toString();
        cust_address = cust_landmark = enterLandmark.getText().toString();
        cust_pincode = enterPincode.getText().toString();
        if (TextUtils.isEmpty(cust_id)) {
            Toast.makeText(this, "Please login first", Toast.LENGTH_LONG).show();
            return false;
        }

        if (TextUtils.isEmpty(cust_name)) {
            Toast.makeText(this, "Please enter name", Toast.LENGTH_LONG).show();
            return false;
        }
        if (TextUtils.isEmpty(cust_email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();
            return false;
        }
        if (TextUtils.isEmpty(cust_login_mob)) {
            Toast.makeText(this, "Please enter mobile", Toast.LENGTH_LONG).show();
            return false;
        }

        if (TextUtils.isEmpty(cust_alter_mob)) {
            Toast.makeText(this, "Please enter alternate mobile", Toast.LENGTH_LONG).show();
            return false;
        }

        if (TextUtils.isEmpty(cust_address)) {
            Toast.makeText(this, "Please enter address", Toast.LENGTH_LONG).show();
            return false;
        }

        if (TextUtils.isEmpty(cust_landmark)) {
            Toast.makeText(this, "Please enter landmark", Toast.LENGTH_LONG).show();
            return false;
        }

        if (TextUtils.isEmpty(cust_pincode)) {
            Toast.makeText(this, "Please enter pincode", Toast.LENGTH_LONG).show();
            return false;
        }

        if (TextUtils.isEmpty(booking_date)) {
            Toast.makeText(this, "Please enter booking date", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void prepopulateData() {
        cust_id = "" + SamsPrefs.getString(this, Constants.CUST_ID);
        cust_name = "" + SamsPrefs.getString(this, Constants.NAME);
        cust_login_mob = "" + SamsPrefs.getString(this, Constants.MOBILE_NUMBER);
        cust_email = "" + SamsPrefs.getString(this, Constants.EMAIL);
        ctype_id = "" + SamsPrefs.getString(this, Constants.CTYPE_ID);
        enterName.setText(cust_name);
        enterMobile.setText(cust_login_mob);
        enterEmail.setText(cust_email);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        onBackPressed();

        return super.onOptionsItemSelected(item);
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
                                time_slot_id = slot_id;
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
                                service_category_id = cat_id;
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
                                price = subCategoryResponse.getData().getServiceList().get(position).getPrice();
                                service_list_id = sub_cat_id;
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


    //http://samarthansapi.the-sams.com/api/Booking/BookService?=2&
// =Pushpendra&
// =pushpendra@gmail.com
// &=8445573535
// &=7599333614
// &=1
// &=Bareilly
// &=Near Hartman College
// &=243122
// &=1
// &=1
// &=199&
// =NO
// &=09/24/2019
// &=1&
// =2
//
//
//
// &=2
// &=1
// &=1
    private void bookService() {

        Utils.showProgressDialog(BookRequestActivity.this);
        RestClient.bookService(cust_id, cust_name, cust_email, cust_login_mob, cust_alter_mob,
                ctype_id, cust_address, cust_landmark, cust_pincode, city_id, state_id, price,
                prime_member_discount, booking_date, time_slot_id, "2", "2",
                service_category_id, service_list_id, new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Utils.dismissProgressDialog();
                        Toast.makeText(BookRequestActivity.this, "Service booked successfully", Toast.LENGTH_LONG).show();
                        Log.d("Booking Response", response.body().toString());
                        finish();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Utils.dismissProgressDialog();
                        Toast.makeText(BookRequestActivity.this, "Unable to book service, please try again later", Toast.LENGTH_LONG).show();

                    }
                });


    }

}