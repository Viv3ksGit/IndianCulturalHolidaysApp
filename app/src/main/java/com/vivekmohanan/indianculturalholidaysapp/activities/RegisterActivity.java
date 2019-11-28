package com.vivekmohanan.indianculturalholidaysapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.vivekmohanan.indianculturalholidaysapp.R;

public class RegisterActivity extends AppCompatActivity {


    EditText registerUserName, registerEmail, registerPassword, registerPasswordConfirm;

    Button registerUserButton;
    TextView errorRegisteringTextView;
    ProgressBar registerProgressView;

    FirebaseAuth authDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        authDb = FirebaseAuth.getInstance();

        registerEmail = findViewById(R.id.register_screen_register_email);
        registerUserName = findViewById(R.id.register_screen_register_name);
        registerPassword = findViewById(R.id.register_screen_password);
        registerPasswordConfirm = findViewById(R.id.register_screen_confirm_password);

        registerUserButton = findViewById(R.id.register_screen_register_user_button);
        errorRegisteringTextView = findViewById(R.id.register_screen_error_msg);
        registerProgressView = findViewById(R.id.register_screen_register_loading);


        showErrorMessage(true);
        setLoadingForAuthentication(false);

    }

    public void SignUpClicked(View view) {

        String userEmail = registerEmail.getText().toString();
        String userName = registerUserName.getText().toString();
        String userPassword = registerPassword.getText().toString();
        String confirmPassword = registerPasswordConfirm.getText().toString();


        if (userName.isEmpty() || userName.length() < 3) {
            showErrorMessage(true);
            return;
        }

        if (TextUtils.isEmpty(userEmail) || !android.util.Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            showErrorMessage(true);
            return;
        }

        if (TextUtils.isEmpty(userPassword) || userPassword.length() < 6) {
            showErrorMessage(true);
            return;


        }


        if (TextUtils.isEmpty(confirmPassword) || confirmPassword.length() < 6) {
            showErrorMessage(true);
            return;
        }


        if (!userPassword.equals(confirmPassword)) {
            showErrorMessage(true);
            return;

        }

        showErrorMessage(false);
        setLoadingForAuthentication(true);

        authDb.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    showErrorMessage(false);

                    Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                      showHomeScreen();
                } else {

                    showErrorMessage(true);
                    setLoadingForAuthentication(false);

                }
            }
        });
    }


    private void setLoadingForAuthentication(boolean isLoading){

        if(isLoading){
            registerUserButton.setVisibility(View.GONE);

            registerUserName.setEnabled(false);
            registerPassword.setEnabled(false);
            registerPasswordConfirm.setEnabled(false);
            registerEmail.setEnabled(false);

            registerProgressView.setVisibility(View.VISIBLE);
        }else {
            registerUserButton.setVisibility(View.VISIBLE);

            registerUserName.setEnabled(true);
            registerPassword.setEnabled(true);
            registerPasswordConfirm.setEnabled(true);
            registerEmail.setEnabled(true);

            registerProgressView.setVisibility(View.GONE);
        }
    }

    private void showHomeScreen(){
        Intent i = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(i);

        finish();
    }

    public void LoginClick(View view) {

        Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(i);

        finish();
    }


    private void showErrorMessage(boolean show) {
        if (show) {
            errorRegisteringTextView.setVisibility(View.VISIBLE);
        } else {
            errorRegisteringTextView.setVisibility(View.INVISIBLE);
        }
    }
}