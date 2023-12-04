package com.example.esieaboard;

import android.content.Intent;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;

public class ClubProfileActivity extends AppCompatActivity {

    ImageButton backButton;
    Button modifyButton, newButton;
    ImageView imageViewProfile;
    TextView clubName, clubEmailAddress, clubDescription;
    RecyclerView recyclerView;

    int id;
    String name, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_profile);

        backButton = findViewById(R.id.back_button);
        modifyButton = findViewById(R.id.modify_button);
        newButton = findViewById(R.id.button_new);
        imageViewProfile = findViewById(R.id.logo_image);
        clubName = findViewById(R.id.club_name);
        clubEmailAddress = findViewById(R.id.club_email_address);
        clubDescription = findViewById(R.id.club_description);
        recyclerView = findViewById(R.id.recycler_view);

        getSetIntentData();

        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClubProfileActivity.this, NewModifyEventActivity.class);
                intent.putExtra("club_id", id);
                startActivity(intent);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClubProfileActivity.this, EditClubActivity.class);
                startActivity(intent);
            }
        });
    }

    void getSetIntentData() {
        if(getIntent().hasExtra("id") && getIntent().hasExtra("name") && getIntent().hasExtra("description")) {
            id = getIntent().getIntExtra("id", -1);
            name = getIntent().getStringExtra("name");
            description = getIntent().getStringExtra("description");

            clubName.setText(name);
            clubDescription.setText(description);
        }
    }
}