package com.example.esieaboard;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class EditEventActivity extends AppCompatActivity {

    EditText inputName, inputDate, inputLocation, inputCapacity, inputDescription;
    Button buttonConfirm, buttonCancel;
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
                name = inputName.getText().toString().trim();
                date = inputDate.getText().toString().trim();
                location = inputLocation.getText().toString().trim();
                description = inputDescription.getText().toString().trim();
                capacity = Integer.parseInt(inputCapacity.getText().toString().trim());
                dataBase.updateEvent(String.valueOf(id), name, date, location, description, capacity);
                finish();
            }
        });
    }

    void getSetIntentData() {
        if(getIntent().hasExtra("id") && getIntent().hasExtra("name")
            && getIntent().hasExtra("date") && getIntent().hasExtra("location")
            && getIntent().hasExtra("description") && getIntent().hasExtra("capacity")
            && getIntent().hasExtra("club_id")) {
            id = getIntent().getIntExtra("id", -1);
            club_id = getIntent().getStringExtra("club_id");
            name = getIntent().getStringExtra("name");
            date = getIntent().getStringExtra("date");
            location = getIntent().getStringExtra("location");
            description = getIntent().getStringExtra("description");
            capacity = getIntent().getIntExtra("capacity", -1);

            inputName.setText(name);
            inputDate.setText(date);
            inputLocation.setText(location);
            inputDescription.setText(description);
            inputCapacity.setText(String.valueOf(capacity));
        }
    }
}