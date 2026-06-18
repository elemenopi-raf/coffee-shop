package com.coffeeshop.dao;

import com.coffeeshop.model.ContactMessage;
<<<<<<< Updated upstream
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
=======
import java.sql.Connection;
import java.sql.PreparedStatement;
>>>>>>> Stashed changes

/**
 * Data access object for contact_messages table.
 */
public final class ContactMessageDAO {

    /**
     * Saves a contact message to the database.
     *
     * @param msg the contact message to save
     * @throws Exception if a database error occurs
     */
    public void save(final ContactMessage msg) throws Exception {
        String sql = "INSERT INTO contact_messages (name, email, message) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, msg.getName());
            stmt.setString(2, msg.getEmail());
            stmt.setString(3, msg.getMessage());
            stmt.executeUpdate();
        }
    }

    public List<ContactMessage> findAll() throws Exception {
        List<ContactMessage> messages = new ArrayList<>();
        String sql = "SELECT id, name, email, message, created_at FROM contact_messages ORDER BY created_at DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                ContactMessage msg = new ContactMessage();
                msg.setId(rs.getInt("id"));
                msg.setName(rs.getString("name"));
                msg.setEmail(rs.getString("email"));
                msg.setMessage(rs.getString("message"));
                Timestamp ts = rs.getTimestamp("created_at");
                if (ts != null) msg.setCreatedAt(ts.toLocalDateTime());
                messages.add(msg);
            }
        }
        return messages;
    }
}
