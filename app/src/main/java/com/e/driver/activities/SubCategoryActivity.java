package com.e.driver.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.e.driver.adapters.SubCategoryAdapter;
import com.e.driver.R;
import com.e.driver.models.SubCategory.SubCategoryResponse;
import com.e.driver.retrofit.RestClient;
import com.e.driver.utils.Constants;
import com.e.driver.utils.SamsPrefs;
import com.e.driver.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubCategoryActivity extends AppCompatActivity implements SubCategoryAdapter.OnSubCategoryClick {

    RecyclerView rv_SubCategory;
    SubCategoryResponse subCategoryResponse;
    private SubCategoryAdapter subCategoryAdapter;
    String cat_id;
     private Toolbar toolbar;
    private TextView mtitle;
    private ImageView imageViewTool;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getIntent().hasExtra(Constants.CATEGORY)){
            toolbar.setTitle(getIntent().getStringExtra(Constants.CATEGORY));
        }
//        imageViewTool=toolbar.findViewById(R.id.ivBackArrow);
        rv_SubCategory = findViewById(R.id.rv_SubCategory);


//        imageViewTool.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                onBackPressed();
//
//            }
//        });

        subCategoryResponse = new SubCategoryResponse();
        rv_SubCategory.setLayoutManager(new GridLayoutManager(SubCategoryActivity.this,2));
        subCategoryAdapter = new SubCategoryAdapter(SubCategoryActivity.this);
        rv_SubCategory.setAdapter(subCategoryAdapter);

        if (getIntent().hasExtra(Constants.CAT_ID)){
            cat_id = getIntent().getStringExtra(Constants.CAT_ID);
        }
        if (!TextUtils.isEmpty(cat_id) && Utils.isInternetConnected(this)){
            Utils.showProgressDialog(this);
            getSubCategory();
        }else{
            Toast.makeText(this, "Please check your internet connection", Toast.LENGTH_LONG).show();
        }
    }

    private void getSubCategory() {
        RestClient.getSubCategory(cat_id, new Callback<SubCategoryResponse>() {
            @Override
            public void onResponse(Call<SubCategoryResponse> call, Response<SubCategoryResponse> response) {
                if (response.code()==200){
                   Utils.dismissProgressDialog();
                    subCategoryResponse=response.body();

                    if (subCategoryResponse!=null && subCategoryResponse.getData().getServiceList()!=null ){
                        subCategoryAdapter.setCategryData(subCategoryResponse.getData().getServiceList());
                        subCategoryAdapter.notifyDataSetChanged();

                    }
                }
            }

            @Override
            public void onFailure(Call<SubCategoryResponse> call, Throwable t) {
                Utils.dismissProgressDialog();
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        onBackPressed();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSubCategoryClick(int position) {
        Toast.makeText(this, "Coming Soon", Toast.LENGTH_LONG).show();



       Intent intent = new Intent(this,BookRequestActivity.class);
       intent.putExtra(Constants.SUB_CAT_ID,subCategoryResponse.getData().getServiceList().get(position).getServiceListId());
       intent.putExtra(Constants.SUB_CAT_NAME,subCategoryResponse.getData().getServiceList().get(position).getServiceName());
       intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
       startActivity(intent);
    }
}