package com.spring.tdfinaloas_collectivites_agricoles.service;

import com.spring.tdfinaloas_collectivites_agricoles.dao.CollectivityDao;
import com.spring.tdfinaloas_collectivites_agricoles.dao.MemberDao;
import com.spring.tdfinaloas_collectivites_agricoles.model.*;
import org.springframework.stereotype.Service;
import java.sql.SQLException;
import java.util.*;

@Service
public class CollectivityService {
    private final CollectivityDao collectivityDao;
    private final MemberDao memberDao;

    public CollectivityService(CollectivityDao collectivityDao, MemberDao memberDao) {
        this.collectivityDao = collectivityDao;
        this.memberDao = memberDao;
    }

    public List<Collectivity> createCollectivities(List<CreateCollectivity> createCollectivities) throws SQLException {
        List<Collectivity> createdCollectivities = new ArrayList<>();
        for (CreateCollectivity createCollectivity : createCollectivities) {
            if (createCollectivity.getFederationApproval() == null || !createCollectivity.getFederationApproval()) {
                throw new IllegalArgumentException("Collectivity without federation approval");
            }
            if (createCollectivity.getStructure() == null) {
                throw new IllegalArgumentException("Structure missing");
            }
            List<Member> members = new ArrayList<>();
            if (createCollectivity.getMembers() != null) {
                for (String memberId : createCollectivity.getMembers()) {
                    Optional<Member> memberOpt = memberDao.findById(memberId);
                    if (memberOpt.isEmpty()) {
                        throw new IllegalArgumentException("Member not found: " + memberId);
                    }
                    members.add(memberOpt.get());
                }
            }

            CreateCollectivityStructure structure = createCollectivity.getStructure();
            Optional<Member> president = memberDao.findById(structure.getPresident());
            Optional<Member> vicePresident = memberDao.findById(structure.getVicePresident());
            Optional<Member> treasurer = memberDao.findById(structure.getTreasurer());
            Optional<Member> secretary = memberDao.findById(structure.getSecretary());
            if (president.isEmpty() || vicePresident.isEmpty() || treasurer.isEmpty() || secretary.isEmpty()) {
                throw new IllegalArgumentException("Member not found in structure");
            }

            String collectivityId = UUID.randomUUID().toString();
            Collectivity collectivity = new Collectivity();
            collectivity.setId(collectivityId);
            collectivity.setLocation(createCollectivity.getLocation());
            collectivity.setFederationApproval(createCollectivity.getFederationApproval());

            collectivityDao.save(collectivity);
            collectivityDao.saveStructure(collectivityId, structure.getPresident(), structure.getVicePresident(), structure.getTreasurer(), structure.getSecretary());

            CollectivityStructure collectivityStructure = new CollectivityStructure();
            collectivityStructure.setPresident(president.get());
            collectivityStructure.setVicePresident(vicePresident.get());
            collectivityStructure.setTreasurer(treasurer.get());
            collectivityStructure.setSecretary(secretary.get());
            collectivity.setStructure(collectivityStructure);
            collectivity.setMembers(members);

            for (Member member : members) {
                collectivityDao.addMemberToCollectivity(collectivityId, member.getId());
            }
            createdCollectivities.add(collectivity);
        }
        return createdCollectivities;
    }

    public Collectivity updateCollectivityInformation(String collectivityId, Collectivity info) throws SQLException {
        Optional<Collectivity> collectivityOpt = collectivityDao.findById(collectivityId);
        if (collectivityOpt.isEmpty()) {
            throw new IllegalArgumentException("Collectivity not found: " + collectivityId);
        }

        if (collectivityDao.existsByName(info.getName())) {
            throw new IllegalArgumentException("The name '" + info.getName() + "' is already used by another collectivity.");
        }

        if (info.getNumber() != null && collectivityDao.existsByNumber(String.valueOf(info.getNumber()))) {
            throw new IllegalArgumentException("The number '" + info.getNumber() + "' is already used by another collectivity.");
        }

        collectivityDao.updateNumberAndName(collectivityId, String.valueOf(info.getNumber()), info.getName());
        return collectivityDao.findById(collectivityId).orElseThrow(() -> new SQLException("Failed to retrieve updated collectivity"));
    }
}