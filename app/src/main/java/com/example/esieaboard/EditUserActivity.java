package com.example.esieaboard;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class EditUserActivity extends AppCompatActivity {

    Button cancelButton, confirmButton;
    EditText nameInput, emailInput, descriptionInput;
    int id;
    String name, email, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

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
                DataBaseHelper dataBase = new DataBaseHelper(EditUserActivity.this);
                name = nameInput.getText().toString().trim();
                email = emailInput.getText().toString().trim();
                description = descriptionInput.getText().toString().trim();
                dataBase.updateUser(String.valueOf(id), name, email, description);
                finish();
            }
        });
    }

    void getSetIntentData() {
        if(getIntent().hasExtra("id") && getIntent().hasExtra("name")
            && getIntent().hasExtra("email") && getIntent().hasExtra("description")) {
            id = getIntent().getIntExtra("id", -1);
            name = getIntent().getStringExtra("name");
            email = getIntent().getStringExtra("email");
            description = getIntent().getStringExtra("description");

            nameInput.setText(name);
            emailInput.setText(email);
            descriptionInput.setText(description);
        }
    }
}