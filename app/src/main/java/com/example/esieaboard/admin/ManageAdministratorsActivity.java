package com.example.esieaboard.admin;

import android.database.Cursor;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.esieaboard.DataBaseHelper;
import com.example.esieaboard.EsieaBoardActivity;
import com.example.esieaboard.MixedAdapter;
import com.example.esieaboard.R;
import com.example.esieaboard.models.ClubModel;
import com.example.esieaboard.models.UserModel;

import java.util.ArrayList;

public class ManageAdministratorsActivity extends AppCompatActivity {

    ImageButton backButton;
    RecyclerView usersRecyclerView;
    DataBaseHelper dataBaseHelper;
    ArrayList<UserModel> users;
    UserModel user;
    MixedAdapter usersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_administrators);

        backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK);
                finish();
            }
        });

        usersRecyclerView = findViewById(R.id.users_list);
        dataBaseHelper = new DataBaseHelper(ManageAdministratorsActivity.this);
        users = new ArrayList<>();
        user = (UserModel) getIntent().getSerializableExtra("user");
        displayUsers();

        usersAdapter = new MixedAdapter(ManageAdministratorsActivity.this, new ArrayList<>(), users, new ArrayList<>(), new ArrayList<>(), user);
        usersRecyclerView.setAdapter(usersAdapter);
        usersRecyclerView.setLayoutManager(new LinearLayoutManager(ManageAdministratorsActivity.this));
    }

    void displayUsers() {
        users.clear();
        Cursor cursor = dataBaseHelper.readUserData();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                users.add(new UserModel(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getInt(6)));
            }
        }
    }
}