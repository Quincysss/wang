package com.example.test;

import android.app.AlarmManager;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.UrlQuerySanitizer;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

public class SecondActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);
                Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                setSupportActionBar(toolbar);
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                        this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
                drawer.addDrawerListener(toggle);
                toggle.syncState();
                NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                Menu menu = navigationView.getMenu();
                navigationView.setNavigationItemSelectedListener(this);
                getSupportActionBar().setTitle("Calorie Tracker");
                FragmentManager fragmentManager = getFragmentManager();
                Fragment mainFragment = null;
                mainFragment = new MainFragment();
                fragmentManager.beginTransaction().replace(R.id.content_frame, mainFragment).commit();
        }

        @Override
        public boolean onPrepareOptionsMenu(Menu menu)
        {
            TextView a = (TextView) findViewById(R.id.menu_name);
            a.setText(checkLogin.getUsername());
            TextView q = (TextView)findViewById(R.id.textView);
            q.setText(checkLogin.getEmail());
            return super.onPrepareOptionsMenu(menu);
        }


        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            if (checkLogin.getCheck()) {
                Boolean b = true;
                Button button = (Button) findViewById(R.id.headerImage);
                button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        FragmentManager fragmentManager = getFragmentManager();
                        MainFragment main = new MainFragment();
                        fragmentManager.beginTransaction().replace(R.id.content_frame, main).commit();
                        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                        drawer.closeDrawer(GravityCompat.START);
                    }
                });
                int id = item.getItemId();
                Fragment nextFragment = null;
                if (id != 0) {
                    switch (id) {
                        case R.id.nav_calorie:
                            nextFragment = new DisplayCalorie();
                            break;
                        case R.id.nav_daily:
                            nextFragment = new DispalyDaily();
                            break;
                        case R.id.nav_home:
                            nextFragment = new MainFragment();
                            break;
                        case R.id.nav_map:
                            nextFragment = new DisplayMap();
                            break;
                        case R.id.nav_report:
                            nextFragment = new DisplayReport();
                            break;
                        case R.id.nav_step:
                            nextFragment = new DisplayStep();
                            break;
                        case R.id.log_out:
                            checkLogin.setCheck(false);
                            Intent intent = new Intent(SecondActivity.this,
                                    MainActivity.class);
                            startActivity(intent);
                            b = false;
                        default:
                            break;
                    }
                    if (b) {
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.content_frame, nextFragment).commit();
                        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                        drawer.closeDrawer(GravityCompat.START);
                    }
                }
                return true;
            }
            Intent intent = new Intent(SecondActivity.this,
                    MainActivity.class);
            startActivity(intent);
            return false;
            }
}
