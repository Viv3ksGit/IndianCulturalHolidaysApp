package com.vivekmohanan.indianculturalholidaysapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.firebase.auth.FirebaseUser;
import com.google.android.gms.common.SignInButton;

public class LoginActivity extends AppCompatActivity {


    EditText LoginEmailTxt, LoginPassTxt;
    TextView LoginTxt;

    Button LoginSignBtn;

    ProgressDialog progressdialog;

    FirebaseAuth Db;

       // class variables
//    GoogleSignInClient googleSignInClient;
//    int RC_SIGN_IN;
//    private static final String TAG = "LoginActivity";
//    SignInButton LoginGBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Db = FirebaseAuth.getInstance();

        LoginEmailTxt = findViewById(R.id.LoginEmailTxt);
        LoginPassTxt = findViewById(R.id.LoginPassTxt);

//        LoginTxt = findViewById(R.id.LoginTxt);

        LoginSignBtn = findViewById(R.id.LoginSignBtn);

        progressdialog = new ProgressDialog(this);

//
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id))
//                .requestEmail()
//                .build();
//
//        googleSignInClient = GoogleSignIn.getClient(this, gso);
//
//        LoginGBtn = findViewById(R.id.LoginGBtn);
//
//
//        LoginGBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent signInIntent = googleSignInClient.getSignInIntent();
//                startActivityForResult(signInIntent, RC_SIGN_IN);
//            }
//        });

    }


//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
//        if (requestCode == RC_SIGN_IN) {
//            // The Task returned from this call is always completed, no need to attach
//            // a listener.
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            try {
//                // Google Sign In was successful, authenticate with Firebase
//                GoogleSignInAccount account = task.getResult(ApiException.class);
//                firebaseAuthWithGoogle(account);
//            } catch (ApiException e) {
//                // Google Sign In failed, update UI appropriately
//                Log.w(TAG, "Google sign in failed", e);
//                // ...
//            }
//            handleSignInResult(task);
//        }
//    }
//
//    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
//        try {
//            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
//
//            // Signed in successfully, show authenticated UI.
//            startActivity(new Intent(getApplicationContext(), MainActivity.class));
//        } catch (ApiException e) {
//            // The ApiException status code indicates the detailed failure reason.
//            // Please refer to the GoogleSignInStatusCodes class reference for more information.
//            Toast.makeText(LoginActivity.this, "signInResult:failed code=" + e.getStatusCode(), Toast.LENGTH_LONG).show();
//
//        }
//    }

//
//    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
//
//        Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
//
//        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
//        Db.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            progressdialog.setContentView(View.INVISIBLE);
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "signInWithCredential:success");
////                            FirebaseUser user = Db.getCurrentUser();
//                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                        } else {
//                            progressdialog.setContentView(View.INVISIBLE);
//                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "signInWithCredential:failure", task.getException());
//                            Toast.makeText(LoginActivity.this, "Authentication Failed.", Toast.LENGTH_LONG).show();
////                            updateUI(null);
//                        }
//
//
//                    }
//                });
//    }




    public void Createaccount(View view) {
        Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(i);

        finish();
    }

    public void LoginClicked(View view) {

        String Email = LoginEmailTxt.getText().toString();
        String Pass = LoginPassTxt.getText().toString();

        if (TextUtils.isEmpty(Email)|| !android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            //LoginEmailTxt.setError("enter a valid email address");
            Toast.makeText(this, "Please enter a email", Toast.LENGTH_LONG).show();
            return;
        }
//        else {
//           LoginEmailTxt.setError(null); }


        if (TextUtils.isEmpty(Pass)) {
            //LoginPassTxt.setError("Enter a vaild password"); }
            Toast.makeText(this, "Please enter a password", Toast.LENGTH_LONG).show();
            return;
        }
//        else
////        { LoginPassTxt.setError(null);}

        progressdialog.setMessage("logging in.");
        progressdialog.show();

        Db.signInWithEmailAndPassword(Email, Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressdialog.dismiss();
                if(task.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();


                }
                else
                {
                    Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                }

            }

        });
    }}
