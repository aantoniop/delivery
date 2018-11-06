package ru.nonamecompany.delivery.Model.Database;

import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ru.nonamecompany.delivery.Extensions.Tags;
import ru.nonamecompany.delivery.Model.Cart.CartItem;
import ru.nonamecompany.delivery.Model.Cart.Order;
import ru.nonamecompany.delivery.Model.Cart.OrderInfo;
import ru.nonamecompany.delivery.Model.Categories.Category;
import ru.nonamecompany.delivery.Model.Categories.Food;
import ru.nonamecompany.delivery.Model.Featured.TopHandler;

public class FirestoreDatabase implements DatabaseAsyncDAO {

    private static FirestoreDatabase instance;
    private FirebaseFirestore database;


    private FirestoreDatabase() {
        database = FirebaseFirestore.getInstance();
    }


    public static FirestoreDatabase getInstance() {
        if (instance == null) instance = new FirestoreDatabase();
        return instance;
    }

    @Override
    public void getTopFood(int top, FoodListener foodListener) {
        TopHandler topHandler = new TopHandler();

        CollectionReference statisticsCollection = database.collection("food_statistics");

        statisticsCollection.get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (DocumentSnapshot doc : queryDocumentSnapshots) {
                topHandler.add(doc.getString("id"), doc.getDouble("count").intValue());
            }

            ArrayList<String> topFoodId = topHandler.getTop(top);

            getFood(food -> {
                for (String id : topFoodId) {
                    if (food.getId().equals(id)) foodListener.onFoodCreated(food);
                }
            });
        });
    }

    @Override
    public void getCategories(CategoryListener categoryListener) {
        CollectionReference categories = database.collection("categories");

        categories.get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (DocumentSnapshot item : queryDocumentSnapshots) {
                Log.i(Tags.DATABASE, MessageFormat.format("Loaded category: {0}", item.getData().toString()));
                categoryListener.onCategoryCreated(item.toObject(Category.class));
            }
        });
    }

    @Override
    public void getFood(FoodListener foodListener) {
        CollectionReference categories = database.collection("categories");

        categories.get().addOnSuccessListener(categoriesQuery -> {
            for (DocumentSnapshot category : categoriesQuery) {
                categories.document(category.getId())
                        .collection("goods")
                        .get()
                        .addOnSuccessListener(goodsQuery -> {
                            for (DocumentSnapshot item : goodsQuery) {
                                logFoodLoading(category, item);

                                Food f = new Food()
                                        .setId(item.getId())
                                        .setCategory(category.getString("name"))
                                        .setName(item.getString("name"))
                                        .setPic(item.getString("pic"))
                                        .setPrice(item.getString("price"));
                                foodListener.onFoodCreated(f);
                            }
                        });
            }
        });
    }

    @Override
    public void getFood(String categoryName, FoodListener foodListener) {
        CollectionReference categories = database.collection("categories");

        categories.whereEqualTo("name", categoryName).get().addOnSuccessListener(queryDocumentSnapshots -> {
            DocumentSnapshot category = queryDocumentSnapshots.getDocuments().get(0);

            Log.d(Tags.DATABASE, MessageFormat.format("Loaded category doc: {0}", category.getId()));

            categories.document(category.getId())
                    .collection("goods")
                    .get()
                    .addOnSuccessListener(goodsQuery -> {
                        for (DocumentSnapshot item : goodsQuery) {
                            logFoodLoading(category, item);

                            Food f = new Food()
                                    .setId(item.getId())
                                    .setCategory(category.getString("category"))
                                    .setName(item.getString("name"))
                                    .setPic(item.getString("pic"))
                                    .setPrice(item.getString("price"));
                            foodListener.onFoodCreated(f);
                        }
                    });
        });
    }

    @Override
    public void placeOrder(Order order) {
        CollectionReference ordersCollection = database.collection("orders");

        DocumentReference newOrder = ordersCollection.document();
        newOrder.set(order.getOrderInfo());

        for (CartItem item : order.getCartContent()) {
            ordersCollection.document(newOrder.getId())
                    .collection("cart")
                    .document()
                    .set(item);
        }

        addToStatistics(order);
    }

    @Override
    public void getOrderHistory(String userId, OrderListener orderListener) {
        CollectionReference ordersCollection = database.collection("orders");

        ordersCollection.whereEqualTo("user", userId).get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (DocumentSnapshot orderDocument : queryDocumentSnapshots) {
                Order order = new Order();
                order.setOrderInfo(orderDocument.toObject(OrderInfo.class));

                getOrderFood(orderDocument.getId(), cartItems -> {
                    order.setCartContent(cartItems);
                    orderListener.onOrderCreated(order);

                    Log.i(Tags.DATABASE, order.toString());
                });
            }
        });
    }


    private void getOrderFood(String orderId, CartContentListener cartContentListener) {
        Gson gson = new Gson();

        ArrayList<CartItem> orderedFood = new ArrayList<>();
        CollectionReference ordersCollection = database.collection("orders");

        ordersCollection.document(orderId)
                .collection("cart")
                .get()
                .addOnSuccessListener(cartQuery -> {
                    for (DocumentSnapshot item : cartQuery) {

                        JsonElement jsonElement = gson.toJsonTree(item.get("food"));
                        Food food = gson.fromJson(jsonElement, Food.class);

                        Integer quantity = item.getLong("quantity").intValue();

                        orderedFood.add(new CartItem(food).setQuantity(quantity));
                        if (isLastDoc(cartQuery, item)) {
                            cartContentListener.onCartContentReceived(orderedFood);
                        }
                    }
                });
    }

    private void addToStatistics(Order order){
        CollectionReference statisticsCollection = database.collection("food_statistics");
        for (CartItem item:order.getCartContent()) {
            Map<String, Object> food = new HashMap<>();
            food.put("id", item.getFood().getId());
            food.put("count",item.getQuantity());
            statisticsCollection.document().set(food);
        }
    }

    private void logFoodLoading(DocumentSnapshot category, DocumentSnapshot item) {
        Log.i(Tags.DATABASE, MessageFormat.format("Loaded product: id({0}) category({1}) data({2})", item.getId(), category.getString("name"), item.getData().toString()));
    }

    private boolean isLastDoc(QuerySnapshot querySnapshot, DocumentSnapshot doc) {
        return querySnapshot.getDocuments().get(querySnapshot.getDocuments().size() - 1).equals(doc);
    }
}
