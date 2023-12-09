package com.example.esieaboard.admin;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.esieaboard.DataBaseHelper;
import com.example.esieaboard.R;
import com.example.esieaboard.models.EventModel;

public class EditEventActivity extends AppCompatActivity {

    EditText inputName, inputDate, inputLocation, inputCapacity, inputDescription;
    Button buttonConfirm, buttonCancel;
    EventModel event;
    int id, capacity;
    String club_id, name, date, location, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);

        inputName = findViewById(R.id.name_input);
        inputDate = findViewById(R.id.date_input);
        inputLocation = findViewById(R.id.location_input);
        inputCapacity = findViewById(R.id.capacity_input);
        inputDescription = findViewById(R.id.description_input);

        buttonCancel = findViewById(R.id.button_cancel);
        buttonConfirm = findViewById(R.id.button_confirm);

        getSetIntentData();

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBaseHelper dataBase = new DataBaseHelper(EditEventActivity.this);
                event.setName(inputName.getText().toString().trim());
                event.setDate(inputDate.getText().toString().trim());
                event.setLocation(inputLocation.getText().toString().trim());
                event.setDescription(inputDescription.getText().toString().trim());
                event.setCapacity(Integer.parseInt(inputCapacity.getText().toString().trim()));
                dataBase.updateEvent(event);

                Intent resultIntent = new Intent();
                resultIntent.putExtra("event", event);
                setResult(RESULT_OK, resultIntent);

                finish();
            }
        });
    }

    void getSetIntentData() {
        if(getIntent().hasExtra("event")) {
            event = (EventModel) getIntent().getSerializableExtra("event");

            inputName.setText(event.getName());
            inputDate.setText(event.getDate());
            inputLocation.setText(event.getLocation());
            inputDescription.setText(event.getDescription());
            inputCapacity.setText(String.valueOf(event.getCapacity()));
        }
    }
}