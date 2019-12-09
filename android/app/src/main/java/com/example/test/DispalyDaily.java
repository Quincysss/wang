package com.example.test;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.json.JSONArray;
import org.json.JSONObject;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class DispalyDaily extends Fragment {
    View vDisplayDaily;
    ListView display;
    ListView displayfood;
    ListView searchFood;
    Button submit;
    EditText search;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        vDisplayDaily = inflater.inflate(R.layout.fragment_daily, container, false);
        display = (ListView) vDisplayDaily.findViewById(R.id.displaycate);
        displayfood = (ListView) vDisplayDaily.findViewById(R.id.displayFood);
        submit = (Button)vDisplayDaily.findViewById(R.id.submitSearch);
        search = (EditText) vDisplayDaily.findViewById(R.id.search);
        searchFood = (ListView)vDisplayDaily.findViewById(R.id.searchFood);
        read a = new read();
        a.execute();
        return vDisplayDaily;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        display.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, Object> data = (HashMap<String, Object>) display.getItemAtPosition(position);
                String name = data.get("category").toString();
                readfood a = new readfood();
                a.execute(name.replace("\"",""));
            }
        });
        displayfood.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String,Object> data = (HashMap<String, Object>)displayfood.getItemAtPosition(position);
                String name = data.get("food").toString();
                number_dialog a = new number_dialog();
                FragmentManager manager = getFragmentManager();
                Bundle bundle = new Bundle();
                bundle.putString("name",name);
                a.setArguments(bundle);
                a.show(manager,"Food number");
            }
        });
        submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = new ProgressDialog(vDisplayDaily.getContext(),
                        R.style.Theme_AppCompat_Dialog_Alert);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Search food ...");
                progressDialog.show();
                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                Search search = new Search();
                                search.execute();
                                progressDialog.dismiss();
                            }
                        }, 3000);
            }
        });
    }

    private class read extends AsyncTask<Void, Void, Void> {
        List<String> a = new ArrayList<>();

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL url = new URL("http://192.168.177.1:8080/assignment2/webresources/wasd.food");
                HttpURLConnection conn = null;
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("ACCEPT", "application/json");
                Scanner input = new Scanner(conn.getInputStream());
                SharedPreferences food = vDisplayDaily.getContext().getSharedPreferences("food", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = food.edit();
                while (input.hasNextLine()) {
                    String q = input.nextLine();
                    JsonArray json = new JsonParser().parse(q).getAsJsonArray();
                    for(int i = 0;i<json.size();i++)
                    {
                        String c = json.get(i).getAsJsonObject().get("category").toString();
                        if(! a.contains(c))
                            a.add(c);
                        String foodid = json.get(i).getAsJsonObject().get("foodid").toString();
                        edit.putString("foodid",foodid);
                    }
                    edit.commit();
                }
            } catch (Exception e) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void data) {
            List<HashMap<String, Object>> result = new ArrayList<>();
            for (String i : a) {
                HashMap<String, Object> x = new HashMap<>();
                x.put("category", i);
                result.add(x);
            }

            SimpleAdapter a = new SimpleAdapter(vDisplayDaily.getContext(), result, R.layout.dailylist,
                    new String[]{"category"}, new int[]{R.id.category});
            display.setAdapter(a);
        }
    }

    private class readfood extends AsyncTask<String, Void, Void> {
        List<String> a = new ArrayList<>();

        @Override
        protected Void doInBackground(String... data) {
            try {
                SharedPreferences share = vDisplayDaily.getContext().getSharedPreferences("foodnumber",Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = share.edit();
                URL url = new URL("http://192.168.177.1:8080/assignment2/webresources/wasd.food/findByCategory/" + data[0]);
                HttpURLConnection conn = null;
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("ACCEPT", "application/json");
                Scanner input = new Scanner(conn.getInputStream());
                while (input.hasNextLine()) {
                    JsonArray json = new JsonParser().parse(input.nextLine()).getAsJsonArray();
                    for(int i = 0;i<json.size();i++)
                    {
                        a.add(json.get(i).getAsJsonObject().get("foodname").toString());
                        edit.putString(json.get(i).getAsJsonObject().get("foodname").toString(),json.get(i).getAsJsonObject().get("foodid").toString());
                    }
                }
                edit.commit();
                conn.disconnect();
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void data) {
            List<HashMap<String, Object>> result = new ArrayList<>();
            for (String i : a) {
                HashMap<String, Object> x = new HashMap<>();
                x.put("food", i);
                result.add(x);
            }

            SimpleAdapter a = new SimpleAdapter(vDisplayDaily.getContext(), result, R.layout.foodlist,
                    new String[]{"food"}, new int[]{R.id.food});
            displayfood.setAdapter(a);
        }
    }

    public class Search extends AsyncTask<Void,Void,HashMap<String,List<String>>> {

        @Override
        protected HashMap<String, List<String>> doInBackground(Void... param) {
            HashMap<String, List<String>> a = new HashMap<>();
            String text = "";
            try {
                List<String> p = new ArrayList<>();
                List<String> name = new ArrayList<>();
                List<String> url = new ArrayList<>();
                JSONObject json = fat.getFood(search.getText().toString());
                JSONArray food = json.getJSONArray("food");
                for (int i = 0; i < food.length(); i++) {
                    JSONObject P = food.getJSONObject(i);
                    String b = googleSearch.search(P.get("food_name").toString(), new String[]{"num"}, new
                            String[]{"1"});
                    if (b == "" || b == "NO INFO FOUND") {
                        continue;
                    } else {
                        JsonObject w = new JsonParser().parse(b.replace("[", "").replace("]", "")).getAsJsonObject();
                        String picture = w.get("src").toString().replace("\"", "");
                        url.add(picture);
                        p.add(P.get("food_description").toString());
                        name.add(P.get("food_name").toString());
                    }
                }
                a.put("des", p);
                a.put("name", name);
                a.put("picture", url);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return a;
        }

        @Override
        protected void onPostExecute(HashMap<String, List<String>> json) {
                SharedPreferences foodlist = vDisplayDaily.getContext().getSharedPreferences("foodlist", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = foodlist.edit();
                ArrayList<SubjectData> arrayList = new ArrayList<>();
                if (! json.isEmpty()) {
                    for (int i = 0; i < json.get("des").size(); i++) {
                        String[] des = json.get("des").get(i).split("\\|");
                        String a = "";
                        for (String b : des) {
                            a += b + ",";
                        }
                        edit.putString(json.get("name").get(i).toString(), a);
                        edit.commit();
                        arrayList.add(new SubjectData(json.get("name").get(i).toString(), json.get("picture").get(i).replace("\"", "")));
                    }
                    CustomAdapter customAdapter = new CustomAdapter(vDisplayDaily.getContext(), arrayList);
                    searchFood.setAdapter(customAdapter);
                } else {
                Toast.makeText(vDisplayDaily.getContext(),"no food is found",Toast.LENGTH_LONG).show();
            }
        }
    }
}


