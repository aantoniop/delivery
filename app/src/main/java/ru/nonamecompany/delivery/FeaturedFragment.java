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
import ru.nonamecompany.delivery.Model.Categories.Food;
import ru.nonamecompany.delivery.Model.Categories.FoodAdapter;
import ru.nonamecompany.delivery.Model.Database.DatabaseAsyncDAO;
import ru.nonamecompany.delivery.Model.Database.FirestoreDatabase;

public class FeaturedFragment extends Fragment {

    DatabaseAsyncDAO database;

    RecyclerView featuredFood;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirestoreDatabase.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_featured, container,false);
        featuredFood = view.findViewById(R.id.featured_food);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        loadFood(2);
    }


    private void loadFood(Integer top) {
        ArrayList<Food> foodList = new ArrayList<>();
        FoodAdapter adapter = new FoodAdapter(foodList, food -> Cart.getInstance().addToCart(food));

        featuredFood.setLayoutManager(new LinearLayoutManager(this.getContext()));
        featuredFood.setAdapter(adapter);

        database.getTopFood(top, food -> {
            Log.i(Tags.DATABASE, food.getId());
            foodList.add(food);
            adapter.notifyDataSetChanged();
        });
    }
}
