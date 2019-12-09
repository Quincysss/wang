package com.example.test;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class DisplayMap extends Fragment implements OnMapReadyCallback {
    View vDisplayMap;
    private GoogleMap mMap;
    MapView mapView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        vDisplayMap = inflater.inflate(R.layout.fragement_map, container, false);
        return vDisplayMap;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstance) {
        super.onViewCreated(view, savedInstance);
        mapView = (MapView) vDisplayMap.findViewById(R.id.map);
        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        location a = new location();
        a.execute(checkLogin.getAddress(), checkLogin.getPostcode());
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (!marker.getTitle().equals("Home Marker")) {
                    double latitude = marker.getPosition().latitude;
                    double longitude = marker.getPosition().longitude;
                    String snippet = marker.getSnippet();
                    String[] data = snippet.split("-");
                    Bundle bundle = new Bundle();
                    bundle.putString("name", marker.getTitle());
                    bundle.putString("open", data[0]);
                    bundle.putString("rating", data[1]);
                    bundle.putDouble("latitude", latitude);
                    bundle.putDouble("longitude", longitude);
                    marker_dialog marker_view = new marker_dialog();
                    marker_view.setArguments(bundle);
                    FragmentManager fragmentManager = getFragmentManager();
                    marker_view.show(fragmentManager, "marker");
                }
                return false;
            }
        });
    }

    public class location extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String address = "";
            String x = strings[0].replace(" ", "+") + "+" + strings[1];
            try {
                URL url = new URL("https://maps.google.com/maps/api/geocode/json?address=" + x + "&key=AIzaSyCjJzYgnpV1rx49Girh0gc9eWrhpP7ssxA");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Accept", "application/json");
                conn.setDoOutput(true);
                Scanner scanner = new Scanner(conn.getInputStream());
                while (scanner.hasNextLine()) {
                    address += scanner.nextLine();
                }
                JsonObject json = new JsonParser().parse(address).getAsJsonObject();
                JsonArray t = json.get("results").getAsJsonArray();
                JsonObject q = t.get(0).getAsJsonObject();
                JsonObject p = q.get("geometry").getAsJsonObject();
                JsonObject a = p.get("location").getAsJsonObject();
                String la = a.get("lat").getAsString();
                String lo = a.get("lng").getAsString();
                return la + "," + lo;
            } catch (Exception e) {
                return "error";
            }
        }

        @Override
        public void onPostExecute(String string) {
            String[] a = string.split(",");
            LatLng sydney = new LatLng(Double.parseDouble(a[0]), Double.parseDouble(a[1]));
            mMap.addMarker(new MarkerOptions().position(sydney).title("Home Marker"));
            float zoom = 13.0f;
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, zoom));
            park pa = new park();
            pa.execute(string);
        }

        public class park extends AsyncTask<String, Void, HashMap<String, List<String>>> {
            boolean flag = true;

            @Override
            protected HashMap<String, List<String>> doInBackground(String... strings) {
                try {
                    HashMap<String, List<String>> a = new HashMap<>();
                    List<String> ln = new ArrayList<>();
                    List<String> name = new ArrayList<>();
                    List<String> open_now = new ArrayList<>();
                    List<String> rating = new ArrayList<>();
                    String park = "";
                    URL url = new URL("https://maps.googleapis.com/maps/api/place/nearbysearch/json?&location=" + strings[0] + "&radius=5000&types=park&key=AIzaSyCjJzYgnpV1rx49Girh0gc9eWrhpP7ssxA");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(15000);
                    conn.setConnectTimeout(15000);
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setRequestProperty("Accept", "application/json");
                    conn.setDoOutput(true);
                    Scanner scanner = new Scanner(conn.getInputStream());
                    while (scanner.hasNextLine()) {
                        park += scanner.nextLine();
                    }
                    JsonArray json = new JsonParser().parse(park).getAsJsonObject().get("results").getAsJsonArray();
                    for (JsonElement j : json) {
                        JsonObject q = j.getAsJsonObject().get("geometry").getAsJsonObject().get("location").getAsJsonObject();
                        String lat = q.get("lat").getAsString();
                        String lon = q.get("lng").getAsString();
                        ln.add(lat + "," + lon);
                        name.add(j.getAsJsonObject().get("name").getAsString());
                        try {
                            open_now.add(j.getAsJsonObject().get("opening_hours").getAsJsonObject().get("open_now").getAsString());
                        } catch (Exception e) {
                            open_now.add("no data");
                        }
                        try {
                            rating.add(j.getAsJsonObject().get("rating").getAsString());
                        } catch (Exception e) {
                            rating.add("no data");
                        }
                    }
                    a.put("location", ln);
                    a.put("name", name);
                    a.put("open", open_now);
                    a.put("rating", rating);
                    return a;
                } catch (Exception e) {
                    flag = false;
                    return null;
                }
            }

            @Override
            public void onPostExecute(HashMap<String, List<String>> da) {
                if (flag == true) {
                    List<String> location = da.get("location");
                    List<String> name = da.get("name");
                    List<String> open = da.get("open");
                    List<String> rating = da.get("rating");
                    for (int i = 0; i < location.size(); i++) {
                        String[] p = location.get(i).split(",");
                        LatLng sydney = new LatLng(Double.parseDouble(p[0]), Double.parseDouble(p[1]));
                        MarkerOptions marker = new MarkerOptions().position(sydney).title(name.get(i)).snippet(open.get(i) + "-" + rating.get(i)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
                        mMap.addMarker(marker);
                    }
                } else
                    Toast.makeText(vDisplayMap.getContext(), "the address is not right", Toast.LENGTH_LONG).show();
            }
        }
    }
}
