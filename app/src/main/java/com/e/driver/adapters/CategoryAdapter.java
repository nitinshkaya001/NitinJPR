package com.e.driver.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.e.driver.R;
import com.e.driver.models.Category.Category;
import com.e.driver.utils.Constants;
import com.squareup.picasso.Picasso;


import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private Context context;
    private List<Category> categoryList;
    OnClickCategoryClick clickCategoryClick;



    public CategoryAdapter(Context context) {
        this.context = context;
    }
    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int i) {

        Category category = categoryList.get(i);

        Picasso.with(context).load(Constants.BASE_URL+category.getImageUrl()).into(holder.categoryImage);
        holder.categoryName.setText("" +category.getCategoryName());
        holder.cardViewCategory.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (clickCategoryClick != null) {

                    clickCategoryClick.onCategoryClick(holder.getAdapterPosition());
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        if (categoryList != null && categoryList.size() > 0) {
            return categoryList.size();

        } else {
            return 0;
        }
    }

    public void setOnCategoryClickListener(OnClickCategoryClick categoryClickListener) {
        this.clickCategoryClick = categoryClickListener;
    }

    public void setCategryData(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView categoryImage;
        private TextView categoryName;
        private CardView cardViewCategory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryImage = itemView.findViewById(R.id.iv_categoryService);
            categoryName = itemView.findViewById(R.id.tv_category_name);
            cardViewCategory = itemView.findViewById(R.id.cv_category);
        }
    }

    public interface OnClickCategoryClick {
        public void onCategoryClick(int position);
    }
}
