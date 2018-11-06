package ru.nonamecompany.delivery.Model.Cart;

import java.util.ArrayList;

public class Order {

    private OrderInfo orderInfo;
    private ArrayList<CartItem> cartContent;


    public Order() {
        cartContent = new ArrayList<>();
    }


    @Override
    public String toString() {
        return "Order{" +
                "orderInfo=" + orderInfo.toString() +
                ", cartContent=" + cartContent.toString() +
                '}';
    }

    public OrderInfo getOrderInfo() {
        return orderInfo;
    }

    public Order setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
        return this;
    }

    public ArrayList<CartItem> getCartContent() {
        return cartContent;
    }

    public Order setCartContent(ArrayList<CartItem> cartContent) {
        this.cartContent = cartContent;
        return this;
    }
}