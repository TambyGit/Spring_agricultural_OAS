package com.spring.tdfinaloas_collectivites_agricoles_finale.service;

import com.spring.tdfinaloas_collectivites_agricoles_finale.entity.*;
import com.spring.tdfinaloas_collectivites_agricoles_finale.exception.BadRequestException;
import com.spring.tdfinaloas_collectivites_agricoles_finale.exception.NotFoundException;
import com.spring.tdfinaloas_collectivites_agricoles_finale.repository.ActivityRepository;
import com.spring.tdfinaloas_collectivites_agricoles_finale.repository.CollectivityRepository;
import com.spring.tdfinaloas_collectivites_agricoles_finale.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ActivityService {
    private final ActivityRepository activityRepository;
    private final CollectivityRepository collectivityRepository;
    private final MemberRepository memberRepository;

    public List<Activity> createActivities(String collectivityId, List<Activity> activities) {
        Collectivity collectivity = collectivityRepository.findById(collectivityId)
                .orElseThrow(() -> new NotFoundException("Collectivity.id= " + collectivityId + " not found"));
        for (Activity activity : activities) {
            activity.setId(UUID.randomUUID().toString());
            activity.setCollectivity(collectivity);
        }
        return activityRepository.saveAll(activities);
    }

    public List<Activity> getActivitiesByCollectivity(String collectivityId) {
        collectivityRepository.findById(collectivityId)
                .orElseThrow(() -> new NotFoundException("Collectivity.id= " + collectivityId + " not found"));
        return activityRepository.findAllByCollectivityId(collectivityId);
    }

    public List<ActivityAttendance> createAttendance(String collectivityId, String activityId,
                                                     List<String> presentIds, List<String> absentIds) {
        collectivityRepository.findById(collectivityId)
                .orElseThrow(() -> new NotFoundException("Collectivity.id= " + collectivityId + " not found"));
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new NotFoundException("Activity.id= " + activityId + " not found"));

        List<ActivityAttendance> attendances = new ArrayList<>();

        for (String memberId : presentIds) {
            if (activityRepository.attendanceAlreadyExists(activityId, memberId)) {
                throw new BadRequestException("Attendance already recorded for Member.id= " + memberId);
            }
            Member member = memberRepository.findById(memberId)
                    .orElseThrow(() -> new NotFoundException("Member.id= " + memberId + " not found"));
            attendances.add(ActivityAttendance.builder()
                    .id(UUID.randomUUID().toString())
                    .activity(activity)
                    .member(member)
                    .status(AttendanceStatus.PRESENT)
                    .build());
        }

        for (String memberId : absentIds) {
            if (activityRepository.attendanceAlreadyExists(activityId, memberId)) {
                throw new BadRequestException("Attendance already recorded for Member.id= " + memberId);
            }
            Member member = memberRepository.findById(memberId)
                    .orElseThrow(() -> new NotFoundException("Member.id= " + memberId + " not found"));
            attendances.add(ActivityAttendance.builder()
                    .id(UUID.randomUUID().toString())
                    .activity(activity)
                    .member(member)
                    .status(AttendanceStatus.ABSENT)
                    .build());
        }

        return activityRepository.saveAttendances(attendances);
    }

    public List<ActivityAttendance> getAttendance(String collectivityId, String activityId) {
        collectivityRepository.findById(collectivityId)
                .orElseThrow(() -> new NotFoundException("Collectivity.id= " + collectivityId + " not found"));
        activityRepository.findById(activityId)
                .orElseThrow(() -> new NotFoundException("Activity.id= " + activityId + " not found"));
        return activityRepository.findAttendancesByActivityId(activityId);
    }
}