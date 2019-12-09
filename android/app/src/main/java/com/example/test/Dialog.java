package com.example.test;

import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Dialog extends DialogFragment implements View.OnClickListener{
    View dialog;
    TextView name;
    TextView category;
    TextView fat;
    TextView calorie;
    TextView amount;
    TextView unit;
    Button submit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        dialog = inflater.inflate(R.layout.food_dialog, container);
        name = (TextView)dialog.findViewById(R.id.food_name);
        category = (TextView)dialog.findViewById(R.id.food_category);
        fat = (TextView) dialog.findViewById(R.id.food_fat);
        calorie = (TextView) dialog.findViewById(R.id.food_calorie);
        amount = (TextView) dialog.findViewById(R.id.food_amount);
        unit = (TextView) dialog.findViewById(R.id.food_unit);
        submit = (Button) dialog.findViewById(R.id.submitFood);
        SharedPreferences foodlist = dialog.getContext().getSharedPreferences("foodlist", Context.MODE_PRIVATE);
        String name = foodlist.getString("name","");
        String detail = foodlist.getString(name,"");
        set a = new set();
        a.execute(name,detail);
        submit.setOnClickListener(this);
        return dialog;
    }

    @Override
    public void onClick(View v) {
        storeFood store = new storeFood();
        store.execute();
    }

    public class set extends AsyncTask<String,Void,List<String>>
    {

        @Override
        protected List<String> doInBackground(String... strings) {
            List<String> a = new ArrayList<>();
            a.add(strings[0]);
            String data = strings[1].replace("-", ",");
            String[] d = data.split(",");
            String[] f = d[0].split(" ");
            int number = 0;
            for(int i = 0;i<f[1].length();i++)
            {
                if(! (Character.isDigit(f[1].charAt(i)) || (f[1].charAt(i) == '/')))
                {
                    number = i;
                    break;
                }
            }
            if(number == 0 )
            {
                a.add("1");
                a.add("each");
            }
            else {
                a.add(f[1].substring(0, number));
                a.add(f[1].substring(number));
            }
            for(int i = 1;i < d.length -2 ; i ++)
            {
                String[] e = d[i].split(":");
                a.add(e[1]);
            }
            return a;
        }

        @Override
        public void onPostExecute(List<String> data)
        {
            name.setText(data.get(0));
            category.setText("food");
            amount.setText(data.get(1));
            unit.setText(data.get(2));
            calorie.setText(data.get(3));
            fat.setText(data.get(4));
        }
    }

    public class storeFood extends AsyncTask<Void,Void,String>
    {

        @Override
        protected String doInBackground(Void... voids) {
            try
            {
                URL url = new URL("http://192.168.177.1:8080/assignment2/webresources/wasd.food");
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                SharedPreferences foodid = dialog.getContext().getSharedPreferences("food",Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = foodid.edit();
                int id = Integer.parseInt(foodid.getString("foodid","").toString())+1;
                Food food = new Food(Double.parseDouble(cut(calorie.getText().toString().trim())),category.getText().toString().trim(),Double.parseDouble(cut(fat.getText().toString().trim())),
                        id,name.getText().toString().trim(),
                        Double.parseDouble(amount.getText().toString().trim()),unit.getText().toString().trim());
                Gson gson = new Gson();
                String foodd = gson.toJson(food);
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setFixedLengthStreamingMode(foodd.getBytes().length);
                conn.setRequestProperty("Content-Type", "application/json");
                PrintWriter out = new PrintWriter(conn.getOutputStream());
                out.print(foodd);
                out.flush();
                edit.putString("foodid",String.valueOf(id));
                edit.commit();
                return "ok";
            }
            catch (Exception e)
            {
                return "error";
            }
        }

        @Override
        public void onPostExecute(String data)
        {
            Toast.makeText(dialog.getContext(),data,Toast.LENGTH_SHORT).show();
        }

        public String cut(String a)
        {
            int number = 0;
            for(int i =0;i<a.length();i++)
            {
                if(! Character.isDigit(a.charAt(i)))
                {
                    number = i;
                    break;
                }
            }

            return a.substring(0,number);
        }
    }
 }