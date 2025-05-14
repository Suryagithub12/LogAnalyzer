package com.myproject.logAnalyzer.util;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

public class GeoIPUtil {
    private static DatabaseReader reader;

    static {
        try {
            // Load MaxMind DB file (GeoLite2)
            File dbFile = new File("src/main/resources/GeoLite2-City.mmdb");
            reader = new DatabaseReader.Builder(dbFile).build();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static GeoLocation getGeoLocation(String ip) {
        try {
            InetAddress ipAddress = InetAddress.getByName(ip);
            CityResponse response = reader.city(ipAddress);  // get city information

            String country = response.getCountry().getName();  // Get country name
            String city = response.getCity().getName();  // Get city name
            return new GeoLocation(country, city);
        } catch (Exception e) {
            return new GeoLocation("Unknown", "Unknown");
        }
    }
}