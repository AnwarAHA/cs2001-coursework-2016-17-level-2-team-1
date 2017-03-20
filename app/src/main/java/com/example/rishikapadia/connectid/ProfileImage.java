package com.example.rishikapadia.connectid;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Map;

public class ProfileImage extends AppCompatActivity {

    private DatabaseReference mDatabase;

    private FirebaseAuth firebaseAuth;

    private FirebaseUser firebaseUser;

    private StorageReference storageReference;

    Button profilePicButton;

    private static final int GALLERY_REQUEST = 1;

    private Uri imageUri = null;

    private ProgressDialog progressDialog;

    ImageView profilePicture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_image);
        getSupportActionBar().setTitle("My Profile");

        profilePicture = (ImageView) findViewById(R.id.userProfilePicture);

        profilePicButton = (Button) findViewById(R.id.profilePicButton);

        progressDialog = new ProgressDialog(this);

        profilePicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryInent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryInent.setType("image/*");
                startActivityForResult(galleryInent,GALLERY_REQUEST);

            }
        });

        firebaseAuth = FirebaseAuth.getInstance();

        firebaseUser = firebaseAuth.getCurrentUser();

        storageReference = FirebaseStorage.getInstance().getReference();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseUser.getUid());

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, String> map = (Map) dataSnapshot.getValue();

                String savedProfilePic = map.get("Image");
                Uri profilePic = Uri.parse(savedProfilePic);
                Picasso.with(ProfileImage.this).load(profilePic).fit().centerCrop().into(profilePicture);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK){

            imageUri = data.getData();
            profilePicture.setImageURI(imageUri);
        }
    }

    private void saveProfile (){

        progressDialog.setMessage("Saving to Profile");
        progressDialog.show();

        if(imageUri != null){

            StorageReference filepath = storageReference.child("Profile_Images").child(imageUri.getLastPathSegment());
            Log.v("E_VALUE","uri: "+filepath);


            filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    @SuppressWarnings("VisibleForTests")
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();

                    mDatabase.child("Image").setValue(downloadUrl.toString());

                    progressDialog.dismiss();
                    Toast.makeText(getBaseContext(),"Data Saved",Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(ProfileImage.this,PersonalProfile.class);
                    startActivity(intent);
                }
            });
        }
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

                saveProfile();
                overridePendingTransition(R.animator.slide_in_right,R.animator.slide_out_left);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }

    }

}
