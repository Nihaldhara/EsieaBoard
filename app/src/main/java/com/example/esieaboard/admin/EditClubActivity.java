package com.example.esieaboard.admin;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.esieaboard.DataBaseHelper;
import com.example.esieaboard.R;
import com.example.esieaboard.models.ClubModel;

public class EditClubActivity extends AppCompatActivity {

    Button cancelButton, confirmButton;
    EditText nameInput, emailInput, descriptionInput;
    ClubModel club;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_club);

        cancelButton = findViewById(R.id.button_cancel);
        confirmButton = findViewById(R.id.button_confirm);

        nameInput = findViewById(R.id.name_input);
        emailInput = findViewById(R.id.email_address_input);
        descriptionInput = findViewById(R.id.description_input);

        getSetIntentData();

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBaseHelper dataBase = new DataBaseHelper(EditClubActivity.this);
                club.setName(nameInput.getText().toString().trim());
                club.setEmail(emailInput.getText().toString().trim());
                club.setDescription(descriptionInput.getText().toString().trim());
                dataBase.updateClub(club);
                finish();
            }
        });
    }

    void getSetIntentData() {
        if(getIntent().hasExtra("id") && getIntent().hasExtra("name")
                && getIntent().hasExtra("description")) {
            club = (ClubModel) getIntent().getSerializableExtra("club");

            nameInput.setText(club.getName());
            emailInput.setText(club.getEmail());
            descriptionInput.setText(club.getDescription());
        }
    }
}