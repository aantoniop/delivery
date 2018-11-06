package ru.nonamecompany.delivery;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import ru.nonamecompany.delivery.Model.Auth.AuthHandler;
import ru.nonamecompany.delivery.Model.Cart.Order;
import ru.nonamecompany.delivery.Model.Database.FirestoreDatabase;
import ru.nonamecompany.delivery.Model.OrderHistory.OrdersHistoryAdapter;

public class OrderHistoryActivity extends AppCompatActivity {

    String userId;

    RecyclerView ordersHistory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        userId = AuthHandler.getInstance().getUserId();
        ordersHistory = findViewById(R.id.order_history_recycler);

        loadHistory();
    }

    private void loadHistory(){
        ArrayList<Order> orders = new ArrayList<>();
        OrdersHistoryAdapter adapter = new OrdersHistoryAdapter(orders);

        ordersHistory.setLayoutManager(new LinearLayoutManager(this));
        ordersHistory.setAdapter(adapter);

        FirestoreDatabase.getInstance().getOrderHistory(userId, order -> {
            orders.add(order);
            adapter.notifyDataSetChanged();
        });
    }
}
