package com.example.test;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

public class MainFragment extends Fragment implements View.OnClickListener {
    private View vMain;
    TextView welcome;
    EditText goal;
    Button submit;
    Button alarm;
    TextView message;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        vMain = inflater.inflate(R.layout.fragment_main, container, false);
        welcome = (TextView) vMain.findViewById(R.id.welcome);
        goal = (EditText)vMain.findViewById(R.id.goal);
        alarm = (Button)vMain.findViewById(R.id.alarm);
        submit = (Button)vMain.findViewById(R.id.setGoal);
        message = (TextView)vMain.findViewById(R.id.goalMessage);
        SharedPreferences setGoal = vMain.getContext().getSharedPreferences("goal", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = setGoal.edit();
        edit.putFloat("goal",0);
        edit.commit();
        alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar. getInstance();
                calendar.setTime( new Date());
                calendar.add(Calendar.MILLISECOND,1);
                setAlarm(calendar);
            }
        });
        submit.setOnClickListener(this);
        Calendar calendar = Calendar. getInstance();
        calendar.setTime( new Date());
        calendar.set(Calendar. HOUR_OF_DAY, 23);
        calendar.set(Calendar. MINUTE, 59);
        calendar.set(Calendar. SECOND, 0);
        calendar.set(Calendar. MILLISECOND, 0);
        setAlarm(calendar);
        if(checkLogin.getCheck())
        {
            welcome.setText( checkLogin.getFirstname().replace("\"",""));
        }
        return vMain;
    }

    @Override
    public void onClick(View v)
    {
        if (checkLogin.getCheck()) {
            try {
                SharedPreferences setGoal = v.getContext().getSharedPreferences("goal", Context.MODE_PRIVATE);
                Float input = Float.parseFloat(goal.getText().toString());
                if (setGoal.getFloat(String.valueOf(checkLogin.getId()), 0) == 0) {
                    message.setText("you has set a new goal");
                } else {
                    message.setText("you have change your goal");
                }
                SharedPreferences.Editor edit = setGoal.edit();
                edit.putFloat(String.valueOf(checkLogin.getId()), input);
                edit.commit();
            } catch (Exception e) {
                message.setText("please enter the valid number");
            }
        }
        else {
            Intent intent = new Intent(vMain.getContext(), MainActivity.class);
            startActivity(intent);
        }
    }

    public void setAlarm(Calendar calendar)
    {
        AlarmManager alarm = (AlarmManager)vMain.getContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(vMain.getContext(),alarm.class);
        intent.setAction("startAlarm");
        PendingIntent p = PendingIntent.getBroadcast(vMain.getContext(),0,intent,0);
        alarm.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,p);
    }
}
