package ru.nonamecompany.delivery.Model.Cart;

import java.text.MessageFormat;

import ru.nonamecompany.delivery.Model.Categories.Food;

public class CartItem {

    private Food food;
    private Integer quantity;


    public CartItem(Food food) {
        this.food = food;
        quantity = 1;
    }


    public Food getFood() {
        return food;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public CartItem setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public void increaseQuantityBy(int n) {
        quantity = quantity + n;
    }

    public void decreaseQuantityBy(int n) {
        quantity = quantity - n;
    }

    @Override
    public String toString() {
        return MessageFormat.format("(food={0}, quantity={1})", food, quantity);
    }
}