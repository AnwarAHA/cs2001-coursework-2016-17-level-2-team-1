package com.example.rishikapadia.connectid;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.R.attr.data;
import static android.R.id.edit;

/**
 * Created by Rishi Kapadia on 08/03/2017.
 */

public class RegisterPage extends AppCompatActivity {

    private EditText Name,Email,Password;
    private Button SignUp;
    private FirebaseAuth Auth;
    private ProgressDialog progressDialog;
    private DatabaseReference databaseReference;
    private Firebase firebase;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        Auth = FirebaseAuth.getInstance();
        firebase = new Firebase("https://connectid-361b2.firebaseio.com/");

        progressDialog = new ProgressDialog(this);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");

        Name = (EditText) findViewById(R.id.namefeild);
        Email = (EditText) findViewById(R.id.emailfeild);
        Password = (EditText) findViewById(R.id.passwordfeild);
        SignUp = (Button)findViewById(R.id.sign_up);

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starRegister();
            }
        });



    }

    private void starRegister(){
        final String name = Name.getText().toString().trim();
        String email = Email.getText().toString().trim();
        String password = Password.getText().toString().trim();

        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){

            progressDialog.setMessage("Signing Up...");
            progressDialog.show();

            Auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()){

                        String user_id = Auth.getCurrentUser().getUid();

                        DatabaseReference current_user = databaseReference.child(user_id);

                        current_user.child("Name").setValue(name);
                        current_user.child("Image").setValue("default");

                        progressDialog.dismiss();

                        Intent intent = new Intent(RegisterPage.this,QRScanner.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                    }

                }
            });

        }
    }
}
