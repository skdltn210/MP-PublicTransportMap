package com.example.mp_publictransportmap;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.Manifest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MapActivity extends AppCompatActivity implements MapView.POIItemEventListener {
    private BikeStationList bikeStationList;
    private Gson gson;
    private MapView mapView;
    private LocationManager locationManager;
    private LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);

        ImageView backImageView = findViewById(R.id.backImageView);

        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        gson = new Gson();
        bikeStationList = new BikeStationList();
        makeRequest();

        mapView = new MapView(this);

        RelativeLayout mapViewContainer = findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    double currentLatitude = location.getLatitude();
                    double currentLongitude = location.getLongitude();

                    mapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(currentLatitude, currentLongitude), 3, true);

                    locationManager.removeUpdates(this);
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                }

                @Override
                public void onProviderEnabled(String provider) {
                }

                @Override
                public void onProviderDisabled(String provider) {
                }
            };
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
        mapView.setPOIItemEventListener(this);
    }

    public void makeRequest() {
        String url1 = "http://openapi.seoul.go.kr:8088/776e414766736b643130367969417068/json/bikeList/1/1000/";
        String url2 = "http://openapi.seoul.go.kr:8088/776e414766736b643130367969417068/json/bikeList/1001/2000/";
        String url3 = "http://openapi.seoul.go.kr:8088/776e414766736b643130367969417068/json/bikeList/2001/3000/";

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest request1 = new StringRequest(Request.Method.GET, url1,
                this::processData,
                error -> {
                    Log.e("Error", "request error!");
                });

        StringRequest request2 = new StringRequest(Request.Method.GET, url2,
                this::processData,
                error -> {
                    Log.e("Error", "request error!");
                });

        StringRequest request3 = new StringRequest(Request.Method.GET, url3,
                this::processData,
                error -> {
                    Log.e("Error", "request error!");
                });
        requestQueue.add(request1);
        requestQueue.add(request2);
        requestQueue.add(request3);
    }
    private void processData(String response)   {
        try {
            JSONObject jsonResponse = new JSONObject(response);
            JSONObject rentBikeStatus = jsonResponse.getJSONObject("rentBikeStatus");
            JSONArray rowArray = rentBikeStatus.getJSONArray("row");
            for (int i = 0; i < rowArray.length(); i++) {
                Log.i("abc",String.valueOf(i));
                JSONObject stationObject = rowArray.getJSONObject(i);
                BikeStation bikeStation = gson.fromJson(stationObject.toString(), BikeStation.class);
                bikeStationList.bikeStationList.add(bikeStation);

                double latitude = Double.parseDouble(bikeStation.stationLatitude);
                double longitude = Double.parseDouble(bikeStation.stationLongitude);

                MapPOIItem marker = new MapPOIItem();
                marker.setItemName(bikeStation.stationName);

                marker.setTag(i);

                marker.setMapPoint(MapPoint.mapPointWithGeoCoord(latitude, longitude));

                marker.setUserObject(bikeStation);

                if (Integer.parseInt(bikeStation.parkingBikeTotCnt) <= 2) {
                    marker.setMarkerType(MapPOIItem.MarkerType.RedPin);
                } else {
                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                }

                mapView.addPOIItem(marker);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("Error", "JSON parsing error!");
        }
    }

    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {
        BikeStation bikeStation = (BikeStation) mapPOIItem.getUserObject();

        Intent intent = new Intent(MapActivity.this, BikeStationActivity.class);

        intent.putExtra("stationName", mapPOIItem.getItemName());
        intent.putExtra("rackCount", bikeStation.rackTotCnt);
        intent.putExtra("parkingBikeCount", bikeStation.parkingBikeTotCnt);
        intent.putExtra("shared",bikeStation.shared);

        startActivity(intent);
    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {

    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (locationManager != null && locationListener != null) {
            locationManager.removeUpdates(locationListener);
        }
    }
}
