package com.example.esieaboard;

import android.content.Intent;
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
                user.setFirstName(nameInput.getText().toString().trim());
                user.setEmailAddress(emailInput.getText().toString().trim());
                user.setPassword(passwordInput.getText().toString().trim());
                user.setDescription(descriptionInput.getText().toString().trim());
                dataBase.updateUser(user);

                Intent resultIntent = new Intent();
                resultIntent.putExtra("user", user);
                setResult(RESULT_OK, resultIntent);

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