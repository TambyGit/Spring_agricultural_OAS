package com.spring.tdfinaloas_collectivites_agricoles.dao;

import com.spring.tdfinaloas_collectivites_agricoles.configuration.DataSource;
import com.spring.tdfinaloas_collectivites_agricoles.model.Collectivity;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class CollectivityDao {
    private final DataSource dataSource;

    public CollectivityDao(DataSource dataSource) {
        this.dataSource = dataSource;
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

    public void saveStructure(String collectivityId, String presidentId, String vicePresidentId,
                              String treasurerId, String secretaryId) throws SQLException {
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
                collectivity.setFederationApproval(rs.getBoolean("federation_approval"));
                return Optional.of(collectivity);
            }
            return Optional.empty();
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

    public Optional<Collectivity> findStructureByCollectivityId(String collectivityId) throws SQLException {
        String sql = "SELECT * FROM collectivity_structure WHERE collectivity_id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, collectivityId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Collectivity collectivity = new Collectivity();
                collectivity.setId(collectivityId);
                // La structure sera chargée séparément
                return Optional.of(collectivity);
            }
            return Optional.empty();
        }
    }
}
