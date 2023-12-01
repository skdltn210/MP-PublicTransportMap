package com.example.mp_publictransportmap;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class BikeStationActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bike_station_info);

        Intent intent = getIntent();
        String stationName = intent.getStringExtra("stationName");
        String rackCount = intent.getStringExtra("rackCount");
        String parkingBikeCount = intent.getStringExtra("parkingBikeCount");
        String shared = intent.getStringExtra("shared");

        TextView textViewStationName = findViewById(R.id.textViewStationName);
        textViewStationName.setText("대여소 이름: " + stationName);

        TextView textViewRackCount = findViewById(R.id.textViewRackCount);
        textViewRackCount.setText("거치대 개수: " + rackCount);

        TextView textViewParkingBikeCount = findViewById(R.id.textViewParkingBikeCount);
        int parkingCount = Integer.parseInt(parkingBikeCount);
        textViewParkingBikeCount.setText("주차된 자전거 수: " + parkingBikeCount);

        if (parkingCount <= 2) {
            textViewParkingBikeCount.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
        }


        TextView textViewShared = findViewById(R.id.textViewShared);
        textViewShared.setText("거치율: " + shared + "%");

        ImageView backImageView = findViewById(R.id.backImageView);
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}

