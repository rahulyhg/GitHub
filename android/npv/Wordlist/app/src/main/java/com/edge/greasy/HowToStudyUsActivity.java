package com.edge.greasy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HowToStudyUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_study_us);

        ListView howToStudyList = (ListView) findViewById(R.id.howToStudyList);

        String[] values = new String[] {
                "",
                "Coming Soon",
                ""
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        // Assign adapter to ListView
        howToStudyList.setAdapter(adapter);
    }
}
