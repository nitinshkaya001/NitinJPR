package com.e.driver.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.e.driver.R;
import com.e.driver.models.Category.Category;
import com.e.driver.models.SubCategory.ServiceList;

import java.util.List;

public class SubCatBookingAdapter extends BaseAdapter {

    Context applicationContext;
    List<ServiceList> serviceLists;
    int flags[];
    OnSubCatSelect onSubCatSelect;

    LayoutInflater layoutInflater;

    public SubCatBookingAdapter(Context applicationContext) {
        this.applicationContext = applicationContext;
        this.flags = flags;
        layoutInflater = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {

        if (serviceLists != null && serviceLists.size() > 0) {
            return serviceLists.size();
        } else {
            return 0;
        }

    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {

        view = layoutInflater.inflate(R.layout.spinner_item, null);
        TextView names = view.findViewById(R.id.title);
        names.setText("" + serviceLists.get(position).getServiceName());
//        names.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (onSubCatSelect!=null)
//                    onSubCatSelect.onSubSelect(serviceLists.get(position).getServiceName(),serviceLists.get(position).getServiceListId());
//            }
//        });
        return view;
    }


    public void setOnStateSelect(OnSubCatSelect onStateSelect) {
        this.onSubCatSelect = onStateSelect;
    }

    public void setSubCatList(List<ServiceList> categoryList) {
        this.serviceLists = categoryList;
    }

    public interface OnSubCatSelect {
        public void onSubSelect(String name, String id);


    }

}

