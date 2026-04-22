package com.spring.tdfinaloas_collectivites_agricoles.controller;

import com.spring.tdfinaloas_collectivites_agricoles.model.Collectivity;
import com.spring.tdfinaloas_collectivites_agricoles.model.CreateCollectivity;
import com.spring.tdfinaloas_collectivites_agricoles.service.CollectivityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/collectivities")
public class CollectivityController {
    private final CollectivityService collectivityService;

    public CollectivityController(CollectivityService collectivityService) {
        this.collectivityService = collectivityService;
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

    // NOUVELLE MÉTHODE POUR L'ATTRIBUTION
    @PutMapping("/{collectivityId}/attribution")
    public ResponseEntity<?> assignAttribution(
            @PathVariable String collectivityId,
            @RequestBody Map<String, String> request) {
        
        // Vérifier que le nom est présent
        String name = request.get("name");
        if (name == null || name.trim().isEmpty()) {
            return new ResponseEntity<>("Name is required", HttpStatus.BAD_REQUEST);
        }
        
        String number = request.get("number");
        
        try {
            Collectivity updatedCollectivity = collectivityService.assignNumberAndName(collectivityId, number, name);
            return new ResponseEntity<>(updatedCollectivity, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("not found")) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
            }
            if (e.getMessage().contains("already has a number")) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
            if (e.getMessage().contains("already used by another collectivity")) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
            }
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (SQLException e) {
            return new ResponseEntity<>("Database error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}