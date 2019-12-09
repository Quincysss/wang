package com.example.test;
import android.content.Context;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import android.net.Uri;
import android.os.Build;
import android.util.*;
import android.util.Base64;
import android.util.Log;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.net.URL;
import java.util.*;
import org.json.*;

public class fat {
        /**
         * FatSecret Authentication
         * http://platform.fatsecret.com/api/default.aspx?screen=rapiauth
         * Reference
         * https://github.com/ethan-james/cookbox/blob/master/src/com/vitaminc4/cookbox/FatSecret.java
         */
        final static private String APP_METHOD = "GET";
        final static private String APP_KEY = "b5ebc57d7f134da6bfae802fefcd1883";
        static private String APP_SECRET = "6b5abccfccc74509a1d113575d0a7d6e&";
        final static private String APP_URL = "http://platform.fatsecret.com/rest/server.api";
        private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";
        private static Context context;

        public static JSONObject getFood(String ab) {
            List<String> params = new ArrayList<>(Arrays.asList(generateOauthParams()));
            String[] template = new String[1];
            params.add("method=foods.search");
            params.add("search_expression=" + Uri.encode(ab));
            params.add("oauth_signature=" + sign(APP_METHOD, APP_URL, params.toArray(template)));
            String line = "";
            HttpURLConnection connection = null;
            JSONObject foods = null;
            try {
                URL url = new URL(APP_URL + "?" + paramify(params.toArray(template)));
                connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(10000);
                connection.setConnectTimeout(15000);
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Accept", "application/json");
                StringBuilder builder = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null)
                    builder.append(line);
                JSONObject food = new JSONObject(builder.toString());
                foods = food.getJSONObject("foods");
                reader.close();
                connection.disconnect();
            } catch (Exception e) {
                Log.w("Fat", e.toString());
            }
            return foods;
        }
            public static String[] generateOauthParams() {
                Calendar calendar = Calendar.getInstance();
                String[] a = {
                        "oauth_consumer_key=" + APP_KEY,
                        "oauth_signature_method=HMAC-SHA1",
                        "oauth_timestamp=" + Long.valueOf(System.currentTimeMillis() * 2).toString(),
                        "oauth_nonce=" + nonce(),
                        "oauth_version=1.0",
                        "format=json"
                };
                return a;
            }

            public static String sign(String method, String uri, String[] params) {
                String[] p = {method, Uri.encode(uri), Uri.encode(paramify(params))};
                String s = join(p, "&");
                SecretKey sk = new SecretKeySpec(APP_SECRET.getBytes(), HMAC_SHA1_ALGORITHM);
                try {
                    Mac m = Mac.getInstance("HmacSHA1");
                    m.init(sk);
                    String signature = Uri.encode(new String(Base64.encode(m.doFinal(s.getBytes()), Base64.DEFAULT)).trim());
                    return signature;
                } catch (NoSuchAlgorithmException e) {
                    Log.w("error", e.getMessage());
                    return null;
                } catch (InvalidKeyException e) {
                    Log.w("error", e.getMessage());
                    return null;
                }
            }

            public static String paramify(String[] params) {
                String[] p = Arrays.copyOf(params, params.length);
                Arrays.sort(p);
                return join(p, "&");
            }

            public static String join(String[] array, String separator) {
                StringBuffer b = new StringBuffer();
                for (int i = 0; i < array.length; i++) {
                    if (i > 0) b.append(separator);
                    b.append(array[i]);
                }
                return b.toString();
            }

            public static String nonce() {
                Random r = new Random();
                StringBuffer n = new StringBuffer();
                for (int i = 0; i < r.nextInt(8) + 2; i++) n.append(r.nextInt(26) + 'a');
                return n.toString();
            }
        }
