package com.example.mp_publictransportmap;

public class BusStop {
    public BusStop(String arrmsg1, String arrmsg2, String busType1, String busType2, String isFullFlag1, String isFullFlag2, String isLast1, String isLast2, String rerideNum1, String rerideNum2, String routeType, String sectNm,String rtNm) {
        this.arrmsg1 = arrmsg1;
        this.arrmsg2 = arrmsg2;
        if(busType1=="0"){
            this.busType1 = "일반";
        }
        else if(busType1=="1"){
            this.busType1 = "저상";
        }
        if(busType2=="0"){
            this.busType2 = "일반";
        }
        else if(busType2=="1"){
            this.busType2 = "저상";
        }
        this.isFullFlag1 = isFullFlag1;
        this.isFullFlag2 = isFullFlag2;
        this.isLast1 = isLast1;
        this.isLast2 = isLast2;
        this.rerideNum1 = rerideNum1;
        this.rerideNum2 = rerideNum2;

        if(routeType=="0"){
            this.routeType="공용";
        }
        else if(routeType=="1"){
            this.routeType="공항";
        }
        else if(routeType=="2"){
            this.routeType="마을";
        }
        else if(routeType=="3"){
            this.routeType="간선";
        }
        else if(routeType=="4"){
            this.routeType="지선";
        }
        else if(routeType=="5"){
            this.routeType="순환";
        }
        else if(routeType=="6"){
            this.routeType="광역";
        }
        else if(routeType=="7"){
            this.routeType="인천";
        }
        else if(routeType=="8"){
            this.routeType="경기";
        }
        else if(routeType=="9"){
            this.routeType="폐지";
        }
        this.sectNm=sectNm;
        this.rtNm=rtNm;

    }
    String arrmsg1;
    String arrmsg2;
    String busType1;
    String busType2;
    String isFullFlag1;
    String isFullFlag2;
    String isLast1;
    String isLast2;
    String rerideNum1;
    String rerideNum2;
    String routeType;
    String sectNm;
    String rtNm;
}
