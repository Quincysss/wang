package com.example.test;

import android.app.Fragment;
import android.app.FragmentManager;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.MessageDigest;
import java.util.List;

public class login extends Fragment implements  View.OnClickListener,View.OnFocusChangeListener{
    View vlogin;
    Button loginButton;
    Button signButton;
    EditText username;
    EditText password;
    TextView error;
    CheckBox check;
    userDatabase db = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        vlogin = inflater.inflate(R.layout.activity, container, false);
        db = Room.databaseBuilder(vlogin.getContext(),
                userDatabase.class, "userDatabase")
                .fallbackToDestructiveMigration()
                .build();
        loginButton = (Button)vlogin.findViewById(R.id.loginButton);
        signButton = (Button)vlogin.findViewById(R.id.signButton);
        username = (EditText)vlogin.findViewById(R.id.username);
        password = (EditText)vlogin.findViewById(R.id.password);
        username.setOnFocusChangeListener(this);
        check = (CheckBox)vlogin.findViewById(R.id.checkbo);
        error = (TextView) vlogin.findViewById(R.id.error);
        loginButton.setOnClickListener(this);
        signButton.setOnClickListener(this);
        return vlogin;
    }

    @Override
    public void onClick(View v) {
        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment = null;
        switch(v.getId()) {
            case R.id.loginButton:
                String passwordMessage = password.getText().toString();
                Hash hash = new Hash();
                String passwordHash = hash.Hash(vlogin.getContext(),passwordMessage);
                check a = new check();
                a.execute(username.getText().toString(),passwordHash);
                break;
            case R.id.signButton:
                fragment = new signup();
                fragmentManager.beginTransaction().replace(R.id.mainframe,fragment).commit();
                break;
        }

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(! hasFocus)
        {
            give a =new give();
            a.execute();
        }
    }


    private class check extends AsyncTask<String,Void,String>
    {
        @Override
        protected String doInBackground(String ... param)
        {
            checkLogin.setData(param[0],param[1]);
            if(checkLogin.getCheck())
            {
                if(! check.isChecked())
                {
                    writeusername w = new writeusername();
                    w.execute();
                }
                else
                {
                    writeAll a = new writeAll();
                    a.execute();
                }
                return "";
            }
            else
            {
                return "please enter the right username and password";
            }
        }

        @Override
        protected void onPostExecute(String data)
        {
            if (checkLogin.getCheck())
            {
                Intent intent = new Intent(vlogin.getContext(),
                        SecondActivity.class);
                startActivity(intent);
            }
            else
            {
                error.setText(data);
            }
        }
    }

    private class writeusername extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                userData userData = new userData(username.getText().toString(), "");
                long id = db.userDao().insert(userData);
            }
            catch (Exception e)
            {

            }
            return null;
        }
    }

    private class writeAll extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                userData user = db.userDao().findByUsername(username.getText().toString());
                user.setPassword(password.getText().toString());
                db.userDao().updateStep(user);
            }
            catch (Exception e)
            {
                userData user = new userData(username.getText().toString(),password.getText().toString());
                db.userDao().insert(user);
            }
            return null;
        }
    }

    public class give extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected Void doInBackground(Void... voids) {
            List<userData> user = db.userDao().getAll();
            for(userData a:user)
            {
                if(a.getUsername().equals(username.getText().toString()))
                {
                    password.setText(a.getPassword());
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void data)
        {
            String x = password.getText().toString();
            if(! x.equals(""))
            {
                check.setChecked(true);
            }
        }
    }
}
