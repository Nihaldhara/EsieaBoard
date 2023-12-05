package com.example.esieaboard;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class EventPageActivity extends AppCompatActivity {

    ImageButton backButton;
    Button editButton;
    TextView eventName, eventDate, eventLocation, eventDescription, eventCapacity;

    int id, capacity;
    String club_id, name, date, location, description;

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
        eventCapacity = findViewById(R.id.event_capacity);

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
                Intent intent = new Intent(EventPageActivity.this, EditEventActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("club_id", club_id);
                intent.putExtra("name", name);
                intent.putExtra("location", location);
                intent.putExtra("date", date);
                intent.putExtra("description", description);
                intent.putExtra("capacity", capacity);
                startActivity(intent);
            }
        });
    }

    void getSetIntentData() {
        if(getIntent().hasExtra("id") && getIntent().hasExtra("name")
                && getIntent().hasExtra("description") && getIntent().hasExtra("date")
                && getIntent().hasExtra("location") && getIntent().hasExtra("capacity")
                && getIntent().hasExtra("club_id")) {
            id = getIntent().getIntExtra("id", -1);
            club_id = getIntent().getStringExtra("club_id");
            name = getIntent().getStringExtra("name");
            location = getIntent().getStringExtra("location");
            date = getIntent().getStringExtra("date");
            description = getIntent().getStringExtra("description");
            capacity = getIntent().getIntExtra("capacity", -1);

            eventName.setText(name);
            eventLocation.setText(location);
            eventDate.setText(date);
            eventDescription.setText(description);
            eventCapacity.setText(String.valueOf(capacity));
        }
    }
}