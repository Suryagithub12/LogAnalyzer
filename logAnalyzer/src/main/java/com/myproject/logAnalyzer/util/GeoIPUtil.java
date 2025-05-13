package com.myproject.logAnalyzer.util;

import com.maxmind.db.Reader;
import com.maxmind.geoip2.model.CityResponse;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

public class GeoIPUtil {
    private static Reader reader;

    static {
        try {
            // Load MaxMind DB file
            File dbFile = new File("src/main/resources/GeoLite2-City.mmdb");
            reader = new Reader(dbFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static GeoLocation getGeoLocation(String ip) {
        try {
            InetAddress ipAddress = InetAddress.getByName(ip);
            CityResponse response = reader.city(ipAddress);

            String country = response.getCountry().getName();
            String city = response.getCity().getName();
            return new GeoLocation(country, city);
        } catch (Exception e) {
            return new GeoLocation("Unknown", "Unknown");
        }
    }
}
