package com.example.rishikapadia.connectid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;

import java.util.ArrayList;



/**
 * Created by Rishi Kapadia on 18/01/2017.
 */

public class ContactList extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private GestureDetectorCompat gestureObject;


        String [] names = {"Bob","Ben","Bill","Jo","Chris","Mark"};
        int [] cards = {R.drawable.card,R.drawable.card,R.drawable.card,R.drawable.card,R.drawable.card,R.drawable.card};

        Toolbar toolbar;
        RecyclerView recyclerView;
        ContactAdapter adapter;
        RecyclerView.LayoutManager layoutManager;
        ArrayList<Name> arrayList = new ArrayList<>();

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.contactlist);

            gestureObject = new GestureDetectorCompat(this, new LearnGesture());

            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            recyclerView = (RecyclerView) findViewById(R.id.list);
            layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(true);

            int count = 0;
            for(String Name:names) {
                arrayList.add(new Name(Name,cards[count]));
                count++;
            }
            adapter = new ContactAdapter(arrayList,this);
            recyclerView.setAdapter(adapter);



        }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.contactlist_menu,menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.backButton:
                Intent intent = new Intent(this, QRScanner.class);
                startActivity(intent);
                overridePendingTransition(R.animator.slide_in_left, R.animator.slide_out_right);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        newText = newText.toLowerCase();
        ArrayList<Name> newList = new ArrayList<>();
        for (Name name : arrayList){
            String name1= name.getName().toLowerCase();
            if(name1.contains(newText))
                newList.add(name);

        }

        adapter.setFilter(newList);
        return true;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gestureObject.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    class LearnGesture extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            if(e2.getX()>e1.getX()){

                Intent intent = new Intent(ContactList.this,QRScanner.class);
                finish();
                startActivity(intent);
                overridePendingTransition(R.animator.slide_in_left,R.animator.slide_out_right);

            }
            else if (e2.getX()<e1.getX()){

            }


            return true;
        }
    }


}

