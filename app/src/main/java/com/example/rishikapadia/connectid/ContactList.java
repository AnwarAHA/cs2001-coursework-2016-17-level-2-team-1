package com.example.rishikapadia.connectid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.FirebaseError;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import junit.framework.Test;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;


/**
 * Created by Rishi Kapadia on 18/01/2017.
 */

public class ContactList extends AppCompatActivity {



    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseContacts;
    private FirebaseAuth firebaseAuth;
    private Query contacts,query;
    private DatabaseReference queryRef,contactsRef;

    private FirebaseUser firebaseUser;

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.contactlist);
            this.setTitle("Contact list");

            firebaseAuth = FirebaseAuth.getInstance();

            firebaseUser = firebaseAuth.getCurrentUser();

            mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

            mDatabaseContacts = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseUser.getUid()).child("Contacts");

            queryRef = FirebaseDatabase.getInstance().getReference();

            contacts = queryRef.child("Users").child(firebaseUser.getUid()).child("Contacts").orderByChild("Uid");
            contacts.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()){
                        for (DataSnapshot Contacts : dataSnapshot.getChildren()){
                            Log.v("something","this is id"+ Contacts.getValue().toString());
                        }
                    }else{
                        Log.v("something","issue");
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            recyclerView = (RecyclerView) findViewById(R.id.list);
            layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(true);
        }

        protected void onStart(){
            super.onStart();

            FirebaseRecyclerAdapter<Boolean, MyViewHolder> adapter = new FirebaseRecyclerAdapter<Boolean, MyViewHolder>(Boolean.class, R.layout.contactadapter, MyViewHolder.class,contacts){
                protected void populateViewHolder(final MyViewHolder viewHolder, Boolean model, int position) {
                    String key = this.getRef(position).getKey();
                    mDatabase.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            final String name = dataSnapshot.child("Name").getValue(String.class);
                            final String image = dataSnapshot.child("Image").getValue(String.class);
                            ((TextView)viewHolder.itemView.findViewById(R.id.contactName)).setText(name);
                            viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                   Intent intent = new Intent(ContactList.this,ContactProfile.class);

                                    intent.putExtra("img_id",image);
                                    intent.putExtra("user_name",name);
                                    startActivity(intent);
                                }
                            });
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }

                    });
                }
            };

            recyclerView.setAdapter(adapter);
        }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        View mView;
        public MyViewHolder(View itemView)
        {
            super(itemView);
            mView = itemView;
        }
        public void setName(String Name){
            TextView contactName = (TextView)mView.findViewById(R.id.contactName);
            contactName.setText(Name);
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        getMenuInflater().inflate(R.menu.contactlist_menu,menu);
//        MenuItem menuItem = menu.findItem(R.id.action_search);
//        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
//        searchView.setOnQueryTextListener(this);
//        return true;
//    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//
//            case R.id.backButton:
//                Intent intent = new Intent(this, QRScanner.class);
//                startActivity(intent);
//                overridePendingTransition(R.animator.slide_in_left, R.animator.slide_out_right);
//                return true;
//
//            default:
//                // If we got here, the user's action was not recognized.
//                // Invoke the superclass to handle it.
//                return super.onOptionsItemSelected(item);
//
//        }
//    }
//
//
//
//    @Override
//    public boolean onQueryTextSubmit(String query) {
//        return false;
//    }
//
//    @Override
//    public boolean onQueryTextChange(String newText) {
//
//        newText = newText.toLowerCase();
//        ArrayList<Name> newList = new ArrayList<>();
//        for (Name name : arrayList){
//            String name1= name.getName().toLowerCase();
//            if(name1.contains(newText))
//                newList.add(name);
//
//        }
//
//        adapter.setFilter(newList);
//        return true;
//    }



//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        this.gestureObject.onTouchEvent(event);
//        return super.onTouchEvent(event);
//    }
//
//    class LearnGesture extends GestureDetector.SimpleOnGestureListener{
//        @Override
//        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//
//            if(e2.getX()>e1.getX()){
//
//                Intent intent = new Intent(ContactList.this,MainActivity.class);
//                finish();
//                startActivity(intent);
//                overridePendingTransition(R.animator.slide_in_left,R.animator.slide_out_right);
//
//            }
//            else if (e2.getX()<e1.getX()){
//
//            }
//
//
//            return true;
//        }
//    }


}

