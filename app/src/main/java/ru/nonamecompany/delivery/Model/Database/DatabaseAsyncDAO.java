package ru.nonamecompany.delivery.Model.Database;

import ru.nonamecompany.delivery.Model.Cart.Order;

public interface DatabaseAsyncDAO {
    void getCategories(CategoryListener categoryListener);
    void getFood(FoodListener foodListener);
    void getFood(String categoryName, FoodListener foodListener);
    void placeOrder(Order order);
    void getOrderHistory(String userId, OrderListener orderListener);
    void getTopFood(int top, FoodListener foodListener);
}
