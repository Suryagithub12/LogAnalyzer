package com.myproject.logAnalyzer.service;

import com.myproject.logAnalyzer.model.LogEntry;
import com.myproject.logAnalyzer.util.GeoIPUtil;
import com.myproject.logAnalyzer.util.GeoLocation;
import com.myproject.logAnalyzer.util.UserAgentParser;
import com.myproject.logAnalyzer.util.ValidatorUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

@Service
public class LogProcessingService {

    public List<LogEntry> processLogFile(MultipartFile file) throws Exception {
        List<LogEntry> validEntries = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("~");

                if (parts.length == 4 && ValidatorUtil.isValidEmail(parts[1]) && ValidatorUtil.isValidIP(parts[2])) {
                    String timestamp = parts[0];
                    String email = parts[1];
                    String ip = parts[2];
                    String userAgent = parts[3];

                    String deviceType = UserAgentParser.getDeviceType(userAgent);
                    String os = UserAgentParser.getOS(userAgent);
                    String androidVersion = UserAgentParser.getAndroidVersion(userAgent);
                    String detectBrowser = UserAgentParser.detectBrowser(userAgent);

                    // Get GeoLocation info
                    GeoLocation location = GeoIPUtil.getGeoLocation(ip);
                    String country = location.getCountry();
                    String city = location.getCity();

                    LogEntry entry = new LogEntry(timestamp, email, ip, userAgent, deviceType, os, androidVersion, detectBrowser, country, city);
                    validEntries.add(entry);
                }
            }
        }

        return validEntries;
    }

    public Map<String, Integer> getDeviceCount(List<LogEntry> entries) {
        Map<String, Integer> deviceCount = new HashMap<>();
        for (LogEntry entry : entries) {
            deviceCount.put(entry.getDeviceType(), deviceCount.getOrDefault(entry.getDeviceType(), 0) + 1);
        }
        return deviceCount;
    }

    public Map<String, Integer> getOSCount(List<LogEntry> entries) {
        Map<String, Integer> osCount = new HashMap<>();
        for (LogEntry entry : entries) {
            osCount.put(entry.getOs(), osCount.getOrDefault(entry.getOs(), 0) + 1);
        }
        return osCount;
    }

    public Map<String, Integer> getCountryStats(List<LogEntry> entries) {
        Map<String, Integer> countryStats = new HashMap<>();
        for (LogEntry entry : entries) {
            countryStats.put(entry.getCountry(), countryStats.getOrDefault(entry.getCountry(), 0) + 1);
        }
        return countryStats;
    }

    public Map<String, Integer> getHourlyStats(List<LogEntry> entries) {
        Map<String, Integer> hourlyStats = new TreeMap<>();
        for (LogEntry entry : entries) {
            String timestamp = entry.getTimestamp(); // assuming format like "2024-05-13 15:22:01"
            String hour = timestamp.split(" ")[1].split(":")[0]; // get "15" from "15:22:01"
            hourlyStats.put(hour, hourlyStats.getOrDefault(hour, 0) + 1);
        }
        return hourlyStats;
    }

}
