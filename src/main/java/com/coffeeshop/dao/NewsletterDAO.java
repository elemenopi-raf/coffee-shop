package com.coffeeshop.dao;

import java.sql.*;

public class NewsletterDAO {
    public void save(String email) throws Exception {
        String sql = "INSERT INTO newsletter_emails (email) VALUES (?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.executeUpdate();
        }
    }

    public boolean exists(String email) throws Exception {
        String sql = "SELECT COUNT(*) FROM newsletter_emails WHERE email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                rs.next();
                return rs.getInt(1) > 0;
            }
        }
    }
}
