package com.example.test;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class signup extends Fragment implements View.OnClickListener  {
    View vsign;
    Button submitButton;
    Button back;
    TextView error;
    EditText Dob;
    EditText username;
    EditText password;
    EditText vpassword;
    EditText email;
    EditText firstname;
    EditText surname;
    EditText height;
    EditText weight;
    EditText address;
    RadioGroup gender;
    EditText postcode;
    EditText step;
    Spinner level;
    String g;
    String l;
    List<String> list;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
        savedInstanceState) {
            vsign = inflater.inflate(R.layout.fragment_sign, container, false);
        g = "M";
        submitButton = (Button)vsign.findViewById(R.id.Signin);
        Dob = (EditText)vsign.findViewById(R.id.Dob);
        username = (EditText)vsign.findViewById(R.id.sign_name);
        firstname = (EditText)vsign.findViewById(R.id.firstname);
        surname = (EditText)vsign.findViewById(R.id.surname);
        email = (EditText)vsign.findViewById(R.id.email);
        weight = (EditText)vsign.findViewById(R.id.weight);
        height = (EditText)vsign.findViewById(R.id.height);
        address = (EditText)vsign.findViewById(R.id.address);
        back = (Button)vsign.findViewById(R.id.log);
        password = (EditText)vsign.findViewById(R.id.sign_password);
        vpassword = (EditText)vsign.findViewById(R.id.re_password);
        postcode = (EditText)vsign.findViewById(R.id.postcode);
        step = (EditText)vsign.findViewById(R.id.step);
        gender = (RadioGroup)vsign.findViewById(R.id.gender);
        level = (Spinner)vsign.findViewById(R.id.level);
        error = (TextView) vsign.findViewById(R.id.signerror);
        back.setOnClickListener(this);
        submitButton.setOnClickListener(this);
        Dob.setOnClickListener(this);
        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radio = (RadioButton) vsign.findViewById(checkedId);
                switch (radio.getText().toString()) {
                    case "Men":
                        g = "M";
                        break;
                    case "Women":
                        g = "F";
                        break;
                }
            }
        });
        initSpinner();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(vsign.getContext(), android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        level.setAdapter(adapter);
        level.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                l = list.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return vsign;
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.Signin:
                if(validate()) {
                    final ProgressDialog progressDialog = new ProgressDialog(vsign.getContext(),
                            R.style.Theme_AppCompat_Dialog_Alert);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("Creating Account...");
                    progressDialog.show();
                    new android.os.Handler().postDelayed(
                            new Runnable() {
                                public void run() {
                                    submit s = new submit();
                                    s.execute();
                                    progressDialog.dismiss();
                                }
                            }, 3000);
                }
                break;
            case R.id.Dob:
                final Calendar calendar = Calendar.getInstance();
                DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(Calendar.YEAR,year);
                        calendar.set(Calendar.MONTH,month);
                        calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                        String myFormat = "yyyy-MM-dd";
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                        Dob.setText(sdf.format(calendar.getTime()));
                    }
                };
                DatePickerDialog datePickerDialog = new DatePickerDialog(vsign.getContext(),date,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                DatePicker datePicker = datePickerDialog.getDatePicker();
                datePicker.setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
                break;
            case R.id.log:
                FragmentManager f = getFragmentManager();
                f.beginTransaction().replace(R.id.mainframe,new Home()).commit();
        }
    }

    private void initSpinner()
    {
        list = new ArrayList<String>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
    }

    private class submit extends AsyncTask<Void,Void,String>
    {

        @Override
        protected String doInBackground(Void... voids) {
            User user;
            HashMap<String, List<String>> dictionary = new HashMap<String, List<String>>();
            try {
                List<String> a = new ArrayList<String>();
                List<String> b = new ArrayList<String>();
                List<String> q = new ArrayList<String>();
                List<String> c = new ArrayList<>();
                URL url = new URL("http://192.168.177.1:8080/assignment2/webresources/wasd.crediental");
                HttpURLConnection conn = null;
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Accept", "application/json");
                Scanner inStream = new Scanner(conn.getInputStream());
                while (inStream.hasNextLine()) {
                    JsonArray json = new JsonParser().parse(inStream.nextLine()).getAsJsonArray();
                    for (JsonElement j : json) {
                        JsonObject x = j.getAsJsonObject();
                        JsonObject p = x.get("userid").getAsJsonObject();
                        c.add(x.get("credientalid").getAsString());
                        a.add(x.get("username").getAsString());
                        if (!b.contains(p.get("email").getAsString())) {
                            b.add(p.get("email").getAsString());
                        }
                        if (!q.contains(p.get("userid").getAsString())) {
                            q.add(p.get("userid").getAsString());
                        }
                    }
                }
                dictionary.put("id", q);
                dictionary.put("username", a);
                dictionary.put("email", b);
                dictionary.put("cid", c);
            } catch (Exception e) {
                return ("error");
            }
            if (dictionary.get("username").contains(username.getText().toString())) {
                return ("this username has exited");
            }
            if (dictionary.get("email").contains(email.getText().toString())) {
                return ("this email has exited");
            }
                try {
                    String id = "";
                    for (String t : dictionary.get("id")) {
                        id = t;
                    }
                    user = new User(Integer.parseInt(id) + 1, firstname.getText().toString(), surname.getText().toString(), email.getText().toString(),
                            Dob.getText().toString() + "T00:00:00+11:00", Double.parseDouble(height.getText().toString()), Double.parseDouble(weight.getText().toString()),
                            g, address.getText().toString(), Integer.parseInt(postcode.getText().toString()), Integer.parseInt(l),
                            Integer.parseInt(step.getText().toString()));
                    int b = sign.createUser(user);
                } catch (Exception e) {
                    return "error";
                }
                try {
                    String cid = "";
                    for (String u : dictionary.get("cid")) {
                        cid = u;
                    }
                    SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
                    String d = date.format(new Date());
                    Hash hash = new Hash();
                    String passwordHash = hash.Hash(vsign.getContext(),password.getText().toString());
                    Account account = new Account(Integer.parseInt(cid) + 1, username.getText().toString(),passwordHash, d, user);
                    int a = sign.createAccount(account);
                    if (a == 204) {
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.mainframe, new Home()).commit();
                        return "the account has been created";
                    }
                    else
                    {
                        Toast.makeText(vsign.getContext(),"error",Toast.LENGTH_LONG);
                    }
                } catch (Exception e) {
                    return "error";

                }
            return null;
        }
        @Override
        public void onPostExecute(String data)
        {
            if (data.equals("error"))
                error.setText(data);
            else
                Toast.makeText(vsign.getContext(),data,Toast.LENGTH_LONG).show();
        }
    }
    public boolean validate(){
        boolean valid = true;

        String userName = username.getText().toString();
        String Password = password.getText().toString();
        String vPassword = vpassword.getText().toString();
        String firstName = firstname.getText().toString();
        String lastName = surname.getText().toString();
        String Address = address.getText().toString();
        String dob = Dob.getText().toString();
        String Email = email.getText().toString();
        String Height = height.getText().toString();
        String Weight = weight.getText().toString();
        String postCode = postcode.getText().toString();
        String Step = step.getText().toString();

        if (userName.isEmpty() || userName.length() < 3) {
            username.setError("at least 3 characters");
            valid = false;
        } else {
            username.setError(null);
        }
        if (Password.isEmpty() || Password.length() < 4 || Password.length() > 10) {
            password.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            password.setError(null);
        }
        if (vPassword.isEmpty() || ! (vPassword.equals(Password)))
        {
            vpassword.setError("please enter the same password");
            valid = false;
        }
        else
        {
            vpassword.setError(null);
        }
        if (dob.isEmpty())
        {
            Dob.setError("please choose a date");
            valid = false;
        }
        else
        {
            Dob.setError(null);
        }
        if (firstName.isEmpty()) {
            firstname.setError("Enter Valid firstName");
            valid = false;
        } else {
            firstname.setError(null);
        }
        if (lastName.isEmpty()) {
            surname.setError("Enter Valid lastName");
            valid = false;
        } else {
            surname.setError(null);
        }
        if (Address.isEmpty()) {
            address.setError("Enter Valid Address");
            valid = false;
        } else {
            address.setError(null);
        }

        if (Email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            email.setError("enter a valid email address");
            valid = false;
        } else {
            email.setError(null);
        }
        if (Height.isEmpty()) {
            height.setError("Enter Valid Height");
            valid = false;
        } else {
            height.setError(null);
        }
        if (Weight.isEmpty()) {
            weight.setError("Enter Valid Weight");
            valid = false;
        } else {
            weight.setError(null);
        }
        if (postCode.isEmpty()) {
            postcode.setError("Enter Valid number");
            valid = false;
        }else
        {
            postcode.setError(null);}
        return valid;
    }
}
