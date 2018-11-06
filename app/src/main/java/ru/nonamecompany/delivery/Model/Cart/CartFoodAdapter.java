package ru.nonamecompany.delivery.Model.Cart;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.MessageFormat;
import java.util.ArrayList;

import ru.nonamecompany.delivery.R;

public class CartFoodAdapter extends RecyclerView.Adapter<CartFoodAdapter.ViewHolder>{

    private ArrayList<CartItem> cart;

    public CartFoodAdapter(){

        cart = Cart.getInstance().getCartItems();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private CartItem item;
        private CartFoodAdapter adapter;

        private TextView category;
        private TextView name;
        private ImageView pic;
        private TextView quantity;

        private ImageButton remove;
        private ImageButton increase;
        private ImageButton decrease;

        public ViewHolder(View itemView, CartFoodAdapter adapter) {
            super(itemView);
            this.adapter = adapter;

            category = itemView.findViewById(R.id.cartFoodCategory);
            name = itemView.findViewById(R.id.cartFoodName);
            pic = itemView.findViewById(R.id.cartFoodPic);
            quantity = itemView.findViewById(R.id.cartQuantity);

            remove = itemView.findViewById(R.id.cartRemove);
            increase = itemView.findViewById(R.id.cartIncreaseQuantity);
            decrease = itemView.findViewById(R.id.cartDecreaseQuantity);
        }

        public void setItem(CartItem item) {
            this.item = item;
            initWidgets();
            initListeners();
        }

        private void initWidgets() {
            loadPic();
            category.setText(item.getFood().getCategory());
            name.setText(item.getFood().getName());
            quantity.setText(item.getQuantity().toString());
        }

        private void loadPic() {
            Glide.with(itemView.getContext())
                    .load(item.getFood().getPic())
                    .apply(new RequestOptions().centerCrop())
                    .into(pic);
        }

        private void initListeners() {
            remove.setOnClickListener(v -> {
                deleteDialog();
            });

            increase.setOnClickListener(v -> {
                Cart.getInstance().addToCart(item.getFood());
                adapter.notifyDataSetChanged();
            });

            decrease.setOnClickListener(v -> {
                Cart.getInstance().decreaseFromCart(item.getFood());
                adapter.notifyDataSetChanged();
            });
        }

        private void deleteDialog() {
            Context ctx = itemView.getContext();
            AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
            builder.setMessage(ctx.getString(R.string.delete_from_cart_message, item.getFood().getName()))
                    .setTitle(ctx.getString(R.string.delete_from_cart_title))
                    .setNegativeButton(ctx.getString(R.string.delete_from_cart_negative_button), (dialog, which) -> {
                    })
                    .setPositiveButton(ctx.getString(R.string.delete_from_cart_positive_button), (dialog, which) -> {
                        Cart.getInstance().removeFromCart(item.getFood());
                        adapter.notifyDataSetChanged();
                    });
            builder.create().show();
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_cart, parent, false);
        return new ViewHolder(v, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setItem(cart.get(position));
    }

    @Override
    public int getItemCount() {
        return cart.size();
    }
}
