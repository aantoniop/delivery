package ru.nonamecompany.delivery.Model.Featured;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TopHandler {
    HashMap<String,Integer> top;

    public TopHandler() {
        top = new HashMap<>();
    }

    public void add(String key, Integer val) {
        if (top.get(key) == null) top.put(key, val);
        else top.put(key, top.get(key) + 1);
    }

    public ArrayList<String> getTop(int n) {
        ArrayList<String> sortedFood = new ArrayList<>();

        List<Map.Entry<String, Integer>> list = new LinkedList<>(top.entrySet());

        Collections.sort(list, (o1, o2) -> (o1.getValue()).compareTo(o2.getValue()));
        Collections.reverse(list);

        for (int i = 0; i < n; i++) {
            sortedFood.add(list.get(i).getKey());
        }
        return sortedFood;
    }


}
