package com.edge.greasy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        ListView aboutUsList = (ListView) findViewById(R.id.aboutUsList);

        String[] values = new String[] {
                "",
                "Neha Rokde",
                "Pranalee Rokde",
                "Vinay B. Patil",
                ""
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        // Assign adapter to ListView
        aboutUsList.setAdapter(adapter);
    }
}
