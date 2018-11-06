package ru.nonamecompany.delivery.Model.OrderHistory;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import ru.nonamecompany.delivery.Model.Cart.CartItem;
import ru.nonamecompany.delivery.R;

public class HistoryFoodAdapter extends RecyclerView.Adapter<HistoryFoodAdapter.ViewHolder> {

    private ArrayList<CartItem> items;

    public HistoryFoodAdapter(ArrayList<CartItem> items) {
        this.items = items;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private CartItem item;

        private ImageView pic;
        private TextView category;
        private TextView name;
        private TextView price;
        private TextView quantity;

        public ViewHolder(View itemView) {
            super(itemView);

            pic = itemView.findViewById(R.id.history_food_pic);
            category = itemView.findViewById(R.id.history_food_category);
            name = itemView.findViewById(R.id.history_food_name);
            price = itemView.findViewById(R.id.history_food_price);
            quantity = itemView.findViewById(R.id.history_food_quantity);
        }


        public void setItem(CartItem item) {
            this.item = item;
            initWidgets();
        }


        private void initWidgets() {
            loadPic();
            category.setText(item.getFood().getCategory());
            name.setText(item.getFood().getName());
            price.setText(item.getFood().getPrice());
            quantity.setText("x" + item.getQuantity().toString());
        }

        private void loadPic() {
            Glide.with(itemView.getContext())
                    .load(item.getFood().getPic())
                    .apply(new RequestOptions().centerCrop())
                    .into(pic);
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_order_history_food, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setItem(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
