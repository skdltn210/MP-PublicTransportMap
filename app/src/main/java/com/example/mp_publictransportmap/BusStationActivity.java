package com.example.mp_publictransportmap;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
public class BusStationActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus_station_info);

        Intent intent = getIntent();
        String stopName = intent.getStringExtra("stopName");
        String stopType = intent.getStringExtra("stopType");
        String nodeId = intent.getStringExtra("node_id");

        TextView textViewStopName = findViewById(R.id.textViewStopName);
        textViewStopName.setText("정류장 이름 : " + stopName);

        TextView textViewStopType = findViewById(R.id.textViewStopType);
        textViewStopType.setText("정류장 타입 : " + stopType);

        makeRequest(nodeId);

        ImageView backImageView = findViewById(R.id.backImageView);
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void makeRequest(String nodeId) {
        String url = "http://ws.bus.go.kr/api/rest/stationinfo/getLowStationByUid?serviceKey=CLce6nrfsFXfHRs%2F88XzAmoWAyKMitpJByuirDon%2B0VZiPutnUhb1ynL%2BTtsrT6TqAVBh4gjXdFMcOxRFgDUsQ%3D%3D&arsId="+nodeId;
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.GET, url,
                this::processData,
                error -> {
                    Log.e("Error", "request error!");
                });
        requestQueue.add(request);
    }

    private void processData(String response) {
        try {
            JSONObject jsonResponse = new JSONObject(response);
            JSONObject busObject = jsonResponse.getJSONObject("realtimeArrivalList");

            String arrmsg1 = busObject.getString("arrmsg1");
            String arrmsg2 = busObject.getString("arrmsg2");
            String busType1 = busObject.getString("busType1");
            String busType2 = busObject.getString("busType2");
            String isFullFlag1 = busObject.getString("isFullFlag1");
            String isFullFlag2 = busObject.getString("isFullFlag2");
            String isLast1 = busObject.getString("isLast1");
            String isLast2 = busObject.getString("isLast2");
            String rerideNum1 = busObject.getString("rerideNum1");
            String rerideNum2 = busObject.getString("rerideNum2");
            String routeType = busObject.getString("routeType");
            String sectNm = busObject.getString("sectNm");
            String rtNm = busObject.getString("rtNm");

            BusStop busStop = new BusStop(arrmsg1, arrmsg2, busType1, busType2, isFullFlag1, isFullFlag2, isLast1, isLast2, rerideNum1, rerideNum2, routeType, sectNm,rtNm);

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("Error", "JSON parsing error!");
        }
    }
}
