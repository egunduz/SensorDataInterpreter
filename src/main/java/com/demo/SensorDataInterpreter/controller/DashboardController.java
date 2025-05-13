package com.demo.SensorDataInterpreter.controller;

import com.demo.SensorDataInterpreter.repository.MetricAlertRepository;
import com.demo.SensorDataInterpreter.repository.SensorMetricsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final SensorMetricsRepository metricService;
    private final MetricAlertRepository alertService;

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        model.addAttribute("metrics", metricService.findAll());
        model.addAttribute("alerts", alertService.findAll());

        return "dashboard"; // maps to dashboard.html in templates
    }
}

