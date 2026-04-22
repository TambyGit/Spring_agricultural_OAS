package com.spring.tdfinaloas_collectivites_agricoles.dao;

import com.spring.tdfinaloas_collectivites_agricoles.configuration.DataSource;
import com.spring.tdfinaloas_collectivites_agricoles.model.Collectivity;
import com.spring.tdfinaloas_collectivites_agricoles.model.CollectivityStructure;
import com.spring.tdfinaloas_collectivites_agricoles.model.Member;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CollectivityDao {
    private final DataSource dataSource;
    private final MemberDao memberDao;

    public CollectivityDao(DataSource dataSource, MemberDao memberDao) {
        this.dataSource = dataSource;
        this.memberDao = memberDao;
    }

    public void save(Collectivity collectivity) throws SQLException {
        String sql = "INSERT INTO collectivities (id, location, federation_approval) VALUES (?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, collectivity.getId());
            stmt.setString(2, collectivity.getLocation());
            stmt.setBoolean(3, collectivity.getFederationApproval() != null && collectivity.getFederationApproval());
            stmt.executeUpdate();
        }
    }

    public void updateNumberAndName(String collectivityId, String number, String name) throws SQLException {
        String sql = "UPDATE collectivities SET number = ?, name = ? WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, number);
            stmt.setString(2, name);
            stmt.setString(3, collectivityId);
            stmt.executeUpdate();
        }
    }

    public void saveStructure(String collectivityId, String presidentId, String vicePresidentId, String treasurerId, String secretaryId) throws SQLException {
        String sql = "INSERT INTO collectivity_structure (collectivity_id, president_id, vice_president_id, " +
                "treasurer_id, secretary_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, collectivityId);
            stmt.setString(2, presidentId);
            stmt.setString(3, vicePresidentId);
            stmt.setString(4, treasurerId);
            stmt.setString(5, secretaryId);
            stmt.executeUpdate();
        }
    }

    public Optional<Collectivity> findById(String id) throws SQLException {
        String sql = "SELECT * FROM collectivities WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Collectivity collectivity = new Collectivity();
                collectivity.setId(rs.getString("id"));
                collectivity.setLocation(rs.getString("location"));
                collectivity.setNumber(rs.getString("number"));
                collectivity.setName(rs.getString("name"));
                collectivity.setFederationApproval(rs.getBoolean("federation_approval"));
                

                Optional<CollectivityStructure> structureOpt = findStructureByCollectivityId(id);
                structureOpt.ifPresent(collectivity::setStructure);
                

                List<Member> members = findMembersByCollectivityId(id);
                collectivity.setMembers(members);
                
                return Optional.of(collectivity);
            }
            return Optional.empty();
        }
    }

    public Optional<CollectivityStructure> findStructureByCollectivityId(String collectivityId) throws SQLException {
        String sql = "SELECT * FROM collectivity_structure WHERE collectivity_id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, collectivityId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Optional<Member> president = memberDao.findById(rs.getString("president_id"));
                Optional<Member> vicePresident = memberDao.findById(rs.getString("vice_president_id"));
                Optional<Member> treasurer = memberDao.findById(rs.getString("treasurer_id"));
                Optional<Member> secretary = memberDao.findById(rs.getString("secretary_id"));
                
                if (president.isPresent() && vicePresident.isPresent() && 
                    treasurer.isPresent() && secretary.isPresent()) {
                    CollectivityStructure structure = new CollectivityStructure();
                    structure.setPresident(president.get());
                    structure.setVicePresident(vicePresident.get());
                    structure.setTreasurer(treasurer.get());
                    structure.setSecretary(secretary.get());
                    return Optional.of(structure);
                }
            }
            return Optional.empty();
        }
    }

    public List<Member> findMembersByCollectivityId(String collectivityId) throws SQLException {
        String sql = "SELECT member_id FROM collectivity_members WHERE collectivity_id = ?";
        List<Member> members = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, collectivityId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Optional<Member> memberOpt = memberDao.findById(rs.getString("member_id"));
                memberOpt.ifPresent(members::add);
            }
        }
        return members;
    }

    public boolean existsByName(String name) throws SQLException {
        String sql = "SELECT COUNT(*) FROM collectivities WHERE name = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
        }
    }

    public boolean existsByNumber(String number) throws SQLException {
        String sql = "SELECT COUNT(*) FROM collectivities WHERE number = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, number);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
        }
    }

    public void addMemberToCollectivity(String collectivityId, String memberId) throws SQLException {
        String sql = "INSERT INTO collectivity_members (collectivity_id, member_id) VALUES (?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, collectivityId);
            stmt.setString(2, memberId);
            stmt.executeUpdate();
        }
    }
}