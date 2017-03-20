package com.example.rishikapadia.connectid;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Map;

import static com.example.rishikapadia.connectid.R.id.picPreview;
import static com.example.rishikapadia.connectid.R.id.userProfilePicture;


public class PersonalProfile extends AppCompatActivity {

    private DatabaseReference mDatabase;

    private FirebaseAuth firebaseAuth;

    private FirebaseUser firebaseUser;

    public String twitterHandle,instaHandle,linkedinHandle;

    TextView textName,textAge,textCourse,textSocieties,textInterests;

    ImageButton profilePicture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.personalprofile);
        getSupportActionBar().setTitle("My Profile");

        textName = (TextView) findViewById(R.id.textName);
        textAge = (TextView) findViewById(R.id.textAge);
        textCourse = (TextView) findViewById(R.id.textCourse);
        textSocieties = (TextView) findViewById(R.id.textSocieties);
        textInterests = (TextView) findViewById(R.id.textInterests);
        profilePicture = (ImageButton) findViewById(R.id.userProfilePicture);

        firebaseAuth = FirebaseAuth.getInstance();

        firebaseUser = firebaseAuth.getCurrentUser();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseUser.getUid());

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, String> map = (Map)dataSnapshot.getValue();

                String savedName = map.get("Name");
                String savedAge = map.get("Age");
                String savedCourse = map.get("Course");
                String savedInterests = map.get("Interests");
                String savedSocieties = map.get("Societies");
                String savedProfilePic = map.get("Image");
                String savedTwitter = map.get("Twitter");
                String savedInstagram = map.get("Instagram");
                String savedLinkedin = map.get("Linkedin");

                textName.setText(savedName);
                textAge.setText(savedAge);
                textCourse.setText(savedCourse);
                textInterests.setText(savedInterests);
                textSocieties.setText(savedSocieties);
                twitterHandle = savedTwitter;
                instaHandle = savedInstagram;
                linkedinHandle = savedLinkedin;
                Uri profilePic = Uri.parse(savedProfilePic);
                Picasso.with(PersonalProfile.this).load(profilePic).fit().centerCrop().into(profilePicture);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    public void profilePicClick(View view){
        Intent intent = new Intent(PersonalProfile.this,ProfileImage.class);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.personalprofilemenu,menu);
        return true;
    }

    public void twitterclick(View view){
        if (TextUtils.isEmpty(twitterHandle)){
            Toast.makeText(getBaseContext(),"Twitter not linked",Toast.LENGTH_LONG).show();
        }else {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.twitter.com/" + twitterHandle));
            startActivity(browserIntent);
        }
    }

    public void instagramclick(View view){
        if (TextUtils.isEmpty(instaHandle)){
            Toast.makeText(getBaseContext(),"Instagram not linked",Toast.LENGTH_LONG).show();
        }else{
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.instagram.com/"+instaHandle));
            startActivity(browserIntent);
        }

    }

    public void linkedinclick(View view){
        if (TextUtils.isEmpty(linkedinHandle)){
            Toast.makeText(getBaseContext(),"Linkedin not linked",Toast.LENGTH_LONG).show();
        }else {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.linkedin.com/in/" + linkedinHandle));
            startActivity(browserIntent);
        }
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


