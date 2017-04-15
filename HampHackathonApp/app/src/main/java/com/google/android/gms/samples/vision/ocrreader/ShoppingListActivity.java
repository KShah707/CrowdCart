package com.google.android.gms.samples.vision.ocrreader;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

public class ShoppingListActivity extends AppCompatActivity {

    ListView shopList;
    ArrayList<String> foods;
    ArrayAdapter<String> adapter;
    Button planRouteBtn;
    FloatingActionButton additem_fab;

    HashMap<String, HashMap<String, Double>> database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        database = new HashMap<String, HashMap<String, Double>>();

        additem_fab = new FloatingActionButton(this);
        additem_fab = (FloatingActionButton) findViewById(R.id.listBtn);

        foods = new ArrayList<String>();
        shopList = (ListView) findViewById(R.id.shopList);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, foods);
        shopList.setAdapter(adapter);

        additem_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(ShoppingListActivity.this);

                alert.setTitle("Add item");
                alert.setMessage("Enter a new item");

                // Set an EditText view to get user input
                final EditText input = new EditText(ShoppingListActivity.this);
                alert.setView(input);

                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        AddItem(input.getText().toString());
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //Cancelled
                    }
                });

                alert.show();
            }
        });

        Intent intent = getIntent();
        database = (HashMap<String, HashMap<String, Double>>) intent.getExtras().get("database");

        planRouteBtn = (Button) findViewById(R.id.planRoute);
        planRouteBtn.setOnClickListener(new View.OnClickListener() {



            final Intent planRouteIntent = new Intent(ShoppingListActivity.this, RoutePlanActivity.class);

            @Override
            public void onClick(View view) {

                planRouteIntent.putExtra("foodList", foods);
                planRouteIntent.putExtra("database", database);
                startActivity(planRouteIntent);
            }
        });
    }

    public void AddItem(String item)
    {
        foods.add(item);
        adapter.notifyDataSetChanged();
    }

}
