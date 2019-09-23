package com.e.driver.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.e.driver.R;
import com.e.driver.models.SubCategory.ServiceList;
import com.e.driver.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.ViewHolder> {
    private Context context;
    private List<ServiceList> subCategoryList;

    public SubCategoryAdapter(Context context) {
        this.context = context;
        this.subCategoryList = subCategoryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sub_category, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        ServiceList serviceList = subCategoryList.get(i);

        Picasso.with(context).load(Constants.BASE_URL+serviceList.getImageUrl()).into(holder.serviceImage);

        holder.serviceName.setText(serviceList.getServiceName());
        holder.servicePrice.setText("INR "+serviceList.getPrice());

    }

    @Override
    public int getItemCount() {
        if (subCategoryList != null && subCategoryList.size() > 0)
            return subCategoryList.size();
        else
            return 0;
    }

    public void setCategryData(List<ServiceList> serviceList) {
        this.subCategoryList = serviceList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView serviceName, servicePrice;
        private ImageView serviceImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            serviceName = itemView.findViewById(R.id.tv_sub_category_name);
            servicePrice = itemView.findViewById(R.id.tv_sub_category_price);
            serviceImage = itemView.findViewById(R.id.iv_sub_category);

        }
    }

    public interface OnSubCategoryClick {
        public void onSubCategoryClick(int position);
    }
}
