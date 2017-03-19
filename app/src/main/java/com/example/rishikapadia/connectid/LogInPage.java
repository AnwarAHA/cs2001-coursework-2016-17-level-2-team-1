package com.example.rishikapadia.connectid;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.fitness.data.Goal;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.vision.MultiProcessor;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;

import static android.R.attr.data;

public class LogInPage extends AppCompatActivity {

    EditText emailfeild,passwordfeild;
    Button login,createAccount;

    private FirebaseAuth firebaseAuth;

    private DatabaseReference databaseReference;

    private LoginButton loginButton;
    private CallbackManager callbackManager;

    private static final String TAG = "FacebookLogin";

    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Facebook
        FacebookSdk.sdkInitialize(this.getApplicationContext());

        setContentView(R.layout.activity_log_in_page);

        //Facebook
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton)findViewById(R.id.login_button);

        loginButton.setReadPermissions(Arrays.asList("email","public_profile"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG,"facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());

            }

            @Override
            public void onCancel() {
                Log.d(TAG,"facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG,"facebook:onError",error);
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();

        //START auth state listener
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };





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


    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            firebaseAuth.removeAuthStateListener(mAuthListener);
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        //Pass activity result back to Facebook
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }


    //START auth with facebook
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());
                        String user_id = firebaseAuth.getCurrentUser().getUid();

                        DatabaseReference current_user = databaseReference.child(user_id);

                        String facebookUserID = "";
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        for(UserInfo profile : user.getProviderData()){
                            facebookUserID = profile.getUid();
                        }

                        String photoUrl = "https://graph.facebook.com/" + facebookUserID + "/picture?large";
                        String fbName = user.getDisplayName();

                        current_user.child("Name").setValue(fbName);
                        current_user.child("Image").setValue(photoUrl);


                        Intent intent = new Intent(LogInPage.this,QRScanner.class);
                        startActivity(intent);
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(LogInPage.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
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
                    }else{
                        PopUp();

                    }
                }
            });

        }

    }

    private void PopUp (){

        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);

        dlgAlert.setMessage("Username or password not found, please re-enter or choose create an account");
        dlgAlert.setTitle("Incorrect username or password");
        dlgAlert.setPositiveButton("Try again", null);
        dlgAlert.setCancelable(true);

        dlgAlert.setPositiveButton("Try again",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        AlertDialog dialog = dlgAlert.create();
        dialog.show(); //show() MUST be called before dialog.getButton
        Button positiveButton1 = dialog.getButton(AlertDialog.BUTTON_POSITIVE);

        LinearLayout parent = (LinearLayout) positiveButton1.getParent();
        parent.setGravity(Gravity.CENTER_HORIZONTAL);
        View leftSpacer = parent.getChildAt(1);
        leftSpacer.setVisibility(View.GONE);
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
                    Toast.makeText(getApplicationContext(), "Welcome", Toast.LENGTH_LONG).show();

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
