package com.example.esieaboard.admin;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.esieaboard.DataBaseHelper;
import com.example.esieaboard.R;
import com.example.esieaboard.models.EventModel;

public class NewEventActivity extends AppCompatActivity {

    EditText inputName, inputDate, inputLocation, inputCapacity, inputDescription;
    Button buttonConfirm, buttonCancel;
    EventModel event;
    int club_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);

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
                DataBaseHelper dataBaseHelper = new DataBaseHelper(NewEventActivity.this);
                event = new EventModel(-1, club_id, inputName.getText().toString(),
                        inputDescription.getText().toString(), inputDate.getText().toString(),
                        inputLocation.getText().toString(), Integer.parseInt(inputCapacity.getText().toString()));
                dataBaseHelper.addEvent(event);
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    void getSetIntentData() {
        if(getIntent().hasExtra("club_id")) {
            club_id = getIntent().getIntExtra("club_id", -1);
        }
    }
}