package com.myproject.logAnalyzer.service;

import com.myproject.logAnalyzer.model.LogEntry;
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

                if (parts.length == 3 && ValidatorUtil.isValidEmail(parts[0]) && ValidatorUtil.isValidIP(parts[1])) {
                    String email = parts[0];
                    String ip = parts[1];
                    String userAgent = parts[2];

                    String deviceType = UserAgentParser.getDeviceType(userAgent);
                    String os = UserAgentParser.getOS(userAgent);
                    String androidVersion = UserAgentParser.getAndroidVersion(userAgent);
                    String detectBrowser = UserAgentParser.detectBrowser(userAgent);

                    LogEntry entry = new LogEntry(email, ip, userAgent, deviceType, os, androidVersion, detectBrowser);
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
}
