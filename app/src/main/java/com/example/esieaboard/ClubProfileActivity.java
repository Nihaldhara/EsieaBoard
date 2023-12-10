package com.example.esieaboard;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.esieaboard.admin.EditClubActivity;
import com.example.esieaboard.admin.NewEventActivity;
import com.example.esieaboard.models.ClubModel;
import com.example.esieaboard.models.EventModel;
import com.example.esieaboard.models.SubscriptionModel;
import com.example.esieaboard.models.UserModel;

import java.util.ArrayList;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class ClubProfileActivity extends AppCompatActivity implements MixedAdapter.EventClickListener {

    private static final int CREATE_EVENT_REQUEST_CODE = 5;
    private static final int EDIT_CLUB_REQUEST_CODE = 9;
    private static final int EVENT_PAGE_REQUEST_CODE = 12;

    ImageButton backButton;
    Button modifyButton, newButton, deleteButton, subscribeButton, unsubscribeButton;
    ImageView imageViewProfile;
    TextView clubName, clubEmailAddress, clubDescription;

    SubscriptionModel subscription;
    UserModel user;
    ClubModel club;
    DataBaseHelper dataBaseHelper;

    ArrayList<EventModel> events;
    MixedAdapter eventsAdapter;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_profile);

        backButton = findViewById(R.id.back_button);
        deleteButton = findViewById(R.id.delete_button);
        modifyButton = findViewById(R.id.modify_button);
        newButton = findViewById(R.id.button_new);
        subscribeButton = findViewById(R.id.subscribe_button);
        unsubscribeButton = findViewById(R.id.unsubscribe_button);
        imageViewProfile = findViewById(R.id.logo_image);
        clubName = findViewById(R.id.club_name);
        clubEmailAddress = findViewById(R.id.club_email_address);
        clubDescription = findViewById(R.id.club_description);
        recyclerView = findViewById(R.id.recycler_view);

        user = (UserModel) getIntent().getSerializableExtra("user");
        events = new ArrayList<>();

        getSetIntentData();

        dataBaseHelper = new DataBaseHelper(this);

        displayEvents();

        eventsAdapter = new MixedAdapter(ClubProfileActivity.this, new ArrayList<>(), new ArrayList<>(), events, new ArrayList<>(), user);
        eventsAdapter.setEventClickListener(this);
        recyclerView.setAdapter(eventsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ClubProfileActivity.this));

        if (subscribed()) {
            subscribeButton.setVisibility(GONE);
            unsubscribeButton.setVisibility(VISIBLE);
        }

        if(user.getRights() < 1) { newButton.setVisibility(GONE); }
        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClubProfileActivity.this, NewEventActivity.class);
                intent.putExtra("club_id", club.getId());
                startActivityForResult(intent, CREATE_EVENT_REQUEST_CODE);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK);
                finish();
            }
        });

        if(user.getRights() < 1) { modifyButton.setVisibility(GONE); }
        modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClubProfileActivity.this, EditClubActivity.class);
                intent.putExtra("club", club);
                startActivityForResult(intent, EDIT_CLUB_REQUEST_CODE);
            }
        });

        if(user.getRights() < 2) { deleteButton.setVisibility(GONE); }
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });

        subscribeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subscription = new SubscriptionModel(-1, user.getId(), club.getId(), 1);
                dataBaseHelper.addSubscription(subscription);
                subscribeButton.setVisibility(GONE);
                unsubscribeButton.setVisibility(VISIBLE);
            }
        });

        unsubscribeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = dataBaseHelper.readSubscriptionData();

                if(cursor != null) {
                    while(cursor.moveToNext()) {
                        if(cursor.getInt(1) == user.getId() && cursor.getInt(2) == club.getId()) {
                            subscription = dataBaseHelper.getSubscriptionByClubAndUserId(club.getId(), user.getId());
                        }
                    }
                }
                dataBaseHelper.deleteSubscriptionRow(String.valueOf(subscription.getId()));
                subscribeButton.setVisibility(VISIBLE);
                unsubscribeButton.setVisibility(GONE);
            }
        });

        updateClubUI(club);
    }

    void getSetIntentData() {
        if (getIntent().hasExtra("club")) {
            club = (ClubModel) getIntent().getSerializableExtra("club");

            clubName.setText(club.getName());
            clubEmailAddress.setText(club.getEmail());
            clubDescription.setText(club.getDescription());
        }
    }

    void updateClubUI(ClubModel club) {
        DataBaseHelper dataBase = new DataBaseHelper(ClubProfileActivity.this);

        club = dataBase.getClubById(club.getId());

        if (club != null) {
            clubName.setText(club.getName());
            clubEmailAddress.setText(club.getEmail());
            clubDescription.setText(club.getDescription());
        }
    }

    boolean subscribed() {
        Cursor cursor = dataBaseHelper.readSubscriptionData();

        if(cursor != null) {
            while(cursor.moveToNext()) {
                if(cursor.getInt(2) == club.getId() && cursor.getInt(1) == user.getId()) {
                    return true;
                }
            }
        }

        return false;
    }

    void displayEvents() {
        events.clear();
        Cursor cursor = dataBaseHelper.readEventData();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                if (cursor.getInt(1) == club.getId()) {
                    events.add(new EventModel(
                            cursor.getInt(0),
                            cursor.getInt(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getString(5),
                            cursor.getInt(6)
                    ));
                }
            }
            cursor.close();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CREATE_EVENT_REQUEST_CODE && resultCode == RESULT_OK) {
            displayEvents();
            eventsAdapter.notifyDataSetChanged();
        } else if (requestCode == EDIT_CLUB_REQUEST_CODE && resultCode == RESULT_OK) {
            ClubModel updatedClub = (ClubModel) data.getSerializableExtra("club");
            updateClubUI(updatedClub);
        }
    }

    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ClubProfileActivity.this);
        builder.setTitle("Delete " + club.getName() + " ?");
        builder.setMessage("Do you really want to delete " + club.getName() + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DataBaseHelper dataBase = new DataBaseHelper(ClubProfileActivity.this);
                dataBase.deleteClubRow(String.valueOf(club.getId()));
                setResult(RESULT_OK);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }

    @Override
    public void onEventClick(EventModel event) {
        Intent intent = new Intent(ClubProfileActivity.this, EventPageActivity.class);
        intent.putExtra("event", event);
        intent.putExtra("user", user);
        startActivityForResult(intent, EVENT_PAGE_REQUEST_CODE);
    }
}