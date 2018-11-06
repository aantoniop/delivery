package ru.nonamecompany.delivery;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import ru.nonamecompany.delivery.Model.Auth.AuthHandler;
import ru.nonamecompany.delivery.Model.Cart.Cart;
import ru.nonamecompany.delivery.Model.Cart.CartFoodAdapter;

public class CartFragment extends Fragment {

    private TextView totalSum;
    private RecyclerView items;
    private Button order;

    private AuthHandler auth;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = AuthHandler.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view;
        if(cartIsEmpty()) {
            view = inflater.inflate(R.layout.fragment_cart_empty, container, false);
        }
        else {
            view = inflater.inflate(R.layout.fragment_cart, container, false);
            totalSum = view.findViewById(R.id.cartTotalSum);
            items = view.findViewById(R.id.cartItems);
            order = view.findViewById(R.id.cartOrderButton);
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        if(!cartIsEmpty()) {
            loadItems();
            totalSum.setText(Cart.getInstance().getTotalSum());
            Cart.getInstance().addOnSumChangedListener(sum -> totalSum.setText(sum));

            order.setOnClickListener(v -> orderClicked());
        }
    }


    private void loadItems(){
        CartFoodAdapter adapter = new CartFoodAdapter();

        items.setLayoutManager(new LinearLayoutManager(this.getContext()));
        items.setAdapter(adapter);
    }

    private void orderClicked() {
        if (auth.isSigned()) {
            startActivity(new Intent(this.getContext(), OrderActivity.class));
        } else {
            auth.authenticate(this);
        }
    }

    private boolean cartIsEmpty(){
        return (Cart.getInstance().size() == 0);
    }
}
