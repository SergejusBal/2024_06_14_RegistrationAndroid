package com.example.a2024_06_14_registrationandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {


    private Button logOutButton;
    private Button editProfile;
    private int userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        logOutButton = findViewById(R.id.welcome_log_uot_button);
        editProfile = findViewById(R.id.welcome_edit_profile);

        logOutButton.setOnClickListener(view -> logOutButtonClicked(view));
        editProfile.setOnClickListener(view -> editEditProfileButtonclicked(view));

        userId = getIntent().getIntExtra("UserID",0);

}
    public void logOutButtonClicked(View view){

        Intent intent = new Intent(this, LogInScreenActivity.class);
        startActivity(intent);
    }

    public void editEditProfileButtonclicked(View view){

        Intent intent = new Intent(this, EditDataActivity.class);
        intent.putExtra("UserID",userId);
        startActivity(intent);

    }





}
