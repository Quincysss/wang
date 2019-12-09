package com.example.test;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class DisplayReport extends Fragment implements View.OnClickListener {
    View vDisplayReport;
    BarChart barChart;
    PieChart pieChart;
    Button lineButton;
    Button pieButton;
    Button create;
    TextView error;
    EditText start;
    EditText end;
    int i = 1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        vDisplayReport = inflater.inflate(R.layout.fragment_report, container, false);
        barChart = (BarChart)vDisplayReport.findViewById(R.id.linechart);
        pieChart = (PieChart)vDisplayReport.findViewById(R.id.piechart);
        lineButton = (Button)vDisplayReport.findViewById(R.id.lineButton);
        pieButton = (Button)vDisplayReport.findViewById(R.id.pieButton);
        error = (TextView) vDisplayReport.findViewById(R.id.test);
        start = (EditText)vDisplayReport.findViewById(R.id.startDate);
        end = (EditText)vDisplayReport.findViewById(R.id.endDate);
        create = (Button)vDisplayReport.findViewById(R.id.create);
        lineButton.setOnClickListener(this);
        pieButton.setOnClickListener(this);
        start.setOnClickListener(this);
        end.setOnClickListener(this);
        create.setOnClickListener(this);
        return vDisplayReport;
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.pieButton:
                i = 2;
                end.setVisibility(View.INVISIBLE);
                end.setEnabled(false);
                break;
            case R.id.lineButton:
                i = 3;
                end.setVisibility(View.VISIBLE);
                end.setEnabled(true);
                break;
            case R.id.startDate:
                final Calendar calendar = Calendar.getInstance();
                DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(Calendar.YEAR,year);
                        calendar.set(Calendar.MONTH,month);
                        calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                        String myFormat = "yyyy-MM-dd";
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                        start.setText(sdf.format(calendar.getTime()));
                    }
                };
                DatePickerDialog datePickerDialog = new DatePickerDialog(vDisplayReport.getContext(),date,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                DatePicker datePicker = datePickerDialog.getDatePicker();
                datePicker.setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
                break;
            case R.id.endDate:
                final Calendar cal = Calendar.getInstance();
                DatePickerDialog.OnDateSetListener da = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        cal.set(Calendar.YEAR,year);
                        cal.set(Calendar.MONTH,month);
                        cal.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                        String myFormat = "yyyy-MM-dd";
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                        end.setText(sdf.format(cal.getTime()));
                    }
                };
                DatePickerDialog datePickerDialog2 = new DatePickerDialog(vDisplayReport.getContext(),da,cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH));
                DatePicker datePicker2 = datePickerDialog2.getDatePicker();
                datePicker2.setMaxDate(System.currentTimeMillis());
                datePickerDialog2.show();
                break;
            case R.id.create:
                switch (i)
                {
                    case 1:
                        error.setText("please choose one kind of chart");
                        break;
                    case 2:
                        Pieshow r = new Pieshow();
                        r.execute(start.getText().toString());
                        break;
                    case 3:
                        Barshow q = new Barshow();
                        q.execute(start.getText().toString(),end.getText().toString());
                        break;
                }
                break;
        }
    }

    private class Pieshow extends AsyncTask<String,Void,String>
    {
        List<String> a = new ArrayList<>();
        List<String> b = new ArrayList<>();
        List<String> c = new ArrayList<>();
        @Override
        protected String doInBackground(String... param) {
            try {
                URL url = new URL("http://192.168.177.1:8080/assignment2/webresources/wasd.report/getRemainingCaloric/"+checkLogin.getId()+"/"+param[0]);
                HttpURLConnection conn = null;
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("ACCEPT", "application/json");
                StringBuilder result = new StringBuilder();
                InputStream in = new BufferedInputStream(conn.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                    JsonObject json = new JsonParser().parse(result.toString()).getAsJsonObject();
                    a.add(json.get("totalConsumed").toString().replace("\"",""));
                    b.add(json.get("totalBurned").toString().replace("\"",""));
                    c.add(json.get("remain").toString().replace("\"",""));
                return "the pie chart is created";
            } catch (Exception e) {
                return "error";
            }
        }


        @Override
        protected void onPostExecute(String data)
        {
            pieChart.setVisibility(View.INVISIBLE);
            barChart.setVisibility(View.INVISIBLE);
            barChart.clear();
            pieChart.clear();
            error.setText(data);
            String remain = "";
            String label = "calorie surplus";
            if(c.get(0).contains("-"))
            {
                remain = c.get(0).replace("-","");
                label = "calorie deficit";
            }
            else
            {
                remain = c.get(0);
            }
            final String[] name = new String[]{"Consumed","Burned",label};
            float total = Float.parseFloat(a.get(0)) + Float.parseFloat(b.get(0)) + Float.parseFloat(remain);
            float[] number = new float[3];
            number[0] = (Float.parseFloat(a.get(0))/total)*100 ;
            number[1] = (Float.parseFloat(b.get(0))/total)*100 ;
            number[2]= (Float.parseFloat(remain)/total)*100;
            final ArrayList<PieEntry> y = new ArrayList<>();
            for(int i =0;i<number.length;i++)
            {
                y.add(new PieEntry(number[i],name[i]));
            }

            PieDataSet pieDataSet = new PieDataSet(y,"data");
            ArrayList<Integer> colors = new ArrayList<Integer>();
            colors.add(Color.RED);
            colors.add(Color.BLUE);
            colors.add(Color.GREEN);
            pieDataSet.setColors(colors);
            PieData pieData = new PieData(pieDataSet);
            Description description = new Description();
            description.setText("");
            pieData.setDrawValues(true);
            pieData.setValueFormatter(new PercentFormatter());
            pieData.setValueTextSize(15f);
            pieChart.setDescription(description);
            pieChart.setData(pieData);
            pieChart.setVisibility(View.VISIBLE);
        }
    }

    private class Barshow extends AsyncTask<String,Void,String>
    {
        List<String> a = new ArrayList<>();
        List<String> b = new ArrayList<>();
        List<String> c = new ArrayList<>();
        @Override
        protected String doInBackground(String... param) {
            try {
                URL url = new URL("http://192.168.177.1:8080/assignment2/webresources/wasd.report/getPeriodSteps/" + checkLogin.getId() + "/"
                +param[0]+"/"+param[1]);
                HttpURLConnection conn = null;
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("ACCEPT", "application/json");
                StringBuilder result = new StringBuilder();
                InputStream in = new BufferedInputStream(conn.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                JsonArray json = new JsonParser().parse(result.toString()).getAsJsonArray();
                for(JsonElement q:json) {
                    a.add(q.getAsJsonObject().get("totalCaloriesConsumed").toString());
                    b.add(q.getAsJsonObject().get("totalCaloriesBurned").toString());
                    String x = q.getAsJsonObject().get("reportPK").getAsJsonObject().get("date").toString();
                    c.add(x.substring(0,x.indexOf("T")));
                }
                return "this bar chart is created";
            }
            catch(Exception e)
            {
                return"error";
            }
        }

        @Override
        protected void onPostExecute(String data) {
            pieChart.clear();
            barChart.clear();
            pieChart.setVisibility(View.INVISIBLE);
            barChart.setVisibility(View.INVISIBLE);
            error .setText(data);
            final String[] name = new String[c.size()];
            float[] x = new float[c.size()];
            for (int i =0;i<c.size();i++)
            {
                name[i] = c.get(i);
                x[i] = (float)i;
            }
            List<BarEntry> yVals1 = new ArrayList<>();
            List<BarEntry> yVals2 = new ArrayList<>();
            for(int i = 0;i < name.length ; i++)
            {
                yVals1.add(new BarEntry(x[i],Float.valueOf(a.get(i))));
                yVals2.add(new BarEntry(x[i]+0.08f,Float.valueOf(b.get(i))));
            }
            IAxisValueFormatter formatter = new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    return name[(int)value];
                }
            };
            BarDataSet set2 = new BarDataSet(yVals2,"burned");
            set2.setColor(Color.RED);
            BarDataSet set1 = new BarDataSet(yVals1,"consumed");
            BarData barData = new BarData(set1,set2);
            barData.setValueTextSize(15f);
            barData.setBarWidth(0.4f);
            Description description = new Description();
            description.setText("");
            barChart.setData(barData);
            barChart.setDescription(description);
            XAxis xAxisFromChart = barChart.getXAxis();
            xAxisFromChart.setDrawAxisLine(true);
            xAxisFromChart.setValueFormatter(formatter);
            xAxisFromChart.setGranularity(1f);
            xAxisFromChart.setPosition(XAxis.XAxisPosition.BOTTOM);
            barChart.setVisibility(View.VISIBLE);
            barChart.groupBars(-0.5f,0.08f,0.06f);
            barChart.invalidate();
        }
        }
    }
