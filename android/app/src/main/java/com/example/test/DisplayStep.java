package com.example.test;

import android.app.AlertDialog;
import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class DisplayStep extends Fragment{
    View vDisplayStep;
    StepsDatabase db;
    Button create;
    Button delete;
    ListView text;
    EditText editText;
    TextView message;
    EditText deleteText;
    TextView deleteMessage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        vDisplayStep = inflater.inflate(R.layout.fragment_steps, container, false);
        db = Room.databaseBuilder(vDisplayStep.getContext(),
                StepsDatabase.class, "CustomerDatabase")
                .fallbackToDestructiveMigration()
                .build();
        create = (Button)vDisplayStep.findViewById(R.id.submitStep);
        delete = (Button)vDisplayStep.findViewById(R.id.deleteStep);
        text = (ListView) vDisplayStep.findViewById(R.id.displaySteps);
        editText = (EditText)vDisplayStep.findViewById(R.id.setSteps);
        deleteText = (EditText)vDisplayStep.findViewById(R.id.deSM);
        message = (TextView)vDisplayStep.findViewById(R.id.stepsResult);
        deleteMessage = (TextView)vDisplayStep.findViewById(R.id.deleteStepsResult);
        readData read = new readData();
        read.execute();
        return vDisplayStep;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertDatabase insert = new InsertDatabase();
                insert.execute();
                readData read = new readData();
                read.execute();
            }
        });
        text.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String,Object> a = (HashMap<String, Object>) text.getItemAtPosition(position);
                final String step_id = a.get("id").toString();
                AlertDialog.Builder builder = new AlertDialog.Builder(vDisplayStep.getContext());
                final AlertDialog edit = builder.create();
                View dialogView = View.inflate(vDisplayStep.getContext(), R.layout.edit_step, null);
                edit.setView(dialogView);
                edit.show();
                final EditText change = (EditText) dialogView.findViewById(R.id.edit_step);
                Button submit_edit = (Button) dialogView.findViewById(R.id.editSteps);
                submit_edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UpdateDatabase update = new UpdateDatabase();
                        update.execute(step_id,change.getText().toString());
                        readData read = new readData();
                        read.execute();
                    }
                });
            }
        });
        delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DeleteDatabase delete = new DeleteDatabase();
                delete.execute();
                readData read = new readData();
                read.execute();
            }
        });

    }

    private class InsertDatabase extends AsyncTask<Void, Void, String>
    {
        @Override
        protected String doInBackground(Void... params) {
            if (!(editText.getText().toString().isEmpty())) {
            try
            {
                int detail = Integer.parseInt(editText.getText().toString());
                Date date = new Date();
                StepData step = new StepData(detail,checkLogin.getId(),date);
                String t = new Gson().toJson(step);
                long id = db.stepsDao().insert(step);
                return ("this step has been recorded");
            }
            catch (Exception e)
            {
                return ("please enter a right number");
            }
        }
        else
        {
            return("please enter a valid number");
        }
    }
    @Override
    protected void onPostExecute(String details) {
        message.setText(details);
    }
    }

    private class readData extends AsyncTask<Void,Void,List<StepData>>
    {
        @Override
        protected List<StepData> doInBackground(Void ... param)
        {
            try {
                List<StepData> step = db.stepsDao().findByUserId(checkLogin.getId());
                return step;
            }
            catch (Exception e)
            {
                List<StepData> step = new ArrayList<>();
                return step;
            }
        }
        @Override
        protected void onPostExecute(List<StepData> data)
        {
            List<HashMap<String,Object>> result = new ArrayList<HashMap<String,Object>>();
            for(StepData a: data)
            {
                HashMap<String,Object> input = new HashMap<String,Object>();
                input.put("id",a.getId());
                input.put("step",a.getStep());
                input.put("time",a.getTime());
                result.add(input);
            }
            SimpleAdapter adapter = new SimpleAdapter(vDisplayStep.getContext(),result,R.layout.list,
                    new String[]{"id","step","time"},new int[]{R.id.stepId,R.id.step,R.id.stepTime});
            text.setAdapter(adapter);
        }
    }

    private class DeleteDatabase extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            if(! (deleteText.getText().toString().isEmpty()))
            {
                try
                {
                    int id = Integer.parseInt(deleteText.getText().toString());
                    List<StepData> step = db.stepsDao().findByUserId(checkLogin.getId());
                    for(StepData a : step)
                    {
                        if(a.getId() == id)
                        {
                            db.stepsDao().delete(a);
                            return("this step has been deleted");
                        }
                    }
                    return("this id is not existed");
                }
                catch (Exception e)
                {
                    return("please enter a integer");
                }
            }
            else {
                return("please enter a valid number");
            }
        }
        protected void onPostExecute(String data) {
            deleteMessage.setText(data);
        }
    }
    private class UpdateDatabase extends AsyncTask<String, Void, String> {
        @Override protected String doInBackground(String... params) {
            StepData step = null;
            try {
                step = db.stepsDao().findById(Integer.parseInt(params[0]));
                step.setStep(Integer.parseInt(params[1]));
            }catch (Exception e)
            {
                return "please enter a valid data";
            }
            if (step!=null) {
                db.stepsDao().updateStep(step);
                return ("this step has been updated");
            }
            return "please enter the valid data";
        }
        @Override
        protected void onPostExecute(String details) {
            Toast.makeText(vDisplayStep.getContext(),details,Toast.LENGTH_LONG).show();
        }
    }
}
