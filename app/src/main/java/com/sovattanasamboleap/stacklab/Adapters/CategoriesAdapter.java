package com.sovattanasamboleap.stacklab.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sovattanasamboleap.stacklab.Models.CategoryModel;
import com.sovattanasamboleap.stacklab.R;
import com.sovattanasamboleap.stacklab.databinding.CategoryCardBinding;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.MyViewHolder> {

    Context context;
    List<CategoryModel> categories;
    CategorySelectListener categorySelectListener;
    int selectedPosition;

    public CategoriesAdapter(Context context, List<CategoryModel> categories, CategorySelectListener categorySelectListener) {
        this.context = context;
        this.categories = categories;
        this.categorySelectListener = categorySelectListener;
        selectedPosition = -1;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoriesAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.category_card, parent, false));
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        CategoryModel category = categories.get(position);

        holder.binding.getRoot().setText(category.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedPosition = position;
                categorySelectListener.onCategorySelected(category);
            }
        });

        if (selectedPosition == position) {
            holder.binding.getRoot().setTextColor(context.getColor(R.color.white));
            holder.binding.getRoot().setBackgroundColor(context.getColor(R.color.orange));
        } else {
            holder.binding.getRoot().setTextColor(context.getColor(R.color.orange));
            holder.binding.getRoot().setBackgroundColor(context.getColor(R.color.white));
        }

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        CategoryCardBinding binding;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CategoryCardBinding.bind(itemView);
        }
    }

    public CategorySelectListener getCategorySelectListener() {
        return categorySelectListener;
    }

    public interface CategorySelectListener {

        void onCategorySelected(CategoryModel category);

    }
}
