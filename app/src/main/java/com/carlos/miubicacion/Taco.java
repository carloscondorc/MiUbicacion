package com.carlos.miubicacion;

/**
 * Created by CarlosEduardo on 19/08/2017.
 */

public class Taco {
    private double latitude;
    private double longitude;
    private String flavor;

    public Taco(double latitude, double longitude, String flavor) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.flavor = flavor;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getFlavor() {
        return flavor;
    }

    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }
}
