package com.myproject.logAnalyzer.model;

public class LogEntry {
    private String email;
    private String ip;
    private String userAgent;
    private String deviceType;
    private String os;
    private String androidVersion;
    private String detectBrowser;
    private String timestamp;
    private String country;
    private String city;


    public LogEntry(String email, String ip, String userAgent, String deviceType, String os, String androidVersion, String detectBrowser) {
        this.email = email;
        this.ip = ip;
        this.userAgent = userAgent;
        this.deviceType = deviceType;
        this.os = os;
        this.androidVersion = androidVersion;
        this.detectBrowser = detectBrowser;
        this.timestamp = timestamp;
        this.country = country;
        this.city = city;
    }

    // Getters
    public String getEmail() { return email; }
    public String getIp() { return ip; }
    public String getUserAgent() { return userAgent; }
    public String getDeviceType() { return deviceType; }
    public String getOs() { return os; }
    public String getAndroidVersion() { return androidVersion; }
    public String getDetectBrowser() { return detectBrowser; }
    public String getTimestamp(){return timestamp;}
    public String getCountry(){return country;}
    public String getCity(){return city;}
}
