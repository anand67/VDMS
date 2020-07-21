package com.example.vdsuser.ModelClass;

public class MyHistoryListModelClass {

    String id;
    String organization;
    String vehicle;
    String date_time;
    String status;

    public MyHistoryListModelClass(String id, String organization, String vehicle, String date_time, String status) {
        this.id = id;
        this.organization = organization;
        this.vehicle = vehicle;
        this.date_time = date_time;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getOrganization() {
        return organization;
    }

    public String getVehicle() {
        return vehicle;
    }

    public String getDate_time() {
        return date_time;
    }

    public String getStatus() {
        return status;
    }
}
