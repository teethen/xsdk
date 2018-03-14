package com.teethen.sdk.xlocation;

/**
 * Created by xingq on 2017/8/29.
 * GPS-WGS84 (GPS坐标)
 */

public class CoordinateGps {

    private double wgs84Lat;
    private double wgs84Lon;

    public CoordinateGps(double wgs84Lat, double wg84Lon) {
        setWgs84Lat(wgs84Lat);
        setWgs84Lon(wg84Lon);
    }

    public double getWgs84Lat() {
        return wgs84Lat;
    }

    public void setWgs84Lat(double wgs84Lat) {
        this.wgs84Lat = wgs84Lat;
    }

    public double getWgs84Lon() {
        return wgs84Lon;
    }

    public void setWgs84Lon(double wgs84Lon) {
        this.wgs84Lon = wgs84Lon;
    }

    @Override
    public String toString() {
        return wgs84Lat + "," + wgs84Lon;
    }
}
