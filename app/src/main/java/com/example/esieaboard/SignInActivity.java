package com.example.esieaboard;

import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.esieaboard.models.UserModel;

public class SignInActivity extends AppCompatActivity {

    EditText inputEmail, inputPassword;
    Button buttonConfirm, buttonLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        inputEmail = findViewById(R.id.email_address_input);
        inputPassword = findViewById(R.id.password_input);

        buttonConfirm = findViewById(R.id.confirm_button);
        buttonLogIn = findViewById(R.id.log_in_button);

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBaseHelper dataBase = new DataBaseHelper(SignInActivity.this);
                UserModel user = new UserModel(-1, "", "",
                        inputEmail.getText().toString(), inputPassword.getText().toString(), "");
                dataBase.addUser(user);
                finish();
            }
        });

        buttonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}