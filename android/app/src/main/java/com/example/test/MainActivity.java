package com.example.test;

import android.os.Bundle;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        FragmentManager f = getFragmentManager();
        f.beginTransaction().replace(R.id.mainframe, new Home()).commit();
    }
}
