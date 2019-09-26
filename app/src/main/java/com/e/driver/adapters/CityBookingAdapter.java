package com.e.driver.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.e.driver.R;
import com.e.driver.models.Category.Category;
import com.e.driver.models.GetState.State;
import com.e.driver.models.cities.CityList;

import java.util.List;

public class CityBookingAdapter extends BaseAdapter {

    Context applicationContext;
    List<CityList> cityLists;
    int flags[];
    OnStateSelect onStateSelect;

    LayoutInflater layoutInflater;

    public CityBookingAdapter(Context applicationContext) {
        this.applicationContext = applicationContext;
        layoutInflater = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {

        if (cityLists != null && cityLists.size() > 0) {
            return cityLists.size();
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
        names.setText("" + cityLists.get(position).getCityName());
//        names.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (onStateSelect!=null)
//                    onStateSelect.onSelect(categoryList.get(position).getCategoryName(),categoryList.get(position).getCategoryId());
//            }
//        });
        return view;
    }


    public void setOnStateSelect(OnStateSelect onStateSelect) {
        this.onStateSelect = onStateSelect;
    }

    public void setCityList(List<CityList> categoryList) {
        this.cityLists = categoryList;
    }

    public interface OnStateSelect {
        public void onStateSelect(String name, String id);


    }

}

