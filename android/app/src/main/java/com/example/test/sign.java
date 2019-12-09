package com.example.test;

import com.google.gson.Gson;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class sign {
    public static int createUser(User user) {
        int response = 0;
        URL url = null;
        HttpURLConnection conn = null;
        final String BASE_URL = "http://192.168.177.1:8080/assignment2/webresources";
        final String methodPath = "/wasd.users";
        try {
        Gson gson = new Gson();
        String stringCourseJson = gson.toJson(user);
        url = new URL(BASE_URL + methodPath);
        conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000);
        conn.setConnectTimeout(15000);
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setFixedLengthStreamingMode(stringCourseJson.getBytes().length);
        conn.setRequestProperty("Content-Type", "application/json");
        PrintWriter out = new PrintWriter(conn.getOutputStream());
        out.print(stringCourseJson);
        out.flush();
        response = conn.getResponseCode();
        out.close();
    } catch(Exception e)
    {
        e.printStackTrace();
    } finally
    {
        conn.disconnect();
    }
    return response;
}

public static int createAccount(Account account)
{
    int response = 0;
    URL url = null;
    HttpURLConnection conn = null;
    try {
        Gson gson = new Gson();
        String stringCourseJson = gson.toJson(account);
        String accounti = stringCourseJson.replace("\\","");
        url = new URL("http://192.168.177.1:8080/assignment2/webresources/wasd.crediental/");
        conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000);
        conn.setConnectTimeout(15000);
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setFixedLengthStreamingMode(accounti.getBytes().length);
        conn.setRequestProperty("Content-Type", "application/json");
        PrintWriter out = new PrintWriter(conn.getOutputStream());
        out.print(accounti);
        out.flush();
        response = conn.getResponseCode();
        out.close();
    }
    catch (Exception e)
    {
        e.printStackTrace();
    } finally
    {
        conn.disconnect();
    }
    return response;
}
}
