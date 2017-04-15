package com.google.android.gms.samples.vision.ocrreader;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class RoutePlanActivity extends AppCompatActivity {

    ArrayList<String> foodList;
    //ShoppingList shoppingList;
    HashMap<String, HashMap<String, Double>> database;
    BestFromList bestFromList;
    HashMap<String, ArrayList<String>> bestRoute;
    StoreFinder storeFinder;
    PriceData priceData;

    Button planRoute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_plan);

        PriceData priceData = new PriceData();
//        for (String food : foodList)
//            priceData.addEntry(food, food + "123", 10.50);

        Intent intent = getIntent();
        Log.w("got intent", "true");
        foodList = intent.getStringArrayListExtra("foodList");
        Log.w("first food item", foodList.get(0));
        database = (HashMap<String, HashMap<String, Double>>) intent.getExtras().get("database");
        HashMap<String, Double> n = new HashMap<String, Double>();
        n.put("stopshop674", 1.39);
        database.put("Garbanzo beans", n);
        HashMap<String, Double> n2 = new HashMap<String, Double>();
        n.put("stopshop674", 2.99);
        database.put("Pita bread", n);
        HashMap<String, Double> n3 = new HashMap<String, Double>();
        n.put("stopshop674", 2.79);
        database.put("Vegetable oil", n);
        HashMap<String, Double> n4 = new HashMap<String, Double>();
        n.put("stopshop674", 6.49);
        database.put("Sesame seeds", n);
        HashMap<String, Double> n5 = new HashMap<String, Double>();
        n.put("stopshop674", 3.49);
        database.put("Celery", n);
        HashMap<String, Double> n6 = new HashMap<String, Double>();
        n.put("stopshop674", 1.78);
        database.put("Baked Beans", n);
        HashMap<String, Double> n7 = new HashMap<String, Double>();
        n.put("stopshop674", 8.99);
        database.put("Printer paper", n);
        HashMap<String, Double> n8 = new HashMap<String, Double>();
        n.put("stopshop674", 4.49);
        database.put("Ricotta", n);
        HashMap<String, Double> n9 = new HashMap<String, Double>();
        n.put("stopshop674", 2.19);
        database.put("Corn", n);

        //Log.w("price", Arrays.asList(priceData.getDatabase().get(foodList(0))));

        bestFromList = new BestFromList(database, foodList, 1, 1);
        Log.w("created bflist", "true");

        BestRoute temp = bestFromList.bestRoute();
        Log.w("got temp", "true");
        bestRoute = temp.getBest();

//        Log.w("found broute", "true");
//        storeFinder = new StoreFinder();
//        Log.w("created storefinder", "true");
//        storeFinder.findStores();
//        Log.w("found stores", "true");

        Map<String, Place> map = storeFinder.getIDMap();

        for (String storeID : bestRoute.keySet())
        {
            Place newPlace = map.get(storeID);
            String locationName = newPlace.getName();
            Log.w("place name", locationName);
        }

        

        //shoppingList = new ShoppingList();
        //shoppingList.setList(foodList);
    }

}
