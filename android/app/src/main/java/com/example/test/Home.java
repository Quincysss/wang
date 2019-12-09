package com.example.test;

import android.app.Fragment;
import android.os.Bundle;
import android.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Home extends Fragment implements View.OnClickListener{
    View vhome;
    Button login;
    Button signup;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        vhome = inflater.inflate(R.layout.unlogin, container, false);
        login = (Button)vhome.findViewById(R.id.login1);
        signup = (Button)vhome.findViewById(R.id.sign1);
        login.setOnClickListener(this);
        signup.setOnClickListener(this);
        return vhome;
    }

    @Override
    public void onClick(View v)
    {
        FragmentManager f = getFragmentManager();
        Fragment a =null;
        switch(v.getId())
        {
            case R.id.login1:
                a = new login();
                break;
            case R.id.sign1:
                a = new signup();
                break;
        }
        f.beginTransaction().replace(R.id.mainframe,a).commit();
    }
}
