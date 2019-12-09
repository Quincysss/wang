package com.example.test;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

public class checkLogin {
    static HashMap<String,Object> dictionary = new HashMap<String,Object>();
    static boolean check;
    static URL url;
    static String username;

    public static boolean check(String a,String b)
    {
        username = a;
        final String Base = "http://192.168.177.1:8080/assignment2/webresources/";
        final String methodPath = "wasd.crediental/findByUsernameAndPasswordHash/"+a+"/"+b;
        HttpURLConnection conn = null;
        String textResult = "";
        try {
            url = new URL(Base+methodPath);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            Scanner inStream = new Scanner(conn.getInputStream());
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
            JsonObject json = new JsonParser().parse(textResult.replace("[","").replace("]","")).getAsJsonObject();
            JsonObject s = new JsonParser().parse(json.get("userid").toString()).getAsJsonObject();
            dictionary.put("id",s.get("userid"));
            dictionary.put("email",s.get("email"));
            dictionary.put("address",s.get("address"));
            dictionary.put("postcode",s.get("postcode"));
            dictionary.put("firstname",s.get("username"));
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            conn.disconnect();
        }
    }

    public static boolean getCheck()
    {
        return check;
    }

    public static void setData(String a,String b)
    {
        check = check(a,b);
    }

    public static void setCheck(boolean x)
    {
        check = x;
    }

    public static int getId(){return Integer.parseInt(dictionary.get("id").toString());}

    public static String getEmail(){return dictionary.get("email").toString();}

    public static String getFirstname(){return dictionary.get("firstname").toString();}

    public static String getAddress(){return dictionary.get("address").toString();
    }
    public static String getPostcode(){return dictionary.get("postcode").toString();}
    public static String getUsername(){return username;}
}
