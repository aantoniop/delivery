package ru.nonamecompany.delivery.Model.Database;

import ru.nonamecompany.delivery.Model.Categories.Food;

public interface FoodListener {
    void onFoodCreated(Food food);
}
