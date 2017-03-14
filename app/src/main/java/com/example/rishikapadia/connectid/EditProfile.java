package com.example.rishikapadia.connectid;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.vision.text.Text;
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

import junit.framework.Test;

import java.util.Map;

import static android.R.attr.data;
import static com.google.zxing.qrcode.decoder.ErrorCorrectionLevel.Q;

/**
 * Created by Rishi Kapadia on 18/01/2017.
 */

public class EditProfile extends AppCompatActivity {

    EditText userName,userAge,userCourse,userInterests,userSocieties;

    Button profilePicture;

    ImageView picPreview;

    private Uri imageUri = null;

    private static final int GALLERY_REQUEST = 1;

    private DatabaseReference mDatabase;

    private FirebaseAuth firebaseAuth;

    private FirebaseUser firebaseUser;

    private StorageReference storageReference;

    private DatabaseReference databaseReference;

    private ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editprofile);
        Firebase.setAndroidContext(this);



        firebaseAuth = FirebaseAuth.getInstance();

        firebaseUser = firebaseAuth.getCurrentUser();

        storageReference = FirebaseStorage.getInstance().getReference();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseUser.getUid());



        profilePicture = (Button) findViewById(R.id.profilePicture);

        progressDialog = new ProgressDialog(this);

        picPreview = (ImageView) findViewById(R.id.picPreview);

        profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryInent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryInent.setType("image/*");
                startActivityForResult(galleryInent,GALLERY_REQUEST);

            }
        });

        userName = (EditText) findViewById(R.id.userName);
        userAge = (EditText) findViewById(R.id.userAge);
        userCourse = (EditText) findViewById(R.id.userCourse);
        userInterests = (EditText) findViewById(R.id.userInterests);
        userSocieties = (EditText) findViewById(R.id.userSocieties);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Map <String, String> map = (Map)dataSnapshot.getValue();

                String savedName = map.get("Name");
                String savedAge = map.get("Age");
                String savedCourse = map.get("Course");
                String savedInterests = map.get("Interests");
                String savedSocieties = map.get("Societies");
                String savedProfilePic = map.get("Image");


                userName.setText(savedName);
                userAge.setText(savedAge);
                userCourse.setText(savedCourse);
                userInterests.setText(savedInterests);
                userSocieties.setText(savedSocieties);
                Uri profilePic = Uri.parse(savedProfilePic);
                picPreview.setImageURI(profilePic);
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

            picPreview.setImageURI(imageUri);
        }
    }

    private void saveProfile (){

        progressDialog.setMessage("Saving to Profile");
        progressDialog.show();

        final String name_val = userName.getText().toString().trim();
        final String age_val = userAge.getText().toString().trim();
        final String course_val = userCourse.getText().toString().trim();
        final String interest_val = userInterests.getText().toString().trim();
        final String societies_val = userSocieties.getText().toString().trim();

        if(!TextUtils.isEmpty(name_val) && !TextUtils.isEmpty(age_val) && !TextUtils.isEmpty(course_val) && !TextUtils.isEmpty(interest_val) && !TextUtils.isEmpty(societies_val) && imageUri != null){

            StorageReference filepath = storageReference.child("Profile_Images").child(imageUri.getLastPathSegment());
            Log.v("E_VALUE","uri: "+filepath);

            filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Uri downloadUrl = imageUri;

                    mDatabase.child("Name").setValue(name_val);
                    mDatabase.child("Age").setValue(age_val);
                    mDatabase.child("Course").setValue(course_val);
                    mDatabase.child("Interests").setValue(interest_val);
                    mDatabase.child("Societies").setValue(societies_val);
                    mDatabase.child("Image").setValue(downloadUrl.toString());
                    progressDialog.dismiss();
                    Toast.makeText(getBaseContext(),"Data Saved",Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(EditProfile.this,PersonalProfile.class);
                    startActivity(intent);
                }
            });
        }else{
                    Toast.makeText(getApplicationContext(), "One or more feilds are missing", Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
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



//                String inputName = userName.getText().toString();
//                Firebase refChild = firebase.child("Name");
//                refChild.setValue(inputName);



                overridePendingTransition(R.animator.slide_in_right,R.animator.slide_out_left);

                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }

    }



}





