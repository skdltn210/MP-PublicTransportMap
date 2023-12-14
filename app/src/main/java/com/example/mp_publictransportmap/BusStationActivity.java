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

public class BusStationActivity extends AppCompatActivity {

    private List<Bus> busList;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus_station_info);

        busList = new ArrayList<>();

        Intent intent = getIntent();
        String stopName = intent.getStringExtra("stopName");
        String stopType = intent.getStringExtra("stopType");
        String stop_no = intent.getStringExtra("stop_no");

        TextView textViewStopName = findViewById(R.id.textViewStopName);
        textViewStopName.setText("정류장 이름 : " + stopName);

        TextView textViewStopType = findViewById(R.id.textViewStopType);
        textViewStopType.setText("정류장 타입 : " + stopType);

        makeRequest(stop_no);

        ImageView backImageView = findViewById(R.id.backImageView);
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void makeRequest(String stop_no) {
        String url = "http://ws.bus.go.kr/api/rest/stationinfo/getLowStationByUid?serviceKey=CLce6nrfsFXfHRs%2F88XzAmoWAyKMitpJByuirDon%2B0VZiPutnUhb1ynL%2BTtsrT6TqAVBh4gjXdFMcOxRFgDUsQ%3D%3D&arsId="+stop_no+"&resultType=json";
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
            JSONObject msgBody = jsonResponse.getJSONObject("msgBody");
            JSONArray itemList = msgBody.getJSONArray("itemList");

            for (int i = 0; i < itemList.length(); i++) {
                JSONObject busObject = itemList.getJSONObject(i);

                String arrmsg1 = busObject.getString("arrmsg1");
                String arrmsg2 = busObject.getString("arrmsg2");
                String busType1 = busObject.getString("busType1");
                String busType2 = busObject.getString("busType2");
                String isLast1 = busObject.getString("isLast1");
                String isLast2 = busObject.getString("isLast2");
                String routeType = busObject.getString("routeType");
                String sectNm = busObject.getString("sectNm");
                String rtNm = busObject.getString("rtNm");
                String term = busObject.getString("term");

                Bus bus = new Bus(arrmsg1, arrmsg2, busType1, busType2, isLast1, isLast2, routeType, sectNm, rtNm, term);

                busList.add(bus);
            }
            displayBusInfo();

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("Error", "JSON parsing error!");
        }
    }
    private void displayBusInfo() {
        int[] textViewIds = {
                R.id.textViewRtNm,
                R.id.textViewSectNm,
                R.id.textViewRtTyoe,
                R.id.textViewTerm,
                R.id.textViewArrMsg1,
                R.id.textViewBusType1,
                R.id.textViewIsLast1,
                R.id.textViewArrMsg2,
                R.id.textViewBusType2,
                R.id.textViewIsLast2,
        };

        for (int i = 0; i < busList.size(); i++) {
            Bus bus = busList.get(i);
            displayBusOnLayout(bus, R.id.busLinearLayout, textViewIds);
        }
    }
    private void displayBusOnLayout(Bus bus, int layoutId, int[] textViewIds) {
        LinearLayout layout = findViewById(layoutId);
        View view = getLayoutInflater().inflate(R.layout.bus, null);
        layout.addView(view);

        for (int i = 0; i < textViewIds.length; i++) {
            TextView textView = view.findViewById(textViewIds[i]);
            switch (i) {
                case 0:
                    textView.setText("노선명 : " + bus.rtNm);
                    break;
                case 1:
                    textView.setText("구간명 : " + bus.sectNm);
                    break;
                case 2:
                    textView.setText("노선 유형 : " + bus.routeType);
                    break;
                case 3:
                    textView.setText("배차간격 : " + bus.term + "분");
                    break;
                case 4:
                    textView.setText("도착예정정보 : " + bus.arrmsg1);
                    break;
                case 5:
                    textView.setText("버스 타입 : " + bus.busType1);
                    break;
                case 6:
                    textView.setText("막차여부 : " + bus.isLast1);
                    break;
                case 7:
                    textView.setText("도착예정정보 : " + bus.arrmsg2);
                    break;
                case 8:
                    textView.setText("버스 타입 : " + bus.busType2);
                    break;
                case 9:
                    textView.setText("막차여부 : " + bus.isLast2);
                    break;
                default:
                    break;
            }
        }
    }
}
