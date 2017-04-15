package com.google.android.gms.samples.vision.ocrreader;

import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;


import java.lang.reflect.Array;
import java.util.ArrayList;

public class RootActivity extends AppCompatActivity {

    private Button receiptBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);

        //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLUE));

        FloatingActionButton camera_fab = (FloatingActionButton) findViewById(R.id.camera_fab);

        final Intent ocrIntent = new Intent(this, OcrCaptureActivity.class);
        camera_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                startActivityForResult(ocrIntent, 0);
            }
        });

        FloatingActionButton additem_fab = (FloatingActionButton) findViewById(R.id.additem_fab);
        additem_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
            }
        });

        ListView itemList = (ListView) findViewById(R.id.item_list);
        //custom liust Adapter
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 0) {
            if (data.hasExtra("result")) {
                ArrayList<String> wordList = (ArrayList<String>) data.getStringArrayListExtra("result");

                if (wordList.size() > 0)
                    Log.w("intent result", wordList.get(0));
                else
                    Log.w("intent result", "No items tapped");
            }
        }

        else
        {
            Log.w("intent status", "failed");
        }
    }
}
