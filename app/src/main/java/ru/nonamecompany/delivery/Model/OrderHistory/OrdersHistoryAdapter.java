package ru.nonamecompany.delivery.Model.OrderHistory;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import ru.nonamecompany.delivery.Model.Cart.CartFoodAdapter;
import ru.nonamecompany.delivery.Model.Cart.CartItem;
import ru.nonamecompany.delivery.Model.Cart.Order;
import ru.nonamecompany.delivery.Model.Cart.OrderInfo;
import ru.nonamecompany.delivery.Model.Database.FirestoreDatabase;
import ru.nonamecompany.delivery.R;

public class OrdersHistoryAdapter extends RecyclerView.Adapter<OrdersHistoryAdapter.ViewHolder>{

    private ArrayList<Order> orders;

    public OrdersHistoryAdapter(ArrayList<Order> orders) {
        this.orders = orders;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private OrderInfo orderInfo;
        private ArrayList<CartItem> items;

        private TextView orderTime;
        private TextView phone;
        private TextView deliveryTime;
        private TextView city;
        private TextView street;
        private TextView house;
        private TextView flat;
        private TextView comment;

        private RecyclerView food;

        public ViewHolder(View itemView) {
            super(itemView);

            orderTime = itemView.findViewById(R.id.history_order_time);
            phone = itemView.findViewById(R.id.history_order_phone);
            deliveryTime = itemView.findViewById(R.id.history_delivery_time);
            city = itemView.findViewById(R.id.history_city);
            street = itemView.findViewById(R.id.history_street);
            house = itemView.findViewById(R.id.history_house);
            flat = itemView.findViewById(R.id.history_flat);
            comment = itemView.findViewById(R.id.history_comment);

            food = itemView.findViewById(R.id.history_food);
        }

        public void setOrderInfo(OrderInfo orderInfo) {
            this.orderInfo = orderInfo;
            initWidgets();
        }

        public void setItems(ArrayList<CartItem> items){
            this.items = items;
            loadHistory();
        }


        private void initWidgets(){
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

            orderTime.setText(dateFormat.format(orderInfo.getOrderTime().toDate()));
            phone.setText(orderInfo.getPhone());
            deliveryTime.setText(orderInfo.getDeliveryTime());
            city.setText(orderInfo.getCity());
            street.setText(orderInfo.getStreet());
            house.setText(orderInfo.getHouse());
            flat.setText(orderInfo.getFlat());
            comment.setText(orderInfo.getComment());
        }

        private void loadHistory(){
            HistoryFoodAdapter adapter = new HistoryFoodAdapter(items);

            //food.setLayoutManager(new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false));
            food.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            food.setAdapter(adapter);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_order_history, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setOrderInfo(orders.get(position).getOrderInfo());
        holder.setItems(orders.get(position).getCartContent());
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }
}
