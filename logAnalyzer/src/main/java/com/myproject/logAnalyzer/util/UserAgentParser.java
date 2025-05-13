package com.myproject.logAnalyzer.util;

public class UserAgentParser {

    public static String getDeviceType(String userAgent) {
        String ua = userAgent.toLowerCase();
        if (ua.contains("mobile")) return "Mobile";
        else if (ua.contains("tablet")) return "Tablet";
        else return "PC";
    }

    public static String getOS(String userAgent) {
        if (userAgent.contains("Windows")) return "Windows";
        if (userAgent.contains("Mac")) return "Mac";
        if (userAgent.contains("Android")) return "Android";
        if (userAgent.contains("iPhone") || userAgent.contains("iPad")) return "iOS";
        if (userAgent.contains("Linux")) return "Linux";
        return "Other";
    }

    public static String detectBrowser(String userAgent) {
        if (userAgent.contains("Chrome/")) return "Chrome";
        if (userAgent.contains("Safari/") && !userAgent.contains("Chrome")) return "Safari";
        if (userAgent.contains("Firefox/")) return "Firefox";
        if (userAgent.contains("Edge/")) return "Edge";
        return "Unknown Browser";
    }

    public static String getAndroidVersion(String userAgent) {
        if (userAgent.contains("Android")) {
            int index = userAgent.indexOf("Android");
            int end = userAgent.indexOf(";", index);
            if (index != -1 && end != -1) {
                return userAgent.substring(index + 8, end).trim();
            }
        }
        return "N/A";
    }
}
