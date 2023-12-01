package com.example.mp_publictransportmap;

public class BusStation {
    public BusStation(String stopNm, String yCode, String stopNo, String xCode, String stopType, String nodeId) {
        this.stop_nm = stopNm;
        this.ycode = yCode;
        this.stop_no = stopNo;
        this.xcode = xCode;
        this.stop_type = stopType;
        this.node_id = nodeId;

    }
    String stop_nm;
    String ycode;//위도
    String stop_no;
    String xcode;//경도
    String stop_type;
    String node_id;

}