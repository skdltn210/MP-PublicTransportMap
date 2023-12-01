package com.example.mp_publictransportmap;

public class MetroStation {
    public MetroStation(String line, String name, Integer code, Double lat, Double lng) {
        this.line = line;
        this.name = name;
        this.code = code;
        this.lat = lat;
        this.lng = lng;
    }
    String line; // 호선
    String name; // 역이름
    Integer code; // 역 코드
    Double lat; // 위도
    Double lng; // 경도
}
