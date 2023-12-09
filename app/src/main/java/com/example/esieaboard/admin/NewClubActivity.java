package com.example.esieaboard.admin;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.esieaboard.DataBaseHelper;
import com.example.esieaboard.R;
import com.example.esieaboard.models.ClubModel;
import com.example.esieaboard.models.EventModel;

public class NewClubActivity extends AppCompatActivity {

    EditText inputName, inputEmail, inputDescription;
    Button buttonCancel, buttonConfirm;
    ClubModel club;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_club);

        inputName = findViewById(R.id.name_input);
        inputEmail = findViewById(R.id.email_address_input);
        inputDescription = findViewById(R.id.description_input);

        buttonCancel = findViewById(R.id.button_cancel);
        buttonConfirm = findViewById(R.id.button_confirm);

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBaseHelper dataBaseHelper = new DataBaseHelper(NewClubActivity.this);
                club = new ClubModel(-1, inputName.getText().toString(), inputEmail.getText().toString().trim(),
                        inputDescription.getText().toString());
                dataBaseHelper.addClub(club);

                setResult(RESULT_OK);
                finish();
            }
        });
    }
}