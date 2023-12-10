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
import com.example.esieaboard.admin.ManageAdministratorsActivity;
import com.example.esieaboard.models.UserModel;

import static android.view.View.GONE;

public class UserProfileActivity extends AppCompatActivity {

    private static final int EDIT_PROFILE_REQUEST_CODE = 1;

    ImageButton backButton;
    Button logOutButton, modifyButton, manageRightsButton;
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
        manageRightsButton = findViewById(R.id.manage_rights_button);
        imageViewProfile = findViewById(R.id.logo_image);
        nameText = findViewById(R.id.user_name);
        emailText = findViewById(R.id.user_email_address);
        descriptionText = findViewById(R.id.user_description);
        recyclerView = findViewById(R.id.recycler_view);

        user = (UserModel) getIntent().getSerializableExtra("user");
        updateUserUI(user);

        if(user.getRights() < 2) { manageRightsButton.setVisibility(GONE); }
        manageRightsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserProfileActivity.this, ManageAdministratorsActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });

        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK);
                finish();
            }
        });

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
                startActivityForResult(intent, EDIT_PROFILE_REQUEST_CODE);
            }
        });

    }

    void updateUserUI(UserModel user) {
        DataBaseHelper dataBase = new DataBaseHelper(UserProfileActivity.this);

        user = dataBase.getUserById(user.getId());

        if (user != null) {
            String userName = user.getFirstName() + " " + user.getLastName();
            nameText.setText(userName);
            emailText.setText(user.getEmailAddress());
            descriptionText.setText(user.getDescription());
        }
    }

    // In UserProfileActivity.java
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_PROFILE_REQUEST_CODE && resultCode == RESULT_OK) {
            // Update the UI with the new data
            UserModel updatedUser = (UserModel) data.getSerializableExtra("user");
            updateUserUI(updatedUser);
        }
    }
}