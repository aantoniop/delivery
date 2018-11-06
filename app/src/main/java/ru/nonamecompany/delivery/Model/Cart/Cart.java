package ru.nonamecompany.delivery.Model.Cart;

import android.util.Log;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;

import ru.nonamecompany.delivery.Extensions.Tags;
import ru.nonamecompany.delivery.Model.Cart.Listeners.OnSumInCartChangedListener;
import ru.nonamecompany.delivery.Model.Categories.Food;

public class Cart {
    private static Cart instance;

    private ArrayList<CartItem> cart;

    private String totalSum;
    private OnSumInCartChangedListener onSumInCartChangedListener;

    private Cart() {
        cart = new ArrayList<>();
        totalSum = "0";
    }


    public static Cart getInstance() {
        if (instance == null) instance = new Cart();
        return instance;
    }

    public ArrayList<CartItem> getCartItems() {
        return cart;
    }

    public String getTotalSum(){
        return totalSum;
    }

    public void addToCart(Food food) {
        if (!isInCart(food)) cart.add(new CartItem(food));
        else getItem(food).increaseQuantityBy(1);

        updateItemsPrice();

        logCartContent();
    }

    public void decreaseFromCart(Food food) throws NoSuchItemInCartException{
        if (isQuantityMoreThanOne(food)) getItem(food).decreaseQuantityBy(1);
        else removeFromCart(food);
        updateItemsPrice();

        logCartContent();
    }

    public void removeFromCart(Food food) throws NoSuchItemInCartException {
        cart.remove(getItem(food));
        updateItemsPrice();

        logCartContent();
    }

    public void addOnSumChangedListener(OnSumInCartChangedListener listener){
        onSumInCartChangedListener = listener;
    }

    public void clear(){
        cart.clear();
        updateItemsPrice();
    }

    public int size(){
        return cart.size();
    }

    private boolean isInCart(Food food) {
        try {
            getItem(food);
            return true;
        } catch (NoSuchItemInCartException e) {
            return false;
        }
    }

    private CartItem getItem(Food food) throws NoSuchItemInCartException{
        for (CartItem item : cart) {
            if (item.getFood().equals(food)) return item;
        }
        throw new NoSuchItemInCartException();
    }

    private boolean isQuantityMoreThanOne(Food food) throws NoSuchItemInCartException{
        return getItem(food).getQuantity() > 1;
    }

    private void updateItemsPrice() {
        BigDecimal sum = new BigDecimal(0);
        for (CartItem item : cart) {
            BigDecimal itemPrice = new BigDecimal(item.getFood().getPrice())
                    .multiply(new BigDecimal(item.getQuantity()));;
            sum = sum.add(itemPrice);
        }
        totalSum = sum.toString();

        if (onSumInCartChangedListener != null) {
            onSumInCartChangedListener.onSumInCartChanged(totalSum);
        }
    }

    private void logCartContent(){
        Log.i(Tags.CART, MessageFormat.format("Cart content: {0}", cart.toString()));
    }
}