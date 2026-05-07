package com.spring.tdfinaloas_collectivites_agricoles_finale.controller;

import com.spring.tdfinaloas_collectivites_agricoles_finale.controller.dto.*;
import com.spring.tdfinaloas_collectivites_agricoles_finale.entity.Activity;
import com.spring.tdfinaloas_collectivites_agricoles_finale.entity.ActivityType;
import com.spring.tdfinaloas_collectivites_agricoles_finale.exception.BadRequestException;
import com.spring.tdfinaloas_collectivites_agricoles_finale.exception.NotFoundException;
import com.spring.tdfinaloas_collectivites_agricoles_finale.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
public class ActivityController {
    private final ActivityService activityService;

    @PostMapping("/collectivities/{id}/activities")
    public ResponseEntity<?> createActivities(@PathVariable String id,
                                              @RequestBody List<CreateActivity> createActivities) {
        try {
            List<Activity> activities = createActivities.stream()
                    .map(dto -> Activity.builder()
                            .label(dto.getLabel())
                            .type(dto.getType() == null ? null : ActivityType.valueOf(dto.getType()))
                            .recurrence(dto.getRecurrence())
                            .date(dto.getDate())
                            .build())
                    .toList();
            return ResponseEntity.status(OK)
                    .body(activityService.createActivities(id, activities).stream()
                            .map(a -> ActivityDto.builder()
                                    .id(a.getId())
                                    .label(a.getLabel())
                                    .date(a.getDate())
                                    .build())
                            .toList());
        } catch (NotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/collectivities/{id}/activities")
    public ResponseEntity<?> getActivities(@PathVariable String id) {
        try {
            return ResponseEntity.status(OK)
                    .body(activityService.getActivitiesByCollectivity(id).stream()
                            .map(a -> ActivityDto.builder()
                                    .id(a.getId())
                                    .label(a.getLabel())
                                    .type(a.getType() == null ? null : a.getType().name())
                                    .recurrence(a.getRecurrence())
                                    .date(a.getDate())
                                    .build())
                            .toList());
        } catch (NotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/collectivities/{id}/activities/{activityId}/attendance")
    public ResponseEntity<?> createAttendance(@PathVariable String id,
                                              @PathVariable String activityId,
                                              @RequestBody CreateAttendance createAttendance) {
        try {
            return ResponseEntity.status(OK)
                    .body(activityService.createAttendance(
                                    id, activityId,
                                    createAttendance.getPresentMemberIds(),
                                    createAttendance.getAbsentMemberIds()).stream()
                            .map(a -> AttendanceDto.builder()
                                    .memberId(a.getMember().getId())
                                    .firstName(a.getMember().getFirstName())
                                    .lastName(a.getMember().getLastName())
                                    .status(a.getStatus().name())
                                    .build())
                            .toList());
        } catch (BadRequestException e) {
            return ResponseEntity.status(BAD_REQUEST).body(e.getMessage());
        } catch (NotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/collectivities/{id}/activities/{activityId}/attendance")
    public ResponseEntity<?> getAttendance(@PathVariable String id,
                                           @PathVariable String activityId) {
        try {
            return ResponseEntity.status(OK)
                    .body(activityService.getAttendance(id, activityId).stream()
                            .map(a -> AttendanceDto.builder()
                                    .memberId(a.getMember().getId())
                                    .firstName(a.getMember().getFirstName())
                                    .lastName(a.getMember().getLastName())
                                    .status(a.getStatus().name())
                                    .build())
                            .toList());
        } catch (NotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}