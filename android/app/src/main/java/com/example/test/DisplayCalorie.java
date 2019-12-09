package com.example.test;

import android.app.Fragment;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class DisplayCalorie extends Fragment {
    View vDisplayCalorie;
    TextView goal;
    TextView step;
    TextView consumed;
    TextView burned;
    StepsDatabase db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        vDisplayCalorie = inflater.inflate(R.layout.fragement_calorie, container, false);
        goal = (TextView) vDisplayCalorie.findViewById(R.id.calorie_goal);
        step = (TextView) vDisplayCalorie.findViewById(R.id.calorie_step);
        consumed = (TextView) vDisplayCalorie.findViewById(R.id.calorie_consumed);
        burned = (TextView) vDisplayCalorie.findViewById(R.id.calorie_burned);
        SharedPreferences share = vDisplayCalorie.getContext().getSharedPreferences("goal", Context.MODE_PRIVATE);
        Float data = share.getFloat(String.valueOf(checkLogin.getId()), 0);
        goal.setText(String.valueOf(data));
        cosume b = new cosume();
        b.execute();
        step a = new step();
        a.execute();
        burned c = new burned();
        c.execute();
        return vDisplayCalorie;
    }

    public class step extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            db = Room.databaseBuilder(vDisplayCalorie.getContext(),
                    StepsDatabase.class, "CustomerDatabase")
                    .fallbackToDestructiveMigration()
                    .build();
            double x = 0.0;
            List<StepData> a = db.stepsDao().findByUserId(checkLogin.getId());
            for (StepData b : a) {
                x += b.getStep();
            }
            return String.valueOf(x);
        }

        @Override
        protected void onPostExecute(String data) {
            step.setText(String.valueOf(data));
        }
    }

    public class cosume extends AsyncTask<Void, Void, String> {


        @Override
        protected String doInBackground(Void... voids) {
            try {
                String data = "";
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String date = format.format(new Date());
                URL url = new URL("http://192.168.177.1:8080/assignment2/webresources/wasd.consumption/findByDateAndId/" + date + "/" + checkLogin.getId());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Accept", "application/json");
                StringBuilder result = new StringBuilder();
                InputStream in = new BufferedInputStream(conn.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                JsonObject json = new JsonParser().parse(result.toString()).getAsJsonObject();
                data = json.get("consumed").getAsString();
                return data;
            } catch (Exception e) {
                return "no data";
            }
        }

        @Override
        protected void onPostExecute(String data) {
            if (data.equals("no data")) {
                Toast.makeText(vDisplayCalorie.getContext(), data, Toast.LENGTH_SHORT);
            } else {
                consumed.setText(data);
            }
        }
    }

    public class burned extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            String x = "";
            try {
                URL url = new URL("http://192.168.177.1:8080/assignment2/webresources/wasd.report/calculateStep/" + checkLogin.getId());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Accept", "application/json");
                StringBuilder result = new StringBuilder();
                InputStream in = new BufferedInputStream(conn.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                JsonObject json = new JsonParser().parse(result.toString()).getAsJsonObject();
                String burn = json.get("burn").getAsString();
                x = burn;
                reader.close();
                conn.disconnect();
            } catch (Exception e) {
                return "error";
            }
            try {
                URL url = new URL("http://192.168.177.1:8080/assignment2/webresources/wasd.report/calcualteDailyBMR/" + checkLogin.getId());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Accept", "application/json");
                StringBuilder result = new StringBuilder();
                InputStream in = new BufferedInputStream(conn.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                JsonObject json = new JsonParser().parse(result.toString()).getAsJsonObject();
                String bmr = json.get("dbmr").getAsString();
                x += "," + bmr;
                reader.close();
                conn.disconnect();
                return x;
            } catch (Exception e) {
                return "error";
            }
        }

        public void onPostExecute(String data) {
            String[] a = data.split(",");
            burned.setText(String.valueOf(Double.parseDouble(step.getText().toString()) * Double.parseDouble(a[0])+Double.parseDouble(a[1])));
        }


    }
}
