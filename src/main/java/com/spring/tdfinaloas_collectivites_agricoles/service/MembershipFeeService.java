package com.spring.tdfinaloas_collectivites_agricoles.service;

import com.spring.tdfinaloas_collectivites_agricoles.dao.CollectivityDao;
import com.spring.tdfinaloas_collectivites_agricoles.dao.MembershipFeeDao;
import com.spring.tdfinaloas_collectivites_agricoles.model.*;
import com.spring.tdfinaloas_collectivites_agricoles.model.enums.ActivityStatus;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class MembershipFeeService {
    private final MembershipFeeDao membershipFeeDao;
    private final CollectivityDao collectivityDao;

    public MembershipFeeService(MembershipFeeDao membershipFeeDao, CollectivityDao collectivityDao) {
        this.membershipFeeDao = membershipFeeDao;
        this.collectivityDao = collectivityDao;
    }

    public List<MembershipFee> findByCollectivityId(String collectivityId) throws SQLException {
        if (collectivityDao.findById(collectivityId).isEmpty()) {
            throw new IllegalArgumentException("Collectivity not found: " + collectivityId);
        }
        return membershipFeeDao.findByCollectivityId(collectivityId);
    }

    public List<MembershipFee> createMembershipFees(String collectivityId, List<CreateMembershipFee> feeRequests) throws SQLException {
        if (collectivityDao.findById(collectivityId).isEmpty()) {
            throw new IllegalArgumentException("Collectivity not found: " + collectivityId);
        }

        List<MembershipFee> createdFees = new ArrayList<>();
        for (CreateMembershipFee request : feeRequests) {
            if (request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Amount must be greater than 0");
            }

            MembershipFee fee = new MembershipFee();
            fee.setId(UUID.randomUUID().toString());
            fee.setCollectivityId(collectivityId);
            fee.setEligibleFrom(request.getEligibleFrom());
            fee.setFrequency(request.getFrequency());
            fee.setAmount(request.getAmount());
            fee.setLabel(request.getLabel());
            fee.setStatus(ActivityStatus.ACTIVE);

            membershipFeeDao.save(fee);
            createdFees.add(fee);
        }
        return createdFees;
    }
}