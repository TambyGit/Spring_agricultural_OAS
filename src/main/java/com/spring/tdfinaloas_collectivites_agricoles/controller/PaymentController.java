package com.spring.tdfinaloas_collectivites_agricoles.controller;

import com.spring.tdfinaloas_collectivites_agricoles.model.MemberPayment;
import com.spring.tdfinaloas_collectivites_agricoles.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/members")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/{id}/payments")
    public ResponseEntity<?> createPayments(@PathVariable String id, @RequestBody List<MemberPayment> payments) {
        try {
            List<MemberPayment> created = paymentService.createPayments(id, payments);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (SQLException e) {
            return new ResponseEntity<>("Database error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}