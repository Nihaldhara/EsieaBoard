package com.example.esieaboard;

import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
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
                if(dataBase.getUserByEmail(inputEmail.getText().toString().trim()) == null)
                {
                    UserModel user = new UserModel(-1, "User", "",
                            inputEmail.getText().toString().trim(), inputPassword.getText().toString().trim(), "", 0);
                    dataBase.addUser(user);
                    finish();
                }
                else {
                    Toast.makeText(SignInActivity.this, "This user already exists", Toast.LENGTH_SHORT).show();
                    inputEmail.setText("");
                    inputPassword.setText("");
                }
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