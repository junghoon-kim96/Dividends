package com.sample.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//재정 재무 금융
@RestController
@RequestMapping("/finance")
public class FinanceController {

    @GetMapping("/dividend/{companyName")
    public ResponseEntity<?> searchFinance(@PathVariable String companyName){
        return null;
    }
}
