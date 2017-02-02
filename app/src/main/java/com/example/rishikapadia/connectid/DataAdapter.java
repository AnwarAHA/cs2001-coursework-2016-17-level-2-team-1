package com.example.rishikapadia.connectid;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rishi Kapadia on 18/01/2017.
 */

public class DataAdapter extends ArrayAdapter {

    List list = new ArrayList();

    public DataAdapter(Context context, int resource) {
        super(context, resource);
    }


    static class LayoutHandler{
        TextView name,age,course,societies,interests;
    }



    @Override
    public void add(Object object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        LayoutHandler layoutHandler;

        if (row == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.personalprofilerow,parent,false);
            layoutHandler = new LayoutHandler();
            layoutHandler.name = (TextView) row.findViewById(R.id.textName);
            layoutHandler.age = (TextView) row.findViewById(R.id.textAge);
            layoutHandler.course = (TextView) row.findViewById(R.id.textCourse);
            layoutHandler.societies = (TextView) row.findViewById(R.id.textSocieties);
            layoutHandler.interests = (TextView) row.findViewById(R.id.textInterests);
            row.setTag(layoutHandler);

        }
        else{
            layoutHandler = (LayoutHandler) row.getTag();


        }
        DataProvider dataProvider = (DataProvider)this.getItem(position);
        layoutHandler.name.setText(dataProvider.getName());
        layoutHandler.age.setText(dataProvider.getAge());
        layoutHandler.course.setText(dataProvider.getCourse());
        layoutHandler.societies.setText(dataProvider.getSocieties());
        layoutHandler.interests.setText(dataProvider.getInterests());
        return row;
    }
}

