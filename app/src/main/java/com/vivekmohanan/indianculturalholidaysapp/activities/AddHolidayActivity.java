package com.vivekmohanan.indianculturalholidaysapp.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.FirebaseDatabase;
import com.vivekmohanan.indianculturalholidaysapp.R;
import com.vivekmohanan.indianculturalholidaysapp.models.EventDetails;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddHolidayActivity extends AppCompatActivity {

    private EditText editTextName;
    private TextView textViewDate;
    private EditText editTextPlace;
    private EditText editTextDescription;
    private EditText editTextFacts;
    private EditText editTextRituals;

    private Date selectedDate = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_holiday);


        textViewDate = findViewById(R.id.text_view_activity_add_holiday_date);
        editTextName = findViewById(R.id.edit_view_activity_add_holiday_name);
        editTextPlace = findViewById(R.id.edit_view_activity_add_holiday_place);
        editTextDescription = findViewById(R.id.edit_view_activity_add_holiday_description);
        editTextFacts = findViewById(R.id.edit_view_activity_add_holiday_facts);
        editTextRituals = findViewById(R.id.edit_view_activity_add_holiday_rituals);

        updateDate();

    }

    private void updateDate() {

        if (selectedDate == null) {
            textViewDate.setText(getString(R.string.please_select_a_date));
            return;
        }
        String myFormat = "dd MMM yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

        textViewDate.setText(sdf.format(selectedDate));
    }

    public void onBackButtonPressed(View view) {
        finish();
    }

    public void onAddHolidayAction(View view) {
        String name = editTextName.getText().toString();
        String url = editTextPlace.getText().toString();
        String desciption = editTextDescription.getText().toString();
        String facts = editTextFacts.getText().toString();
        String rituals = editTextRituals.getText().toString();


        if (name.isEmpty() ||url.isEmpty() || desciption.isEmpty() || selectedDate == null || facts.isEmpty() || rituals.isEmpty()) {

            Toast.makeText(this, "Please enter valid details", Toast.LENGTH_SHORT).show();
            return;
        }

        EventDetails eventDetails = new EventDetails();

        eventDetails.setDescription(desciption);
        eventDetails.setEventName(name);
        eventDetails.setImageUrl(url);
        eventDetails.setFacts(facts);
        eventDetails.setRituals(rituals);
        eventDetails.setEventDate(selectedDate.getTime());

        FirebaseDatabase.getInstance().getReference().child(getString(R.string.event_details)).push().setValue(eventDetails);

        Toast.makeText(this, "Holiday successfully added.", Toast.LENGTH_SHORT).show();

        finish();

    }


    public void onSelectDatePressed(View view) {

        Calendar myCalendar = Calendar.getInstance();

        new DatePickerDialog(AddHolidayActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {


                Calendar myCalendar = Calendar.getInstance();

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                selectedDate = myCalendar.getTime();
                updateDate();
            }
        }, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }
}
