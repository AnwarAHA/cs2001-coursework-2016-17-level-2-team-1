package com.example.rishikapadia.connectid;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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


public class CardViewBack extends AppCompatActivity {

    private DatabaseReference mDatabase;

    private FirebaseAuth firebaseAuth;

    private FirebaseUser firebaseUser;

    public String twitterHandle,instaHandle,linkedinHandle;

    TextView textSocieties,textInterests;

    Button close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view_back);

        textSocieties = (TextView) findViewById(R.id.textSocieties);
        textInterests = (TextView) findViewById(R.id.textInterests);

        firebaseAuth = FirebaseAuth.getInstance();

        firebaseUser = firebaseAuth.getCurrentUser();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseUser.getUid());

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, String> map = (Map)dataSnapshot.getValue();

                String savedInterests = map.get("Interests");
                String savedSocieties = map.get("Societies");
                String savedTwitter = map.get("Twitter");
                String savedInstagram = map.get("Instagram");
                String savedLinkedin = map.get("Linkedin");

                textInterests.setText(savedInterests);
                textSocieties.setText(savedSocieties);
                twitterHandle = savedTwitter;
                instaHandle = savedInstagram;
                linkedinHandle = savedLinkedin;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void cardClickBack(View view){
        Intent intent = new Intent(CardViewBack.this,CardView.class);
        startActivity(intent);
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

    public void close(View view){
        Intent intent = new Intent(CardViewBack.this, PersonalProfile.class);
        startActivity(intent);
    }
}


