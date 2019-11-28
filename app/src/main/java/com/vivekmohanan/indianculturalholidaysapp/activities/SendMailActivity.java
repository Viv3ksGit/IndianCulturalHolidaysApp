package com.vivekmohanan.indianculturalholidaysapp.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.vivekmohanan.indianculturalholidaysapp.R;

public class SendMailActivity extends AppCompatActivity {

    EditText SendEditToTxt, SendEditSubjectTxt,SendEditMessageTxt;
    TextView SendToText;
    Button SendMailButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_mail);


        SendEditToTxt = findViewById(R.id.SendEditToTxt);
        SendEditSubjectTxt = findViewById(R.id.SendEditSubjectTxt);
        SendEditMessageTxt = findViewById(R.id.SendEditMessageTxt);

        SendToText = findViewById(R.id.SendToText);
        SendMailButton = findViewById(R.id.SendMailButton);


        ActionBar actionBar = getSupportActionBar();

        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void Send(View view) {

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto: Viv3kdev@gmail.com"));
        intent.putExtra(Intent.EXTRA_SUBJECT,SendEditSubjectTxt.getText().toString());
        intent.putExtra(Intent.EXTRA_TEXT,SendEditMessageTxt.getText().toString());
        startActivity(intent);

    }
}
