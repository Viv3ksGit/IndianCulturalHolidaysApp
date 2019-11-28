package com.vivekmohanan.indianculturalholidaysapp.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.vivekmohanan.indianculturalholidaysapp.R;
import com.vivekmohanan.indianculturalholidaysapp.managers.UserAuthenticationManager;

public class LoginActivity extends AppCompatActivity {


    EditText loginEmailEditText, loginPasswordEditText;

    Button loginButton;

    ProgressBar loadingProgress;
    TextView errorMsgTextView;

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() != null) {
                switch (intent.getAction()) {
                    case UserAuthenticationManager.BROADCAST_LOGIN_FAILED:

                        break;
                    case UserAuthenticationManager.BROADCAST_LOGIN_SUCCESSFUL:
                        showHomeScreen();

                        break;
                }
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginEmailEditText = findViewById(R.id.login_screen_user_name_edit_text);
        loginPasswordEditText = findViewById(R.id.login_screen_password_edit_text);

        loginButton = findViewById(R.id.login_screen_login_button);
        loadingProgress = findViewById(R.id.login_screen_loading_progress);
        errorMsgTextView = findViewById(R.id.login_screen_error_msg);

        setLoadingForAuthentication(false);

        showErrorMessage(false);


        IntentFilter intentFilter = new IntentFilter();

        intentFilter.addAction(UserAuthenticationManager.BROADCAST_LOGIN_FAILED);
        intentFilter.addAction(UserAuthenticationManager.BROADCAST_LOGIN_SUCCESSFUL);
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, intentFilter);

    }

    private void showHomeScreen(){
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }


    public void showRegisterUserScreen(View view) {
        Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(i);

        finish();
    }

    public void loginClicked(View view) {

        String Email = loginEmailEditText.getText().toString();
        String Pass = loginPasswordEditText.getText().toString();

        if (TextUtils.isEmpty(Email) || !android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {

            showErrorMessage(true);
            return;
        }

        if (TextUtils.isEmpty(Pass)) {
            showErrorMessage(true);


            return;
        }

        setLoadingForAuthentication(true);
        showErrorMessage(false);


        FirebaseAuth.getInstance().signInWithEmailAndPassword(Email, Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


                setLoadingForAuthentication(false);
                if(!task.isSuccessful()){

                    showErrorMessage(true);
                }else {
                    showHomeScreen();
                }


            }

        });
    }

    private void setLoadingForAuthentication(boolean isLoading){

        if(isLoading){
            loginButton.setVisibility(View.GONE);

            loginEmailEditText.setEnabled(false);
            loginPasswordEditText.setEnabled(false);

            loadingProgress.setVisibility(View.VISIBLE);
        }else {
            loginButton.setVisibility(View.VISIBLE);

            loginEmailEditText.setEnabled(true);
            loginPasswordEditText.setEnabled(true);

            loadingProgress.setVisibility(View.GONE);
        }
    }

    private void showErrorMessage(boolean show){
        if(show){
            errorMsgTextView.setVisibility(View.VISIBLE);
        }else {
            errorMsgTextView.setVisibility(View.INVISIBLE);
        }
    }
}
