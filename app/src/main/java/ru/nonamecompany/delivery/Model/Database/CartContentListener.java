package ru.nonamecompany.delivery.Model.Database;

import java.util.ArrayList;

import ru.nonamecompany.delivery.Model.Cart.CartItem;

public interface CartContentListener {
    void onCartContentReceived(ArrayList<CartItem> cartItems);
}
