package com.example.esieaboard;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class EventPageActivity extends AppCompatActivity {

    ImageButton backButton;
    Button editButton;
    TextView eventName, eventDate, eventLocation, eventDescription;

    int id;
    String name, date, location, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_page);

        backButton = findViewById(R.id.back_button);
        editButton = findViewById(R.id.edit_button);
        eventName = findViewById(R.id.event_name);
        eventDate = findViewById(R.id.event_date);
        eventLocation = findViewById(R.id.event_location);
        eventDescription = findViewById(R.id.event_description);

        getSetIntentData();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EventPageActivity.this, NewModifyEventActivity.class);
                startActivity(intent);
            }
        });
    }

    void getSetIntentData() {
        if(getIntent().hasExtra("id") && getIntent().hasExtra("name")
                && getIntent().hasExtra("description") && getIntent().hasExtra("date")
                && getIntent().hasExtra("location")) {
            id = getIntent().getIntExtra("id", -1);
            name = getIntent().getStringExtra("name");
            location = getIntent().getStringExtra("location");
            date = getIntent().getStringExtra("date");
            description = getIntent().getStringExtra("description");

            eventName.setText(name);
            eventLocation.setText(location);
            eventDate.setText(date);
            eventDescription.setText(description);
        }
    }
}