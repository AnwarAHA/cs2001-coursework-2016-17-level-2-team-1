package com.example.rishikapadia.connectid;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.plus.model.people.Person;
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


public class CardView extends AppCompatActivity {

    private DatabaseReference mDatabase;

    private FirebaseAuth firebaseAuth;

    private FirebaseUser firebaseUser;

    public String twitterHandle,instaHandle,linkedinHandle;

    TextView textName,textAge,textCourse,textSocieties,textInterests;

    ImageButton profilePicture;
    Button close;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        textName = (TextView) findViewById(R.id.textName);
        textAge = (TextView) findViewById(R.id.textAge);
        textCourse = (TextView) findViewById(R.id.textCourse);
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
                String savedProfilePic = map.get("Image");
                String savedTwitter = map.get("Twitter");
                String savedInstagram = map.get("Instagram");
                String savedLinkedin = map.get("Linkedin");

                textName.setText(savedName);
                textAge.setText(savedAge);
                textCourse.setText(savedCourse);
                twitterHandle = savedTwitter;
                instaHandle = savedInstagram;
                linkedinHandle = savedLinkedin;
                Uri profilePic = Uri.parse(savedProfilePic);
                Picasso.with(CardView.this).load(profilePic).fit().centerCrop().into(profilePicture);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void profilePicClick(View view){
        Intent intent = new Intent(CardView.this,ProfileImage.class);
        startActivity(intent);
    }

    public void cardClick(View view){
        Intent intent = new Intent(CardView.this,CardViewBack.class);
        startActivity(intent);
    }

    public void close(View view){
        Intent intent = new Intent(CardView.this, PersonalProfile.class);
        startActivity(intent);
    }
}


