package com.e.driver.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.e.driver.R;
import com.e.driver.models.Category.Category;

import java.util.List;

public class CategoryBookingAdapter extends BaseAdapter {

    Context applicationContext;
    List<Category> categoryList;
    int flags[];
    OnCatSelect onStateSelect;

    LayoutInflater layoutInflater;

    public CategoryBookingAdapter(Context applicationContext) {
        this.applicationContext = applicationContext;
        this.flags = flags;
        layoutInflater = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {

        if (categoryList != null && categoryList.size() > 0) {
            return categoryList.size();
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
        names.setText("" + categoryList.get(position).getCategoryName());
//        names.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (onStateSelect!=null)
//                    onStateSelect.onSelect(categoryList.get(position).getCategoryName(),categoryList.get(position).getCategoryId());
//            }
//        });
        return view;
    }


    public void setOnStateSelect(OnCatSelect onStateSelect) {
        this.onStateSelect = onStateSelect;
    }

    public void setCatList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public interface OnCatSelect {
        public void onSelect(String name, String id);


    }

}

