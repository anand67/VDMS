package com.example.vdsuser.ModelClass;

public class MyVehicleListModelClass {

    String id;
    String vehicle_name;
    String vehicle_number;
    String vehicle_color;

    public MyVehicleListModelClass(String id, String vehicle_name, String vehicle_number, String vehicle_color) {
        this.id = id;
        this.vehicle_name = vehicle_name;
        this.vehicle_number = vehicle_number;
        this.vehicle_color = vehicle_color;
    }

    public String getId() {
        return id;
    }

    public String getVehicle_name() {
        return vehicle_name;
    }

    public String getVehicle_number() {
        return vehicle_number;
    }

    public String getVehicle_color() {
        return vehicle_color;
    }
}
