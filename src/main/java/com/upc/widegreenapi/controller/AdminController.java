package com.upc.widegreenapi.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin
public class AdminController {
    @GetMapping("/dashboard")
    public String verDashboardAdmin() {
        return "Bienvenido al dashboard del ADMIN";
    }
}
