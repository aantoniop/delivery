package ru.nonamecompany.delivery.Model.Categories;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

import ru.nonamecompany.delivery.Model.Categories.UiListeners.CategoryClickListener;
import ru.nonamecompany.delivery.R;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    CategoryClickListener listener;

    private List<Category> categories;

    public CategoryAdapter(List<Category> categories, CategoryClickListener listener) {
        this.categories = categories;
        this.listener = listener;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private Button category;

        public ViewHolder(View itemView, CategoryClickListener listener) {
            super(itemView);
            category = itemView.findViewById(R.id.category_button);
            category.setOnClickListener(v -> listener.onCategoryClicked(category.getText().toString()));
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_category, parent, false);
        return new CategoryAdapter.ViewHolder(v, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.category.setText(categories.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}
