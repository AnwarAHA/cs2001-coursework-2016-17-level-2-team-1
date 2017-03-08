package com.example.rishikapadia.connectid;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

/**
 * Created by Rishi Kapadia on 18/01/2017.
 */

public class PersonalProfile extends AppCompatActivity {

    ListView listView;
    SQLiteDatabase sqLiteDatabase;
    DbHelper dbHelper;
    Cursor cursor;
    DataAdapter dataAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.personalprofile);
        getSupportActionBar().setTitle("My Profile");


        listView = (ListView) findViewById(R.id.listview);
        dataAdapter = new DataAdapter(getApplicationContext(), R.layout.personalprofilerow);
        listView.setAdapter(dataAdapter);
        dbHelper = new DbHelper(getApplicationContext());
        sqLiteDatabase = dbHelper.getReadableDatabase();
        cursor = dbHelper.getInformation(sqLiteDatabase);


        if (cursor.moveToFirst()) {

            do {
                String name, age, course,societies,interests;
                name = cursor.getString(0);
                age = cursor.getString(1);
                course = cursor.getString(2);
                societies = cursor.getString(3);
                interests = cursor.getString(4);

                DataProvider dataProvider = new DataProvider(name, age, course,societies,interests);
                dataAdapter.add(dataProvider);

            } while (cursor.moveToNext());

        }


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.personalprofilemenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.editButton:
                Intent intent = new Intent(this,EditProfile.class);
                startActivity(intent);
                overridePendingTransition(R.animator.slide_in_left,R.animator.slide_out_right);
                return true;

            case R.id.backButton:
                Intent intent2 = new Intent(this,QRScanner.class);
                startActivity(intent2);
                overridePendingTransition(R.animator.slide_in_right,R.animator.slide_out_left);
                return true;


            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

}


