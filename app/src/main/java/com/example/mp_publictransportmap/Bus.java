package com.example.mp_publictransportmap;

import java.util.Objects;

public class Bus {
    public Bus(String arrmsg1, String arrmsg2, String busType1, String busType2, String isLast1, String isLast2, String routeType, String sectNm, String rtNm, String term) {
        this.arrmsg1 = arrmsg1;
        this.arrmsg2 = arrmsg2;
        if(Objects.equals(busType1, "0")){
            this.busType1 = "일반";
        }
        else if(Objects.equals(busType1, "1")){
            this.busType1 = "저상";
        }
        else if(Objects.equals(busType1, "2")){
            this.busType1 = "굴절";
        }
        if(Objects.equals(busType2, "0")){
            this.busType2 = "일반";
        }
        else if(Objects.equals(busType2, "1")){
            this.busType2 = "저상";
        }
        else if(Objects.equals(busType2, "2")){
            this.busType2 = "굴절";
        }
        if(Objects.equals(isLast1, "1")){
            this.isLast1 = "O";
        }
        else{
            this.isLast1 = "X";
        }
        if(Objects.equals(isLast2, "1")){
            this.isLast2 = "O";
        }
        else{
            this.isLast2 = "X";
        }

        if(Objects.equals(routeType, "0")){
            this.routeType="공용";
        }
        else if(Objects.equals(routeType, "1")){
            this.routeType="공항";
        }
        else if(Objects.equals(routeType, "2")){
            this.routeType="마을";
        }
        else if(Objects.equals(routeType, "3")){
            this.routeType="간선";
        }
        else if(Objects.equals(routeType, "4")){
            this.routeType="지선";
        }
        else if(Objects.equals(routeType, "5")){
            this.routeType="순환";
        }
        else if(Objects.equals(routeType, "6")){
            this.routeType="광역";
        }
        else if(Objects.equals(routeType, "7")){
            this.routeType="인천";
        }
        else if(Objects.equals(routeType, "8")){
            this.routeType="경기";
        }
        else if(Objects.equals(routeType, "9")){
            this.routeType="폐지";
        }
        this.sectNm=sectNm;
        this.rtNm=rtNm;
        this.term=term;

    }
    String arrmsg1;
    String arrmsg2;
    String busType1;
    String busType2;
    String isLast1;
    String isLast2;
    String routeType;
    String sectNm;
    String rtNm;
    String term;
}
