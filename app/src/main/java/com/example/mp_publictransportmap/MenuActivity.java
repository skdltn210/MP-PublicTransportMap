package com.example.mp_publictransportmap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        ImageView backImageView = findViewById(R.id.backImageView);

        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");

        Toast.makeText(this, "환영합니다! " + username + "님", Toast.LENGTH_SHORT).show();

        Button buttonBus = findViewById(R.id.buttonBus);
        Button buttonSubway = findViewById(R.id.buttonSubway);
        Button buttonBike = findViewById(R.id.buttonBike);

        buttonBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, MapActivity3.class);
                startActivity(intent);
            }
        });

        buttonSubway.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, MapActivity2.class);
                startActivity(intent);
            }
        });

        buttonBike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });
    }
}
