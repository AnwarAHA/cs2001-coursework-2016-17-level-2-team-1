package com.example.rishikapadia.connectid;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.Firebase;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.google.zxing.qrcode.decoder.ErrorCorrectionLevel.Q;

public class LogInPage extends AppCompatActivity {

    EditText emailfeild,passwordfeild;
    Button login,createAccount;

    private FirebaseAuth firebaseAuth;

    private DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_page);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");

        emailfeild = (EditText) findViewById(R.id.loginemailfeild);
        passwordfeild = (EditText) findViewById(R.id.loginpasswordfeild);
        login = (Button) findViewById(R.id.login);
        createAccount = (Button) findViewById(R.id.createAccount);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogIn();
            }
        });

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInPage.this,RegisterPage.class);
                startActivity(intent);
            }
        });
    }

    private void checkLogIn(){
        String email = emailfeild.getText().toString().trim();
        String password = passwordfeild.getText().toString().trim();

        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){

            firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        checkUserExists();
                    }
                }
            });



        }

    }

    private void checkUserExists(){

        final String user_id = firebaseAuth.getCurrentUser().getUid();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChild(user_id)){
                    Intent intent = new Intent(LogInPage.this,QRScanner.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }else{
                    //need to fully complete there account
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
