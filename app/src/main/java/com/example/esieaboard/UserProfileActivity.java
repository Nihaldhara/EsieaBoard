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

public class UserProfileActivity extends AppCompatActivity {

    ImageButton backButton;
    Button logOutButton, modifyButton;
    ImageView imageViewProfile;
    TextView textView1, textView2, textView3;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        backButton = findViewById(R.id.back_button);
        logOutButton = findViewById(R.id.log_out_button);
        modifyButton = findViewById(R.id.modify_button);
        imageViewProfile = findViewById(R.id.logo_image);
        textView1 = findViewById(R.id.user_name);
        textView2 = findViewById(R.id.user_email_address);
        textView3 = findViewById(R.id.user_description);
        recyclerView = findViewById(R.id.recycler_view);

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
                startActivity(intent);
            }
        });
    }
}