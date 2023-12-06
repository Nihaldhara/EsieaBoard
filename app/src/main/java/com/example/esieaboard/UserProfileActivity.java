package com.example.esieaboard;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import com.example.esieaboard.models.UserModel;

public class UserProfileActivity extends AppCompatActivity {

    ImageButton backButton;
    Button logOutButton, modifyButton;
    ImageView imageViewProfile;
    TextView nameText, emailText, descriptionText;
    RecyclerView recyclerView;
    UserModel user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        backButton = findViewById(R.id.back_button);
        logOutButton = findViewById(R.id.log_out_button);
        modifyButton = findViewById(R.id.modify_button);
        imageViewProfile = findViewById(R.id.logo_image);
        nameText = findViewById(R.id.user_name);
        emailText = findViewById(R.id.user_email_address);
        descriptionText = findViewById(R.id.user_description);
        recyclerView = findViewById(R.id.recycler_view);

        user = (UserModel) getIntent().getSerializableExtra("user");

        String userName = user.getFirstName() + " " + user.getLastName();
        nameText.setText(userName);
        emailText.setText(user.getEmailAddress());
        descriptionText.setText(user.getDescription());

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserProfileActivity.this, EditUserActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });
    }
}