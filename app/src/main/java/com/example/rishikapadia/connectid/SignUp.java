package com.example.rishikapadia.connectid;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class SignUp extends AppCompatActivity {

    private EditText emailfeild,passwordfeild;
    private Button sign_up;

    private FirebaseAuth Auth;

    private FirebaseAuth.AuthStateListener AuthListener;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        Auth = FirebaseAuth.getInstance();


        emailfeild = (EditText)findViewById(R.id.emailfeild);
        passwordfeild = (EditText) findViewById(R.id.passwordfeild);
        sign_up = (Button) findViewById(R.id.sign_up);


        AuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            if(firebaseAuth.getCurrentUser()!=null){
                startActivity( new Intent(SignUp.this,QRScanner.class));
            }
            }
        };




        sign_up.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startSignIn();
            }
        });


        }

        protected void onStart(){
            super.onStart();

            Auth.addAuthStateListener(AuthListener);
        }

        private void startSignIn() {
            String email = emailfeild.getText().toString();
            String password = passwordfeild.getText().toString();

            if (TextUtils.isEmpty(email)|| TextUtils.isEmpty(password)) {

                Toast.makeText(SignUp.this, "A feild is empty", Toast.LENGTH_LONG).show();

            }else{
                Auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {

                        }
                    }
                });
            }




        }



    }



