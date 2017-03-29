package com.edge.greasy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.edge.greasy.database.SqlLiteDbHelper;
import com.edge.greasy.utility.DataHolder;
import com.edge.greasy.utility.Utility;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private Button hard_wordsBtn, easy_wordsBtn, skip_wordsBtn, all_wordsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DataHolder.mHelper = new SqlLiteDbHelper(this);
        DataHolder.mHelper.openDataBase();

        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

            /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });*/

        hard_wordsBtn = (Button) findViewById(R.id.hard_words);
        easy_wordsBtn = (Button) findViewById(R.id.easy_Words);
        skip_wordsBtn = (Button) findViewById(R.id.skip_words);
        all_wordsBtn = (Button) findViewById(R.id.all_words);

        hard_wordsBtn.setOnClickListener(this);
        easy_wordsBtn.setOnClickListener(this);
        skip_wordsBtn.setOnClickListener(this);
        all_wordsBtn.setOnClickListener(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utility.refreshData(this, DataHolder.mHelper);

        easy_wordsBtn.setText("Easy Words " + "(" + DataHolder.getCountForCategory("Easy") + ")");

        skip_wordsBtn.setText("Skipped Words " + "(" + DataHolder.getCountForCategory("Skip") + ")");

        hard_wordsBtn.setText("Hard Words " + "(" + DataHolder.getCountForCategory("Hard") + ")");

        all_wordsBtn.setText("All Words " + "(" + DataHolder.getCountForCategory("All") + ")");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id){
            case R.id.action_searchWord:
                Intent intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_hard) {
            Intent allIntent = new Intent(this, HardWordsActivity.class);
            startActivity(allIntent);
        } else if (id == R.id.nav_easy) {
            Intent allIntent = new Intent(this, EasyWordsActivity.class);
            startActivity(allIntent);
        } else if (id == R.id.nav_skip) {
            Intent allIntent = new Intent(this, SkipWordsActivity.class);
            startActivity(allIntent);
        } else if (id == R.id.nav_all) {
            Intent allIntent = new Intent(this, AllWordsActivity.class);
            startActivity(allIntent);
        } else if (id == R.id.nav_aboutUs) {
            Intent allIntent = new Intent(this, AboutUsActivity.class);
            startActivity(allIntent);
        } else if (id == R.id.nav_howToStudy) {
            Intent allIntent = new Intent(this, HowToStudyUsActivity.class);
            startActivity(allIntent);
        }else if (id == R.id.nav_subscription) {
            Intent allIntent = new Intent(this, SubscriptionActivity.class);
            startActivity(allIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.hard_words:
                Intent hardIntent = new Intent(this, HardWordsActivity.class);
                startActivity(hardIntent);
                break;
            case R.id.easy_Words:
                Intent easyIntent = new Intent(this, EasyWordsActivity.class);
                startActivity(easyIntent);
                break;
            case R.id.skip_words:
                Intent skipIntent = new Intent(this, SkipWordsActivity.class);
                startActivity(skipIntent);
                break;
            case R.id.all_words:
                Intent allIntent = new Intent(this, AllWordsActivity.class);
                startActivity(allIntent);
                break;
            default:
                break;
        }
    }
}
