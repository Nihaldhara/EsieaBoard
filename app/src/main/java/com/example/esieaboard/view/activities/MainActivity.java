package com.example.esieaboard.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.esieaboard.R;
import com.example.esieaboard.database.db.AppDatabase;

public class MainActivity extends AppCompatActivity {
    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = AppDatabase.getInstance(MainActivity.this);
    }
}