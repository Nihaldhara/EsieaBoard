package com.example.esieaboard;

import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.esieaboard.admin.EditEventActivity;
import com.example.esieaboard.models.AttendanceModel;
import com.example.esieaboard.models.EventModel;
import com.example.esieaboard.models.UserModel;

import java.util.ArrayList;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class EventPageActivity extends AppCompatActivity {

    private static final int EDIT_EVENT_REQUEST_CODE = 1;

    ImageButton backButton;
    Button editButton, attendButton, cancelButton;
    TextView eventName, eventDate, eventLocation, eventDescription, eventCapacity, eventAttendees;
    AttendanceModel attendance;
    EventModel event;
    UserModel user;
    DataBaseHelper dataBaseHelper;

    ArrayList<AttendanceModel> attendances;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_page);

        backButton = findViewById(R.id.back_button);
        editButton = findViewById(R.id.edit_button);
        attendButton = findViewById(R.id.attend_button);
        cancelButton = findViewById(R.id.cancel_button);
        eventName = findViewById(R.id.event_name);
        eventDate = findViewById(R.id.event_date);
        eventLocation = findViewById(R.id.event_location);
        eventDescription = findViewById(R.id.event_description);
        eventCapacity = findViewById(R.id.event_capacity);
        eventAttendees = findViewById(R.id.event_attendees);

        user = (UserModel) getIntent().getSerializableExtra("user");
        attendances = new ArrayList<>();

        dataBaseHelper = new DataBaseHelper(EventPageActivity.this);

        getSetIntentData();

        eventAttendees.setText(String.valueOf(countAttendance()));
        if(attendee()) {
            attendButton.setVisibility(GONE);
            cancelButton.setVisibility(VISIBLE);
        }

        if(countAttendance() >= event.getCapacity()) {
            attendButton.setVisibility(GONE);
        }

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if(user.getRights() < 1) { editButton.setVisibility(GONE); }
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EventPageActivity.this, EditEventActivity.class);
                intent.putExtra("event", event);
                startActivityForResult(intent, EDIT_EVENT_REQUEST_CODE);

            }
        });

        attendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attendance = new AttendanceModel(-1, user.getId(), event.getId(), 1);
                dataBaseHelper.addAttendance(attendance);
                attendButton.setVisibility(GONE);
                cancelButton.setVisibility(VISIBLE);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = dataBaseHelper.readAttendanceData();

                if(cursor != null) {
                    while(cursor.moveToNext()) {
                        if(cursor.getInt(1) == user.getId()) {
                            attendance = dataBaseHelper.getAttendanceByEventId(event.getId());
                        }
                    }
                }
                dataBaseHelper.deleteAttendanceRow(String.valueOf(attendance.getId()));
                attendButton.setVisibility(VISIBLE);
                cancelButton.setVisibility(GONE);
            }
        });
    }

    int countAttendance() {
        attendances.clear();
        Cursor cursor = dataBaseHelper.readAttendanceData();
        int result = 0;

        if(cursor != null) {
            while (cursor.moveToNext()) {
                if(cursor.getInt(2) == event.getId()) {
                    result++;
                }
            }
        }
        return result;
    }

    boolean attendee() {
        Cursor cursor = dataBaseHelper.readAttendanceData();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                if(cursor.getInt(2) == event.getId() && cursor.getInt(1) == user.getId()) {
                    attendance = new AttendanceModel(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2), cursor.getInt(3));
                    return true;
                }
            }

        }

        return false;
    }

    void getSetIntentData() {
        if(getIntent().hasExtra("event")) {
            event = (EventModel) getIntent().getSerializableExtra("event");

            eventName.setText(event.getName());
            eventLocation.setText(event.getLocation());
            eventDate.setText(event.getDate());
            eventDescription.setText(event.getDescription());
            eventCapacity.setText(String.valueOf(event.getCapacity()));
        }
    }

    void updateEventUI(EventModel event) {
        DataBaseHelper dataBase = new DataBaseHelper(EventPageActivity.this);

        event = dataBase.getEventById(event.getId());

        if (event != null) {
            eventName.setText(event.getName());
            eventLocation.setText(event.getLocation());
            eventDate.setText(event.getDate());
            eventDescription.setText(event.getDescription());
            eventCapacity.setText(String.valueOf(event.getCapacity()));
        }
    }

    // In UserProfileActivity.java
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_EVENT_REQUEST_CODE && resultCode == RESULT_OK) {
            // Update the UI with the new data
            EventModel updatedEvent = (EventModel) data.getSerializableExtra("event");
            updateEventUI(updatedEvent);
        }
    }
}