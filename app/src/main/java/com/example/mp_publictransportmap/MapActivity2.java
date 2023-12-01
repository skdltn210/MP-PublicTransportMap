package com.example.mp_publictransportmap;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.Manifest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class MapActivity2 extends AppCompatActivity implements MapView.POIItemEventListener {
    private MapView mapView;
    private MetroStationList metroStationList;
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

        mapView = new MapView(this);
        metroStationList = new MetroStationList();

        JSONArray jsonArray = loadJSONArrayFromRawResource(R.raw.metro_station);
        if (jsonArray != null) {
            addMarkersFromJsonArray(jsonArray);
        }

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

    private JSONArray loadJSONArrayFromRawResource(int resourceId) {
        JSONArray jsonArray = null;
        try {
            InputStream is = getResources().openRawResource(resourceId);
            BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            br.close();
            String jsonString = sb.toString();
            jsonArray = new JSONArray(jsonString);
        } catch (IOException | JSONException ex) {
            ex.printStackTrace();
        }
        return jsonArray;
    }



    private void addMarkersFromJsonArray(JSONArray jsonArray) {
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject stationObject = jsonArray.getJSONObject(i);
                MetroStation metroStation = new MetroStation(
                        stationObject.getString("line"),
                        stationObject.getString("name"),
                        stationObject.getInt("code"),
                        stationObject.getDouble("lat"),
                        stationObject.getDouble("lng")
                );
                metroStationList.metroStationList.add(metroStation);

                double lat = metroStation.lat;
                double lng = metroStation.lng;
                MapPOIItem marker = new MapPOIItem();
                marker.setItemName(metroStation.name);
                marker.setTag(metroStation.code);
                marker.setMapPoint(MapPoint.mapPointWithGeoCoord(lat, lng));
                marker.setUserObject(metroStation);

                mapView.addPOIItem(marker);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {
        MetroStation metroStation = (MetroStation) mapPOIItem.getUserObject();

        Intent intent = new Intent(MapActivity2.this, MetroStationActivity.class);

        intent.putExtra("line", metroStation.line);
        intent.putExtra("stationName", metroStation.name);
        intent.putExtra("code", metroStation.code);

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