package ru.nonamecompany.delivery.Model.Categories;

import java.math.BigDecimal;
import java.util.Objects;

public class Food {

    private String id;

    private String category;
    private String name;
    private String pic;
    private String price;


    public String getId() {
        return id;
    }

    public Food setId(String id) {
        this.id = id;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public Food setCategory(String category) {
        this.category = category;
        return this;
    }

    public String getName() {
        return name;
    }

    public Food setName(String name) {
        this.name = name;
        return this;
    }

    public String getPic() {
        return pic;
    }

    public Food setPic(String pic) {
        this.pic = pic;
        return this;
    }

    public String getPrice() {
        return price;
    }

    public Food setPrice(String price) {
        this.price = price;
        return this;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, category, name, pic, price);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;

        Food food = (Food) obj;
        return id.equals(food.id);
    }
}
