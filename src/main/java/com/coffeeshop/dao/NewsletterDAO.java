package com.coffeeshop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Data access object for newsletter_emails table.
 */
public final class NewsletterDAO {

    /**
     * Saves a new subscriber email to the database.
     *
     * @param email the email to save
     * @throws Exception if a database error occurs
     */
    public void save(final String email) throws Exception {
        String sql = "INSERT INTO newsletter_emails (email) VALUES (?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.executeUpdate();
        }
    }

    /**
     * Checks whether an email already exists in the database.
     *
     * @param email the email to check
     * @return true if the email already exists
     * @throws Exception if a database error occurs
     */
    public boolean exists(final String email) throws Exception {
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
