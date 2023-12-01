package com.example.mp_publictransportmap;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MetroStationActivity extends AppCompatActivity {
    private List<Metro> upList;
    private List<Metro> downList;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.metro_station_info);

        upList = new ArrayList<>();
        downList = new ArrayList<>();

        Intent intent = getIntent();
        String stationName = intent.getStringExtra("stationName");
        String line = intent.getStringExtra("line");

        TextView textViewStationName = findViewById(R.id.textViewStationName);
        textViewStationName.setText("지하철 역 이름 : " + stationName +"역");

        TextView textViewLine = findViewById(R.id.textViewLine);
        textViewLine.setText("호선 : " + line);

        makeRequest(stationName);

        ImageView backImageView = findViewById(R.id.backImageView);
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void makeRequest(String stationName) {
        String url = "http://swopenAPI.seoul.go.kr/api/subway/6a664e5542736b64353141584a4f58/json/realtimeStationArrival/0/30/"+stationName;

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
            JSONArray realtimeArrivalList = jsonResponse.getJSONArray("realtimeArrivalList");

            for (int i = 0; i < realtimeArrivalList.length(); i++) {
                JSONObject trainObject = realtimeArrivalList.getJSONObject(i);

                String updnLine = trainObject.getString("updnLine");
                String trainLineNm = trainObject.getString("trainLineNm");
                String btrainSttus = trainObject.getString("btrainSttus");
                String arvlMsg2 = trainObject.getString("arvlMsg2");

                Metro metro = new Metro(updnLine, trainLineNm, btrainSttus, arvlMsg2);

                if ("상행".equals(updnLine)) {
                    upList.add(metro);
                } else {
                    downList.add(metro);
                }
            }
            displayMetroInfo();

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("Error", "JSON parsing error!");
        }
    }
    private void displayMetroInfo() {
        int[] textViewIds = {
                R.id.textViewTrainLineNm,
                R.id.textViewBtrainSttus,
                R.id.textViewArvlMsg2,
        };

        for (int i = 0; i < upList.size() && i<4; i++) {
            Metro metro = upList.get(i);
            displayMetroOnLayout(metro, R.id.upLinearLayout, textViewIds);
        }

        for (int i = 0; i < downList.size() && i<4; i++) {
            Metro metro = downList.get(i);
            displayMetroOnLayout(metro, R.id.downLinearLayout, textViewIds);
        }
    }
    private void displayMetroOnLayout(Metro metro, int layoutId, int[] textViewIds) {
        LinearLayout layout = findViewById(layoutId);
        View view = getLayoutInflater().inflate(R.layout.metro, null);
        layout.addView(view);

        for (int i = 0; i < textViewIds.length; i++) {
            TextView textView = view.findViewById(textViewIds[i]);
            switch (i) {
                case 0:
                    textView.setText("도착지 방면 : " + metro.trainLineNm);
                    break;
                case 1:
                    textView.setText("종류 : " + metro.btrainSttus);
                    break;
                case 2:
                    textView.setText("현재 위치 : " + metro.arvlMsg2);
                    break;
                default:
                    break;
            }
        }
    }
}
