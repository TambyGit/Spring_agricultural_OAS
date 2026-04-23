package com.spring.tdfinaloas_collectivites_agricoles.controller;

import com.spring.tdfinaloas_collectivites_agricoles.model.*;
import com.spring.tdfinaloas_collectivites_agricoles.service.CollectivityService;
import com.spring.tdfinaloas_collectivites_agricoles.service.MembershipFeeService;
import com.spring.tdfinaloas_collectivites_agricoles.service.TransactionService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/collectivities")
public class CollectivityController {
    private final CollectivityService collectivityService;
    private final MembershipFeeService membershipFeeService;
    private final TransactionService transactionService;

    public CollectivityController(CollectivityService collectivityService,
                                  MembershipFeeService membershipFeeService,
                                  TransactionService transactionService) {
        this.collectivityService = collectivityService;
        this.membershipFeeService = membershipFeeService;
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<?> createCollectivities(@RequestBody List<CreateCollectivity> createCollectivities) {
        try {
            List<Collectivity> created = collectivityService.createCollectivities(createCollectivities);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("federation approval") || e.getMessage().contains("Structure missing")) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
            if (e.getMessage().contains("Member not found")) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (SQLException e) {
            return new ResponseEntity<>("Database error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}/informations")
    public ResponseEntity<?> updateCollectivityInformation(@PathVariable String id, @RequestBody Collectivity info) {
        try {
            Collectivity updated = collectivityService.updateCollectivityInformation(id, info);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("not found")) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (SQLException e) {
            return new ResponseEntity<>("Database error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}/membershipFees")
    public ResponseEntity<?> getMembershipFees(@PathVariable String id) {
        try {
            List<MembershipFee> fees = membershipFeeService.findByCollectivityId(id);
            return new ResponseEntity<>(fees, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (SQLException e) {
            return new ResponseEntity<>("Database error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{id}/membershipFees")
    public ResponseEntity<?> createMembershipFees(@PathVariable String id, @RequestBody List<CreateMembershipFee> fees) {
        try {
            List<MembershipFee> created = membershipFeeService.createMembershipFees(id, fees);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("not found")) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (SQLException e) {
            return new ResponseEntity<>("Database error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}/transactions")
    public ResponseEntity<?> getTransactions(
            @PathVariable String id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        try {
            List<Transaction> transactions = transactionService.findByCollectivityIdAndDateRange(id, from, to);
            return new ResponseEntity<>(transactions, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("not found")) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (SQLException e) {
            return new ResponseEntity<>("Database error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}