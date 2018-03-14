package com.teethen.sdk.xlocation;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.teethen.sdk.xutil.PermissionUtil;
import com.teethen.sdk.xutil.ToastUtil;

/**
 * Created by xingq on 2017/8/26.
 * 谷歌GPS定位
 */

public class LocationUtil {
    private static final String TAG = "LocationUtil";

    private static long minTime = 1 * 1000;
    private static float minDistance = 0f;

    private static Location gpsLocation;
    private static LocationManager locationManager;
    private static LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            gpsLocation = location;
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

    /**
     * 获取GPS定位
     * @param context
     * @return
     */
    public static Location getLocation(Context context) {
        Location location = null;

        try {
            if (locationManager == null) {
                locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            }

            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, locationListener);
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (location == null) {
                        location = getLocationFromNetwork(context, locationManager);
                    }
                } else {
                    location = getLocationFromNetwork(context, locationManager);
                }
            } else {
                PermissionUtil permissionUtil = new PermissionUtil(context);
                permissionUtil.requestPermissions("", new PermissionUtil.PermissionListener() {
                    @Override
                    public void doAfterGrand(String... permission) {
                        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                            getLocation(context);
                        }
                    }
                    @Override
                    public void doAfterDenied(String... permission) {
                        ToastUtil.showToast(context, "GPS定位权限被拒绝授予本应用");
                    }
                }, Manifest.permission.ACCESS_FINE_LOCATION);
            }

            if (locationManager != null && locationListener != null) {
                locationManager.removeUpdates(locationListener);
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }

        gpsLocation = location;

        return location;
    }

    private static Location getLocationFromNetwork(Context context, LocationManager locationManager) {
        Location location = null;

        if (locationManager == null) {
            locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        }

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, minTime, minDistance, locationListener);
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }

        return location;
    }

    public static String getLongitudeLatitude(Location... location) {
        String s = null;

        //double[] lat_lon = new double[2];

        if (location != null && location.length == 1) {
            Location l = location[0];
            s = l.getLongitude() + "," + l.getLatitude(); //原生gps
            //lat_lon = CoordinateUtil.wgs84toBd09(l.getLongitude(), l.getLatitude());
        } else if (gpsLocation != null) {
            s = gpsLocation.getLongitude() + "," + gpsLocation.getLatitude(); //原生gps
        }

        //double longitude = lat_lon[0];
        //double latitude = lat_lon[1];

        //s = longitude + "," + latitude;

        return s;
    }
}
