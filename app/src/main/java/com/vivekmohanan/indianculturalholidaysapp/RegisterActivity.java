package com.vivekmohanan.indianculturalholidaysapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    TextView RegisterTxt;

    EditText RegisterNameTxt, RegisterEmailTxt, RegisterPassTxt, RegisterVerifyTxt;

    Button RegisterBtn;

    ProgressDialog progressdialog;

    FirebaseAuth authDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        authDb = FirebaseAuth.getInstance();
//        RegisterTxt = findViewById(R.id.RegisterTxt);

        RegisterEmailTxt = findViewById(R.id.RegisterEmailTxt);
        RegisterNameTxt = findViewById(R.id.RegisterNameTxt);
        RegisterPassTxt = findViewById(R.id.RegisterPassTxt);
        RegisterVerifyTxt = findViewById(R.id.RegisterVerifyTxt);

        RegisterBtn = findViewById(R.id.RegisterBtn);


        progressdialog = new ProgressDialog(this);

    }

    public void SignUpClicked(View view) {

        String Email = RegisterEmailTxt.getText().toString();
        String Name = RegisterNameTxt.getText().toString();
        String Pass = RegisterPassTxt.getText().toString();
        String Vpass = RegisterVerifyTxt.getText().toString();




        if (Name.isEmpty() || Name.length() < 3) {
            Toast.makeText(this, "Please Enter a Name.", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(Email) || !android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            Toast.makeText(this, "Please Enter an Email.", Toast.LENGTH_LONG).show();
            return;
        }
        //   RegisterEmailTxt.setError(null);
//        }

        //vaild = false;


//        else {
//            RegisterNameTxt.setError(null);
//
//        }

        if (TextUtils.isEmpty(Pass)|| Pass.length() < 4 || Pass.length() > 10) {
            Toast.makeText(this, "Enter a password", Toast.LENGTH_LONG).show();
            RegisterPassTxt.setError("between 4 and 10 alphanumeric characters");
            return;
            //RegisterPassTxt.setError(");
//            return;
//            vaild = false;

        }
//        else {
//            RegisterPassTxt.setError(null);
//        }

        if (TextUtils.isEmpty(Vpass)|| Vpass.length() < 4 || Vpass.length() > 10) {
            Toast.makeText(this, "Please enter confirm password.", Toast.LENGTH_LONG).show();
            return;


            //  RegisterVerifyTxt.setError("between 4 and 10 alphanumeric characters");
//            return;
//            vaild = false;
        }
//        else {
//            RegisterVerifyTxt.setError(null);
//        }

        if (!Pass.equals(Vpass)) {
            Toast.makeText(this, "The password do not match", Toast.LENGTH_LONG).show();
            return;

        }
        progressdialog.setMessage("Registering account..");
        progressdialog.show();

//            if ((Name.isEmpty() || Name.length() > 3) && (Pass.isEmpty() || Pass.length() < 4 || Pass.length() > 10)
//
//                    && (Vpass.isEmpty() || Vpass.length() < 4 || Vpass.length() > 10)
//                    && (Email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches())
//
//                    && (Pass.equals(Vpass))) {
//
//

//        } else{
//            return vaild;
//        }
        authDb.createUserWithEmailAndPassword(Email, Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(i);

                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "Registration is failed", Toast.LENGTH_SHORT).show();
                }
                progressdialog.dismiss();
            }
        });
//        return vaild;
    }


    public void LoginClick(View view) {

        Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(i);

        finish();
    }
}