package com.example.esieaboard;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.esieaboard.models.ClubModel;

public class ManageClubsAdminActivity extends AppCompatActivity {

    ImageButton backImageButton;
    Button newButton;
    EditText nameInput, descriptionInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_clubs_admin);

        backImageButton = findViewById(R.id.back_button);
        newButton = findViewById(R.id.new_button);
        nameInput = findViewById(R.id.name_input);
        descriptionInput = findViewById(R.id.description_input);

        backImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBaseHelper dataBaseHelper = new DataBaseHelper(ManageClubsAdminActivity.this);
                ClubModel clubModel = new ClubModel(-1, nameInput.getText().toString().trim(),
                        descriptionInput.getText().toString().trim());
                boolean inserted = dataBaseHelper.addClub(clubModel);
                if(inserted)
                    Toast.makeText(ManageClubsAdminActivity.this, "Successfully inserted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(ManageClubsAdminActivity.this, "Failed", Toast.LENGTH_SHORT).show();

                finish();
            }
        });

    }
}