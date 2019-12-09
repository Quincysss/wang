package com.example.test;

import android.arch.persistence.room.Room;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class alarm extends BroadcastReceiver {
    Context current_context;
    StepsDatabase db;
    double goals = 0.0;
    int steps = 0;
    JsonObject user = new JsonObject();
    double totalburn = 0.0;
    double consume = 0.0;
    reportPK key;

    @Override
    public void onReceive(Context context, Intent intent) {
        current_context = context;
        if ("startAlarm".equals(intent.getAction())) {
            consume consu = new consume();
            consu.execute();
        }
    }

    public class consume extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                db = Room.databaseBuilder(current_context,
                        StepsDatabase.class, "CustomerDatabase")
                        .fallbackToDestructiveMigration()
                        .build();
                List<StepData> step = db.stepsDao().findByUserId(checkLogin.getId());
                for (StepData a:step)
                {
                    steps += a.getStep();
                }
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
                consume = Double.parseDouble(json.get("consumed").getAsString());
                reader.close();
                conn.disconnect();
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        public void onPostExecute(Void data)
        {
            burned burn = new burned();
            burn.execute();
        }
    }

        public class burned extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                double each_step = 0.0;
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
                    each_step = Double.parseDouble(burn);
                    reader.close();
                    conn.disconnect();
                } catch (Exception e) {
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
                    double bmr = Double.parseDouble(json.get("dbmr").getAsString());
                    reader.close();
                    conn.disconnect();
                    totalburn = each_step * steps + bmr;
                } catch (Exception e) {
                }
                return null;
            }

            @Override
            public void onPostExecute(Void data) {
                readUser read_user = new readUser();
                read_user.execute();
            }
        }

    public class readUser extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            final String Base_url = "http://192.168.177.1:8080/assignment2/webresources/";
            HttpURLConnection conn = null;
            try {
                URL url = new URL(Base_url + "wasd.users/findByUserid/" + String.valueOf(checkLogin.getId()));
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Accept", "application/json");
                Scanner inStream = new Scanner(conn.getInputStream());
                while (inStream.hasNextLine()) {
                    user = new JsonParser().parse(inStream.nextLine().replace("[", "").replace("]", "")).getAsJsonObject();
                }
                inStream.close();
                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onPostExecute(Void data) {
            Post post = new Post();
            post.execute();
        }
    }

    public class Post extends AsyncTask<Void,Void,String>
    {

            @Override
            protected String doInBackground(Void... reports) {
                SharedPreferences goal = current_context.getSharedPreferences("goal", Context.MODE_PRIVATE);
                goals = (double) goal.getFloat(String.valueOf(checkLogin.getId()), 0);
                SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
                key = new reportPK(dateformat.format(new Date()), checkLogin.getId());
                report submit = new report(goals, key, consume, totalburn, steps, user);
                final String Base_url = "http://192.168.177.1:8080/assignment2/webresources/";
                HttpURLConnection con = null;
                try {
                    URL urltext = new URL(Base_url + "wasd.report");
                    con = (HttpURLConnection) urltext.openConnection();
                    Gson gson = new Gson();
                    String stringCourseJson = gson.toJson(submit);
                    con = (HttpURLConnection) urltext.openConnection();
                    con.setReadTimeout(10000);
                    con.setConnectTimeout(15000);
                    con.setRequestMethod("POST");
                    con.setDoOutput(true);
                    con.setFixedLengthStreamingMode(stringCourseJson.getBytes().length);
                    con.setRequestProperty("Content-Type", "application/json");
                    PrintWriter out = new PrintWriter(con.getOutputStream());
                    out.print(stringCourseJson);
                    out.flush();
                    goal.edit().clear().commit();
                    db.stepsDao().deleteAll();
                    db.close();
                    return "this report has been submit";
                } catch (Exception e) {
                    return "error";
                }
            }

            @Override
            public void onPostExecute(String data)
            {
                Toast.makeText(current_context,data,Toast.LENGTH_LONG).show();
            }
        }
}
