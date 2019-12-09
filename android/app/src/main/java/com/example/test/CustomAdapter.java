package com.example.test;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

class CustomAdapter implements ListAdapter {
    ArrayList<SubjectData> arrayList;
    Context context;
    public CustomAdapter(Context context, ArrayList<SubjectData> arrayList) {
        this.arrayList=arrayList;
        this.context=context;
    }
    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }
    @Override
    public boolean isEnabled(int position) {
        return true;
    }
    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
    }
    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
    }
    @Override
    public int getCount() {
        return arrayList.size();
    }
    @Override
    public Object getItem(int position) {
        return position;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public boolean hasStableIds() {
        return false;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final SubjectData subjectData = arrayList.get(position);
        if(convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.search_food, null);
            final TextView tittle = convertView.findViewById(R.id.searchId);
            convertView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    SharedPreferences foodlist = v.getContext().getSharedPreferences("foodlist",Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = foodlist.edit();
                    edit.putString("name",tittle.getText().toString());
                    String b = foodlist.getString(tittle.getText().toString(),"");
                    edit.commit();
                    Dialog dialog = new Dialog();
                    FragmentManager manager = ((AppCompatActivity)context).getFragmentManager();
                    dialog.show(manager,"Food Detail");
                }
            });
            ImageView imag = convertView.findViewById(R.id.picture_food);
            tittle.setText(subjectData.SubjectName);
            Picasso.with(context)
                    .load(subjectData.Image)
                    .into(imag);
        }
        return convertView;
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
    @Override
    public int getViewTypeCount() {
        return arrayList.size();
    }
    @Override
    public boolean isEmpty() {
        return false;
    }
}
