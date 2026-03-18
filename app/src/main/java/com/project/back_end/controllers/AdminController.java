
package com.project.back_end.controllers;

import java.util.Map;

import com.project.back_end.services.CommonService;
import com.project.back_end.services.Commonservice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.back_end.models.Admin;

@RestController
@RequestMapping("${api.path}admin")
public class AdminController {

    private final CommonService service;

    public AdminController(Commonservice commonservice) {
        this.service = commonservice;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> adminLogin(@RequestBody Admin admin) {
        return service.validateAdmin(admin);
    }
}

