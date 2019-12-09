package com.example.test;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class googleSearch{
    private static final String key = "AIzaSyCjJzYgnpV1rx49Girh0gc9eWrhpP7ssxA";
    private static final String id = "006774490865108625428:vrtg87btakk";

    public static String search(String keyword,String[] param,String[] value) {
        keyword = keyword.replace(" ", "+");
        URL url = null;
        HttpURLConnection conn = null;
        String query = "";

        if (param != null && value != null) {
            for (int i = 0; i < param.length; i++) {
                query += "&";
                query += param[i];
                query += "=";
                query += value[i];
            }
        }
        try {
            url = new URL("https://www.googleapis.com/customsearch/v1?key=" + key + "&cx=" + id + "&q=" + keyword + query);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            String line;
            StringBuilder builder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = reader.readLine())!= null) {
                builder.append(line);
            }
            String src = "";
            try{
                JSONObject jsonObject = new JSONObject(builder.toString());
                JSONArray jsonArray = jsonObject.getJSONArray("items");
                if(jsonArray != null && jsonArray.length() > 0) {
                    JSONObject pagemap =jsonArray.getJSONObject(0).getJSONObject("pagemap");
                    src = pagemap.get("cse_thumbnail").toString();
                }
            }catch (Exception e){
                src = "NO INFO FOUND";
            }
            reader.close();
            return src;
        } catch (Exception e)

        {
        }finally {
            conn.disconnect();
        }
        return null;
    }
}
