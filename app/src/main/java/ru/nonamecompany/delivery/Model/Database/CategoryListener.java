package ru.nonamecompany.delivery.Model.Database;

import ru.nonamecompany.delivery.Model.Categories.Category;

public interface CategoryListener {
    void onCategoryCreated(Category category);
}
