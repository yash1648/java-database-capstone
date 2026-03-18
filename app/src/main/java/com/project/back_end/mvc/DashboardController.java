package com.project.back_end.mvc;

import com.project.back_end.services.Commonservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class DashboardController {

    @Autowired
    private Commonservice commonservice;

    /*
     Admin Dashboard Route
     URL Example:
     /adminDashboard/{token}
    */
    @GetMapping("/adminDashboard/{token}")
    public String adminDashboard(@PathVariable String token) {

        boolean isValid = commonservice.validateToken(token, "admin");

        if (isValid) {
            return "admin/adminDashboard";
        }

        return "redirect:/";
    }

    /*
     Doctor Dashboard Route
     URL Example:
     /doctorDashboard/{token}
    */
    @GetMapping("/doctorDashboard/{token}")
    public String doctorDashboard(@PathVariable String token) {

        boolean isValid = commonservice.validateToken(token, "doctor");

        if (isValid) {
            return "doctor/doctorDashboard";
        }

        return "redirect:/";
    }
}