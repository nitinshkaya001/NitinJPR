package com.e.driver.activities;


import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.e.driver.adapters.CategoryAdapter;
import com.e.driver.R;
import com.e.driver.models.Category.ServiceResponse;
import com.e.driver.retrofit.RestClient;
import com.e.driver.utils.Constants;
import com.e.driver.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements CategoryAdapter.OnClickCategoryClick {

    ServiceResponse serviceResponse;
    RecyclerView rv_service;
    private Context context;
    private  CategoryAdapter categoryAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        rv_service = findViewById(R.id.rv_service);
        serviceResponse = new ServiceResponse();

        //setAdapter
        rv_service.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
        categoryAdapter = new CategoryAdapter(MainActivity.this);
        categoryAdapter.setOnCategoryClickListener(this);
        rv_service.setAdapter(categoryAdapter);

        if (Utils.isInternetConnected(this)) {
            Utils.showProgressDialog(this);
            getAllcategories();
        } else {
            Toast.makeText(this, "Please check your internet connection", Toast.LENGTH_LONG).show();
        }
    }

    private void getAllcategories() {
        RestClient.getService(new Callback<ServiceResponse>() {
            @Override
            public void onResponse(Call<ServiceResponse> call, Response<ServiceResponse> response) {


                Utils.dismissProgressDialog();
                if (response.code() == 200) {
                    serviceResponse = response.body();

                    if (serviceResponse.getStatusType().equalsIgnoreCase("Success")  && serviceResponse.getData().getCategories() != null) {
                        categoryAdapter.setCategryData(serviceResponse.getData().getCategories());
                        categoryAdapter.notifyDataSetChanged();
                    }
                    Log.d("msg", "data ");
                }
            }

            @Override
            public void onFailure(Call<ServiceResponse> call, Throwable t) {
                Utils.dismissProgressDialog();
                Log.d("msg", t.getMessage().toString());

            }
        });

    }

    @Override
    public void onCategoryClick(int position) {
        Intent intent = new Intent(this,SubCategoryActivity.class);
        intent.putExtra(Constants.CAT_ID,serviceResponse.getData().getCategories().get(position).getCategoryId());
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }
}
