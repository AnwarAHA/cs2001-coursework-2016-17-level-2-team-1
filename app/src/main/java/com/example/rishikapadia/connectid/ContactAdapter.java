package com.example.rishikapadia.connectid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by Rishi Kapadia on 18/01/2017.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyViewHolder> {

    ArrayList<Name> arrayList = new ArrayList<>();
    Context ctx;

    public ContactAdapter(ArrayList<Name>arrayList, Context ctx){
        this.arrayList = arrayList;
        this.ctx = ctx;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contactadapter,parent,false);

        return new MyViewHolder(view,ctx,arrayList);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {


        holder.profileCard.setImageResource(arrayList.get(position).getCard_id());
        holder.profileName.setText(arrayList.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView profileCard;
        TextView profileName;
        ArrayList<Name> names = new ArrayList<Name>();
        Context ctx;


        public MyViewHolder(View itemView, Context ctx, ArrayList<Name>names){
            super(itemView);
            this.names = names;
            this.ctx = ctx;

            itemView.setOnClickListener(this);

            profileCard = (ImageView) itemView.findViewById(R.id.profileCard);
            profileName = (TextView) itemView.findViewById(R.id.textName);

        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
            Name name = this.names.get(position);
            Intent intent = new Intent(ctx,ContactProfile.class);
            intent.putExtra("img_id",name.getCard_id());
            intent.putExtra("user_name",name.getName());
            this.ctx.startActivity(intent);
            ((Activity)ctx).overridePendingTransition(R.animator.slide_in_right,R.animator.slide_out_left);
        }


    }

    public void setFilter(ArrayList<Name>newList){
        arrayList = new ArrayList<>();
        arrayList.addAll(newList);
        notifyDataSetChanged();

    }




}

