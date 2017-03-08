package com.example.rishikapadia.connectid;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;

/**
 * Created by Rishi Kapadia on 18/01/2017.
 */

public class EditProfile extends AppCompatActivity {

    EditText userName,userAge,userCourse,userInterests,userSocieties;
    private Button testbutton;
    private Firebase firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editprofile);
        Firebase.setAndroidContext(this);

        firebase = new Firebase("https://connectid-361b2.firebaseio.com/");

        userName = (EditText) findViewById(R.id.userName);
        userAge = (EditText) findViewById(R.id.userAge);
        userCourse = (EditText) findViewById(R.id.userCourse);
        userInterests = (EditText) findViewById(R.id.userInterests);
        userSocieties = (EditText) findViewById(R.id.userSocieties);
        testbutton = (Button) findViewById(R.id.testbutton);

        testbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });
















    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editprofilemenu,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.saveButton:

                String inputName = userName.getText().toString();
                Firebase refChild = firebase.child("Name");
                refChild.setValue(inputName);


                Toast.makeText(getBaseContext(),"Data Saved",Toast.LENGTH_LONG).show();
                overridePendingTransition(R.animator.slide_in_right,R.animator.slide_out_left);

                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }

    }

}





