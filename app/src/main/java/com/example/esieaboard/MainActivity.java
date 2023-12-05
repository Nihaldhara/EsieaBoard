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
import com.example.esieaboard.models.ClubModel;
import com.example.esieaboard.models.EventModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ImageButton userImageButton;
    Button newClubButton;
    RecyclerView clubsRecyclerView, eventsRecyclerView;
    DataBaseHelper dataBaseHelper;
    ArrayList<ClubModel> clubs;
    ArrayList<EventModel> events;
    MixedAdapter clubsAdapter, eventsAdapter;

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

        clubs = new ArrayList<>();
        events = new ArrayList<>();

        displayClubs();
        displayEvents();

        clubsAdapter = new MixedAdapter(MainActivity.this, new ArrayList<>(), clubs);
        clubsRecyclerView.setAdapter(clubsAdapter);
        clubsRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        eventsAdapter = new MixedAdapter(MainActivity.this, events, new ArrayList<>());
        eventsRecyclerView.setAdapter(eventsAdapter);
        eventsRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    void displayClubs() {
        Cursor cursor = dataBaseHelper.readClubData();
        if(cursor.getCount() > 0) {
            while(cursor.moveToNext()) {
                clubs.add(new ClubModel(cursor.getInt(0), cursor.getString(1), cursor.getString(2)));
            }
        }
    }

    void displayEvents() {
        Cursor cursor = dataBaseHelper.readEventData();
        if(cursor.getCount() > 0) {
            while(cursor.moveToNext()) {
                events.add(new EventModel(cursor.getInt(0), cursor.getInt(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4), cursor.getString(5),
                        cursor.getInt(6)));
            }
        }
    }
}