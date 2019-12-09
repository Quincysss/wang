package com.example.test;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.w3c.dom.Text;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;


public class number_dialog extends DialogFragment implements View.OnClickListener {
    View dialog;
    TextView message;
    TextView calorie;
    TextView foodname;
    TextView fat;
    TextView unit;
    TextView serving_amount;
    EditText number;
    Button submit;
    String name;
    JsonObject food = new JsonObject();
    String id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        dialog = inflater.inflate(R.layout.eat_food, container);
        message = (TextView)dialog.findViewById(R.id.eat_message);
        calorie = (TextView)dialog.findViewById(R.id.store_calorie);
        foodname = (TextView) dialog.findViewById(R.id.store_name);
        fat = (TextView)dialog.findViewById(R.id.store_fat);
        unit = (TextView)dialog.findViewById(R.id.store_unit);
        serving_amount = (TextView)dialog.findViewById(R.id.store_Amount);
        number = (EditText)dialog.findViewById(R.id.number);
        submit = (Button) dialog.findViewById(R.id.submit_number);
        name = getArguments().getString("name");
        submit.setOnClickListener(this);
        SharedPreferences foodlist = dialog.getContext().getSharedPreferences("foodnumber", Context.MODE_PRIVATE);
        id = foodlist.getString(name,"");
        readfood r = new readfood();
        r.execute(id);
        return dialog;
    }

    @Override
    public void onClick(View v) {

        post p = new post();
        p.execute();
    }

    public class readfood extends AsyncTask<String,Void,Void>
    {
        @Override
        protected Void doInBackground(String ... string) {
            final String Base_url = "http://192.168.177.1:8080/assignment2/webresources/";
            HttpURLConnection conn = null;
            try {
                URL url1 = new URL(Base_url + "wasd.food/findByFoodid/" + string[0]);
                conn = (HttpURLConnection) url1.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Accept", "application/json");
                Scanner inStream = new Scanner(conn.getInputStream());
                while (inStream.hasNextLine()) {
                    food = new JsonParser().parse(inStream.nextLine().replace("[", "").replace("]", "")).getAsJsonObject();
                }
                inStream.close();
                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onPostExecute(Void Void)
        {
            foodname.setText(food.get("foodname").toString());
            serving_amount.setText(food.get("servingAmount").toString());
            unit.setText(food.get("servingUnit").toString());
            fat.setText(food.get("fat").toString());
            calorie.setText(food.get("caloricAmount").toString());
        }
    }

    public class post extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... Void) {
            JsonObject user = new JsonObject();
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
            try {
                URL url = new URL(Base_url + "wasd.consumption");
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                Gson gson = new Gson();
                Date date = new Date();
                String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
                SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
                String da = dateFormat.format(date);
                consumptionPK c = new consumptionPK(da,Integer.parseInt(id),checkLogin.getId());
                String d = gson.toJson(c);
                JsonObject e = new JsonParser().parse(d).getAsJsonObject();
                consumption consumption = new consumption(e, food, Double.parseDouble(number.getText().toString()), user);
                String con = gson.toJson(consumption).replace("\\","");
                conn.setFixedLengthStreamingMode(con.getBytes().length);
                conn.setRequestProperty("Content-Type", "application/json");
                PrintWriter out = new PrintWriter(conn.getOutputStream());
                out.print(con);
                out.flush();
                int code = conn.getResponseCode();
                out.close();
                conn.disconnect();
                if (code == 204)
                    return "the record has been created";
                else
                    return "this food has been choosed today";
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "error";
        }

        @Override
        public void onPostExecute(String data)
        {
            message.setText(data);
        }

    }

}
