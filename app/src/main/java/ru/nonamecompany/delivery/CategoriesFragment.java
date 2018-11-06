package ru.nonamecompany.delivery;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ru.nonamecompany.delivery.Extensions.Tags;
import ru.nonamecompany.delivery.Model.Cart.Cart;
import ru.nonamecompany.delivery.Model.Categories.Category;
import ru.nonamecompany.delivery.Model.Categories.CategoryAdapter;
import ru.nonamecompany.delivery.Model.Database.FirestoreDatabase;
import ru.nonamecompany.delivery.Model.Database.DatabaseAsyncDAO;
import ru.nonamecompany.delivery.Model.Database.FoodListener;
import ru.nonamecompany.delivery.Model.Categories.Food;
import ru.nonamecompany.delivery.Model.Categories.FoodAdapter;

public class
CategoriesFragment extends Fragment {

    private DatabaseAsyncDAO database;

    private RecyclerView categoriesRecycleView;
    private RecyclerView foodRecycleView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(Tags.INSTANTIATING, "Categories created");
        database = FirestoreDatabase.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.i(Tags.INSTANTIATING, "Categories view created");
        View view = inflater.inflate(R.layout.fragment_categories, container, false);
        categoriesRecycleView = view.findViewById(R.id.categoriesList);
        foodRecycleView = view.findViewById(R.id.foodList);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        loadCategories();
        loadFood();
    }


    private void loadCategories() {
        ArrayList<Category> categoriesList = new ArrayList<>();
        CategoryAdapter adapter = new CategoryAdapter(categoriesList, this::loadFood);


        categoriesRecycleView.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false));
        categoriesRecycleView.setAdapter(adapter);

        database.getCategories(category -> {
            categoriesList.add(category);
            adapter.notifyDataSetChanged();
        });
    }

    private void loadFood(@Nullable String category) {
        ArrayList<Food> foodList = new ArrayList<>();
        FoodAdapter adapter = new FoodAdapter(foodList, food -> Cart.getInstance().addToCart(food));

        foodRecycleView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        foodRecycleView.setAdapter(adapter);

        FoodListener listener = food -> {
            foodList.add(food);
            adapter.notifyDataSetChanged();
        };

        if (category == null) database.getFood(listener);
        else database.getFood(category, listener);
    }

    private void loadFood() {
        loadFood(null);
    }
}