package ru.nonamecompany.delivery;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.Timestamp;

import ru.nonamecompany.delivery.Model.Auth.AuthHandler;
import ru.nonamecompany.delivery.Model.Cart.Cart;
import ru.nonamecompany.delivery.Model.Cart.Order;
import ru.nonamecompany.delivery.Model.Cart.OrderInfo;
import ru.nonamecompany.delivery.Model.Database.FirestoreDatabase;
import ru.nonamecompany.delivery.Model.Database.DatabaseAsyncDAO;

public class OrderActivity extends AppCompatActivity {

    private DatabaseAsyncDAO database;

    private EditText phone;
    private EditText deliveryTime;
    private EditText city;
    private EditText street;
    private EditText house;
    private EditText flat;
    private EditText comment;
    private Button placeButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        database = FirestoreDatabase.getInstance();

        initViews();
        initListeners();
    }

    private void initViews() {
        phone = findViewById(R.id.orderPhone);
        deliveryTime = findViewById(R.id.orderDeliveryTime);
        city = findViewById(R.id.orderCity);
        street = findViewById(R.id.orderStreet);
        house = findViewById(R.id.orderHouse);
        flat = findViewById(R.id.orderFlat);
        comment = findViewById(R.id.orderComment);
        placeButton = findViewById(R.id.orderPlace);
    }

    private void initListeners() {
        phone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        placeButton.setOnClickListener(v -> {
            if (isAllFieldsFilled()) placeOrder();
            else rejectOrder(getString(R.string.fill_all_fields));
        });
    }

    private boolean isAllFieldsFilled() {
        return !phone.getText().toString().isEmpty()
                && !deliveryTime.getText().toString().isEmpty()
                && !city.getText().toString().isEmpty()
                && !street.getText().toString().isEmpty()
                && !house.getText().toString().isEmpty()
                && !flat.getText().toString().isEmpty()
                /*&& !comment.getText().toString().isEmpty()*/;
    }

    private void rejectOrder(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void placeOrder() {
        database.placeOrder(createOrder());
        Cart.getInstance().clear();
        finish();
    }


    private Order createOrder() {
        OrderInfo info = new OrderInfo()
                .setUser(AuthHandler.getInstance().getUserId())
                .setOrderTime(Timestamp.now())
                .setPhone(phone.getText().toString())
                .setDeliveryTime(deliveryTime.getText().toString())
                .setCity(city.getText().toString())
                .setStreet(street.getText().toString())
                .setHouse(house.getText().toString())
                .setFlat(flat.getText().toString())
                .setComment(comment.getText().toString());

        return new Order()
                .setOrderInfo(info)
                .setCartContent(Cart.getInstance().getCartItems());
    }
}
