package com.example.healthCard.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ContentDeliveryController {
        @GetMapping("/login")
        public String loginContent(){
        return "login";
    }

        @GetMapping("/home")
        public String home(){
        return "home";
    }

        @GetMapping("/register")
        public String registerContent(){
        return "register";
    }

        @GetMapping("/forgot-password")
        public String forgotPassword(){
        return "forgot_password";
    }

        @GetMapping("/update-password")
        public String updatePassword(){
        return "update_password";
    }

/*        @PostMapping("/login")
        @ResponseBody
        public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> input) {
        Map<String, String> response = new HashMap<>();

        if ("aman.kaushal@calsoftinc.com".equals(input.get("username")) && "aman".equals(input.get("password"))) {
            response.put("jwt", "token");
            response.put("redirectUrl", "/dashboard"); // URL to redirect to on success
            return ResponseEntity.ok(response);
        } else {
            response.put("error", "The email address or password you entered is incorrect.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }*/

        @GetMapping("/api/protected-data")
        public ResponseEntity<Map<String, String>> getProtectedData(@RequestHeader(value = "Authorization") String authHeader) {
        // Check the token and return data
        Map<String, String> response = new HashMap<>();
        response.put("data", "Example data");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // Remove "Bearer " prefix
            // Validate the token and respond accordingly
            // For demonstration, just return some data
            return ResponseEntity.ok(response);
        } else {
            response.put("error", "The email address or password you entered is incorrect.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

        @GetMapping("/profile")
        public ResponseEntity<Map<String, String>> getProfile(@RequestHeader(value = "Authorization") String authHeader){
        Map<String, String> response = new HashMap<>();
        response.put("name", "Aman");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // Remove "Bearer " prefix
            // Validate the token and respond accordingly
            // For demonstration, just return some data
            return ResponseEntity.ok(response);
        } else {
            response.put("error", "The email address or password you entered is incorrect.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

        @GetMapping("/list-hospital")
        public ResponseEntity<List<Map<String, Object>>> listHospitals(@RequestHeader(value = "Authorization") String authHeader){
        List<Map<String, Object>> output = new ArrayList<>();
        Map<String, Object> response = new HashMap<>();
        response.put("name", "Apple hospital");
        response.put("address", "Bhawarkua Indore");
        response.put("state", "Madhya Pradesh");
        response.put("contact", "9988229911");
        response.put("coverage", true);

        output.add(response);

        Map<String, Object> response2 = new HashMap<>();
        response2.put("name", "Bombay hospital");
        response2.put("address", "Indore");
        response2.put("state", "Madhya Pradesh");
        response2.put("contact", "7788778811");
        response2.put("coverage", true);

        output.add(response2);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // Remove "Bearer " prefix
            // Validate the token and respond accordingly
            // For demonstration, just return some data
            return ResponseEntity.ok(output);
        } else {
            response.put("error", "The email address or password you entered is incorrect.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(output);
        }
    }

        @GetMapping("/dashboard")
        public String dashboard(){
        return "dashboard";
    }
    }
