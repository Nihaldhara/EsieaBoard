package com.example.esieaboard;

import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ImageButton userImageButton;
    Button newClubButton;
    RecyclerView clubsRecyclerView, eventsRecyclerView;
    DataBaseHelper dataBaseHelper;
    ArrayList<String> club_id, club_name, club_description;
    ArrayList<String> event_id, event_club_id, event_name, event_description, event_date, event_location, event_capacity;
    ClubCustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_board);

        clubsRecyclerView = findViewById(R.id.club_profiles);
        eventsRecyclerView = findViewById(R.id.events_list);
        userImageButton = findViewById(R.id.user_profile_button);
        newClubButton = findViewById(R.id.new_club_button);

        userImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UserProfileActivity.class);
                startActivity(intent);
            }
        });

        newClubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ManageClubsAdminActivity.class);
                startActivity(intent);
            }
        });

        dataBaseHelper = new DataBaseHelper(MainActivity.this);
        club_id = new ArrayList<>();
        club_name = new ArrayList<>();
        club_description = new ArrayList<>();

        displayClubs();

        customAdapter = new ClubCustomAdapter(MainActivity.this, club_id, club_name, club_description);
        clubsRecyclerView.setAdapter(customAdapter);
        clubsRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    void displayClubs() {
        Cursor cursor = dataBaseHelper.readClubData();
        if(cursor.getCount() > 0) {
            while(cursor.moveToNext()) {
                club_id.add(cursor.getString(0));
                club_name.add(cursor.getString(1));
                club_description.add(cursor.getString(2));
            }
        }
    }

    void displayEvents() {
        Cursor cursor = dataBaseHelper.readEventData();
        if(cursor.getCount() > 0) {
            while(cursor.moveToNext()) {
                event_id.add(cursor.getString(0));
                event_club_id.add(cursor.getString(1));
                event_name.add(cursor.getString(2));
                event_description.add(cursor.getString(3));
                event_date.add(cursor.getString(4));
                event_location.add(cursor.getString(5));
                event_capacity.add(cursor.getString(6));
            }
        }
    }
}