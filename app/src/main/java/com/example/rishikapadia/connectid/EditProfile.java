package com.example.rishikapadia.connectid;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Rishi Kapadia on 18/01/2017.
 */

public class EditProfile extends AppCompatActivity {

    EditText userName,userAge,userCourse,userInterests,userSocieties;
    Context context = this;
    DbHelper dbHelper;
    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editprofile);

        userName = (EditText) findViewById(R.id.userName);
        userAge = (EditText) findViewById(R.id.userAge);
        userCourse = (EditText) findViewById(R.id.userCourse);
        userInterests = (EditText) findViewById(R.id.userInterests);
        userSocieties = (EditText) findViewById(R.id.userSocieties);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editprofilemenu,menu);
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
                Intent intent = new Intent(this, EditProfile.class);
                startActivity(intent);
                overridePendingTransition(R.animator.slide_in_left, R.animator.slide_out_right);
                return true;

            case R.id.backButton:
                Intent intent2 = new Intent(this, PersonalProfile.class);
                startActivity(intent2);
                overridePendingTransition(R.animator.slide_in_right, R.animator.slide_out_left);
                return true;

            case R.id.saveButton:
                Intent intent4 = new Intent(this,PersonalProfile.class);
                String name = userName.getText().toString();
                String age = userAge.getText().toString();
                String course = userCourse.getText().toString();
                String societies = userSocieties.getText().toString();
                String interests = userInterests.getText().toString();

                dbHelper = new DbHelper(context);
                sqLiteDatabase = dbHelper.getWritableDatabase();
                dbHelper.addInformation(name,age,course,societies,interests,sqLiteDatabase);
                Toast.makeText(getBaseContext(),"Data Saved",Toast.LENGTH_LONG).show();
                dbHelper.close();
                startActivity(intent4);
                overridePendingTransition(R.animator.slide_in_right,R.animator.slide_out_left);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

}





