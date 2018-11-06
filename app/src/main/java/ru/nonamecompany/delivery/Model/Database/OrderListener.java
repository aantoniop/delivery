package ru.nonamecompany.delivery.Model.Database;

import ru.nonamecompany.delivery.Model.Cart.Order;

public interface OrderListener {
    void onOrderCreated(Order order);
}
