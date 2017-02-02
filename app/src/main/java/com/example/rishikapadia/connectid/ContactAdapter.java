package com.example.rishikapadia.connectid;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by Rishi Kapadia on 18/01/2017.
 */

public class ContactAdapter extends ArrayAdapter <String> {

    public ContactAdapter(Context context, String[] names) {
        super(context,R.layout.contactadapter, names);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.contactadapter, parent,false);

        String singleName = getItem(position);
        TextView text = (TextView) customView.findViewById(R.id.textName);
        ImageView image = (ImageView) customView.findViewById(R.id.profileCard);

        text.setText(singleName);
        image.setImageResource(R.drawable.card);
        return customView;
    }
}
