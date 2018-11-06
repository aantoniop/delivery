package ru.nonamecompany.delivery.Model.Categories;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.text.MessageFormat;
import java.util.List;

import ru.nonamecompany.delivery.Extensions.Tags;
import ru.nonamecompany.delivery.Model.Categories.UiListeners.AddToCartListener;
import ru.nonamecompany.delivery.R;


public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {

    private AddToCartListener addToCartListener;

    private List<Food> food;

    public FoodAdapter(List<Food> food, AddToCartListener addToCartListener) {
        this.food = food;
        this.addToCartListener = addToCartListener;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private Food food;

        private ProgressBar progressBar;

        private TextView category;
        private TextView name;
        private ImageView pic;
        private TextView price;
        private Button addToCart;

        public ViewHolder(View itemView, AddToCartListener addToCartListener) {
            super(itemView);

            progressBar = itemView.findViewById(R.id.imageProgressBar);

            category = itemView.findViewById(R.id.foodCategory);
            name = itemView.findViewById(R.id.foodName);
            pic = itemView.findViewById(R.id.foodPic);
            price = itemView.findViewById(R.id.foodPrice);
            addToCart = itemView.findViewById(R.id.addToCart);

            addToCart.setOnClickListener(v -> addToCartListener.onAddToCartClicked(food));
        }

        public void setFood(Food food) {
            this.food = food;
            initWidgets();
        }

        private void initWidgets() {
            loadPicture();
            category.setText(food.getCategory());
            name.setText(food.getName());
            price.setText(food.getPrice());
        }

        private void loadPicture() {
            RequestListener<Drawable> listener = new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    Log.i(Tags.DATABASE, MessageFormat.format("Error while loading image {0}", food.getPic()));
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    progressBar.setVisibility(View.GONE);
                    Log.i(Tags.DATABASE, MessageFormat.format("Picture {0} loaded", food.getPic()));
                    return false;
                }
            };

            Glide.with(itemView.getContext())
                    .load(food.getPic())
                    .apply(new RequestOptions().centerCrop())
                    .listener(listener)
                    .into(pic);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_food, parent, false);
        return new ViewHolder(v, addToCartListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setFood(food.get(position));
    }

    @Override
    public int getItemCount() {
        return food.size();
    }

}
