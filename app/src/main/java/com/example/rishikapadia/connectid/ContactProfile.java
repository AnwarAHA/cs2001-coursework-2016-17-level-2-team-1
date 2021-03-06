package com.example.rishikapadia.connectid;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import static com.example.rishikapadia.connectid.R.id.picPreview;

/**
 * Created by Rishi Kapadia on 18/01/2017.
 */

public class ContactProfile extends AppCompatActivity implements OnMapReadyCallback {

    private GestureDetectorCompat gestureObject;
    private GoogleMap mMap;


    ImageView contactPicture;
    TextView contactName,contactYear,contactCourse,contactInterests,contactSocieties;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contactprofile);
        gestureObject = new GestureDetectorCompat(this, new LearnGesture());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        contactName = (TextView) findViewById(R.id.contactName);
        contactName.setText(""+getIntent().getStringExtra("contactName"));

        contactYear = (TextView) findViewById(R.id.contactYear);
        contactYear.setText(""+getIntent().getStringExtra("contactYear"));

        contactCourse = (TextView) findViewById(R.id.contactCourse);
        contactCourse.setText(""+getIntent().getStringExtra("contactCourse"));

        contactSocieties = (TextView) findViewById(R.id.contactSocieties);
        contactSocieties.setText(""+getIntent().getStringExtra("contactSocieties"));

        contactInterests = (TextView) findViewById(R.id.contactInterests);
        contactInterests.setText(""+getIntent().getStringExtra("contactInterests"));


        contactPicture = (ImageView) findViewById(R.id.contactPicture);
        Uri profilePic = Uri.parse(getIntent().getStringExtra("img"));
        Picasso.with(ContactProfile.this).load(profilePic).fit().centerCrop().into(contactPicture);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.MyToolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);



        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapse_toolbar);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.MyAppbar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle(getIntent().getStringExtra(""+getIntent().getStringExtra("contactName")));
                    isShow = true;
                } else if(isShow) {
                    collapsingToolbarLayout.setTitle(" ");
                    isShow = false;
                }
            }
        });

        Context context = this;
        collapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(context,R.color.colorPrimary));

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

                Intent intent = new Intent(ContactProfile.this,ContactList.class);
                finish();
                startActivity(intent);
                overridePendingTransition(R.animator.slide_in_left,R.animator.slide_out_right);

            }
            else if (e2.getX()<e1.getX()){

            }


            return true;
        }
    }

    public void twitterclick(View view){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.twitter.com/"+getIntent().getStringExtra("contactTwitter")));
        startActivity(browserIntent);
    }

    public void instagramclick(View view){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.instagram.com/"+getIntent().getStringExtra("contactInstagram")));
        startActivity(browserIntent);
    }

    public void linkedinclick(View view){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.linkedin.com/in/"+getIntent().getStringExtra("contactLinkedin")));
        startActivity(browserIntent);
    }

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.contactprofile_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.backButton:
                Intent intent = new Intent(this, ContactList.class);
                startActivity(intent);
                overridePendingTransition(R.animator.slide_in_left, R.animator.slide_out_right);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

}

