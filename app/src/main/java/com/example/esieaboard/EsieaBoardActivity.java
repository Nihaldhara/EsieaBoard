package com.example.esieaboard;

import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.esieaboard.admin.NewClubActivity;
import com.example.esieaboard.models.ClubModel;
import com.example.esieaboard.models.EventModel;
import com.example.esieaboard.models.SubscriptionModel;
import com.example.esieaboard.models.UserModel;

import java.util.ArrayList;

public class EsieaBoardActivity extends AppCompatActivity {

    private static final int MANAGE_CLUBS_REQUEST_CODE = 4;
    private static final int CLUB_PROFILE_REQUEST_CODE = 6;
    private static final int EVENT_EDIT_REQUEST = 8;


    ImageButton userImageButton;
    Button newClubButton;
    RecyclerView clubsRecyclerView, eventsRecyclerView;
    DataBaseHelper dataBaseHelper;
    ArrayList<SubscriptionModel> subscriptions;
    ArrayList<ClubModel> clubs;
    ArrayList<EventModel> events;
    MixedAdapter clubsAdapter, eventsAdapter;
    UserModel user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_board);

        clubsRecyclerView = findViewById(R.id.club_profiles);
        eventsRecyclerView = findViewById(R.id.events_list);
        userImageButton = findViewById(R.id.user_profile_button);
        newClubButton = findViewById(R.id.new_club_button);

        user = (UserModel) getIntent().getSerializableExtra("user");

        userImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EsieaBoardActivity.this, UserProfileActivity.class);
                intent.putExtra("user", user);
                startActivityForResult(intent, CLUB_PROFILE_REQUEST_CODE);
            }
        });

        newClubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EsieaBoardActivity.this, NewClubActivity.class);
                startActivityForResult(intent, MANAGE_CLUBS_REQUEST_CODE);
            }
        });

        dataBaseHelper = new DataBaseHelper(EsieaBoardActivity.this);

        subscriptions = new ArrayList<>();
        clubs = new ArrayList<>();
        events = new ArrayList<>();

        displaySubscriptions();
        displayClubs();
        displayEvents();

        clubsAdapter = new MixedAdapter(EsieaBoardActivity.this, subscriptions, new ArrayList<>(), clubs, user);
        clubsRecyclerView.setAdapter(clubsAdapter);
        clubsRecyclerView.setLayoutManager(new LinearLayoutManager(EsieaBoardActivity.this));

        eventsAdapter = new MixedAdapter(EsieaBoardActivity.this, subscriptions, events, new ArrayList<>(), user);
        eventsRecyclerView.setAdapter(eventsAdapter);
        eventsRecyclerView.setLayoutManager(new LinearLayoutManager(EsieaBoardActivity.this));
    }

    void displaySubscriptions() {
        subscriptions.clear();
        Cursor cursor = dataBaseHelper.readSubscriptionData();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                if (cursor.getInt(1) == user.getId()) {
                    subscriptions.add(new SubscriptionModel(cursor.getInt(0), cursor.getInt(1),
                            cursor.getInt(2), cursor.getInt(3)));
                }
            }
            cursor.close(); // Close the cursor when done
        }
    }

    void displayClubs() {
        clubs.clear();
        Cursor cursor = dataBaseHelper.readClubData();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                clubs.add(new ClubModel(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)));
            }
        }
    }

    void displayEvents() {
        events.clear();
        Cursor cursorEvents = dataBaseHelper.readEventData();
        Cursor cursorSubscriptions = dataBaseHelper.readSubscriptionData();

        if (cursorEvents != null && cursorSubscriptions != null) {
            while (cursorEvents.moveToNext()) {
                while (cursorSubscriptions.moveToNext()) {
                    if (cursorEvents.getInt(1) == cursorSubscriptions.getInt(2)) {
                        events.add(new EventModel(
                                cursorEvents.getInt(0),
                                cursorEvents.getInt(1),
                                cursorEvents.getString(2),
                                cursorEvents.getString(3),
                                cursorEvents.getString(4),
                                cursorEvents.getString(5),
                                cursorEvents.getInt(6)
                        ));
                    }
                }
                cursorSubscriptions.moveToFirst();
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MANAGE_CLUBS_REQUEST_CODE && resultCode == RESULT_OK) {
            displayClubs();
            clubsAdapter.notifyDataSetChanged();
        } else if (requestCode == EVENT_EDIT_REQUEST && resultCode == RESULT_OK) {
            displayEvents();
            clubsAdapter.notifyDataSetChanged();
        }
    }
}