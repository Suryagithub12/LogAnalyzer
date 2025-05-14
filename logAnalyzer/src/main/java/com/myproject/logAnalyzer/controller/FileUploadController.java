package com.myproject.logAnalyzer.controller;

import com.myproject.logAnalyzer.service.LogProcessingService;
import com.myproject.logAnalyzer.model.LogEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Controller
public class FileUploadController {

    @Autowired
    private LogProcessingService logProcessingService;

    @PostMapping("/upload")
    public String uploadLogFile(@RequestParam("file") MultipartFile file, Model model) {
        try {
            // Process uploaded log file
            List<LogEntry> validEntries = logProcessingService.processLogFile(file);

            // Get various stats
            Map<String, Integer> deviceCount = logProcessingService.getDeviceCount(validEntries);
            Map<String, Integer> osCount = logProcessingService.getOSCount(validEntries);
            Map<String, Integer> countryStats = logProcessingService.getCountryStats(validEntries);

            // ✅ New: Get hourly hit stats
            Map<String, Integer> hourlyStats = logProcessingService.getHourlyStats(validEntries);

            // Add attributes to model
            model.addAttribute("entries", validEntries);
            model.addAttribute("total", validEntries.size());
            model.addAttribute("deviceCount", deviceCount);
            model.addAttribute("osCount", osCount);
            model.addAttribute("countryStats", countryStats);
            model.addAttribute("hourlyStats", hourlyStats); // ✅ Add this for Chart.js

        } catch (Exception e) {
            model.addAttribute("message", "Error while reading file.");
        }
        return "result";
    }
}
