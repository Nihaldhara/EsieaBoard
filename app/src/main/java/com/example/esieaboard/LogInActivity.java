package com.example.esieaboard;

import android.content.Intent;
import android.os.UserManager;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.esieaboard.models.UserModel;

public class LogInActivity extends AppCompatActivity {

    EditText inputEmail, inputPassword;
    Button buttonConfirm, buttonSignIn;
    UserModel user;
    String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        inputEmail = findViewById(R.id.email_address_input);
        inputPassword = findViewById(R.id.password_input);

        buttonConfirm = findViewById(R.id.confirm_button);
        buttonSignIn = findViewById(R.id.sign_in_button);

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBaseHelper dataBaseHelper = new DataBaseHelper(LogInActivity.this);
                email = inputEmail.getText().toString().trim();
                password = inputPassword.getText().toString().trim();

                user = dataBaseHelper.getUserByEmailAndPassword(email, password);

                if (user != null) {
                    Intent intent = new Intent(LogInActivity.this, EsieaBoardActivity.class);
                    intent.putExtra("user", user); // Pass user model to the next activity
                    Toast.makeText(LogInActivity.this, "Successfully logged in!", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                } else {
                    inputPassword.setText("");
                    Toast.makeText(LogInActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                }

            }
        });

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogInActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });
    }
}