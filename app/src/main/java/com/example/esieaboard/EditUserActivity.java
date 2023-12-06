package com.example.esieaboard;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.esieaboard.models.UserModel;

public class EditUserActivity extends AppCompatActivity {

    Button cancelButton, confirmButton;
    EditText nameInput, emailInput, descriptionInput, passwordInput;
    UserModel user;
    String name, email, password, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        cancelButton = findViewById(R.id.button_cancel);
        confirmButton = findViewById(R.id.button_confirm);

        nameInput = findViewById(R.id.name_input);
        emailInput = findViewById(R.id.email_address_input);
        descriptionInput = findViewById(R.id.description_input);
        passwordInput = findViewById(R.id.password_input);

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
                password = passwordInput.getText().toString().trim();
                description = descriptionInput.getText().toString().trim();
                dataBase.updateUser(String.valueOf(user.getId()), name, email, password, description);
                finish();
            }
        });
    }

    void getSetIntentData() {
        if(getIntent().hasExtra("user")) {
            user = (UserModel) getIntent().getSerializableExtra("user");

            nameInput.setText(user.getFirstName());
            emailInput.setText(user.getEmailAddress());
            passwordInput.setText(user.getPassword());
            descriptionInput.setText(user.getDescription());
        }
    }
}