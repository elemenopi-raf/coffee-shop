package com.coffeeshop.dao;

import com.coffeeshop.model.ContactMessage;
import java.sql.*;

public class ContactMessageDAO {
    public void save(ContactMessage msg) throws Exception {
        String sql = "INSERT INTO contact_messages (name, email, message) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, msg.getName());
            stmt.setString(2, msg.getEmail());
            stmt.setString(3, msg.getMessage());
            stmt.executeUpdate();
        }
    }
}
