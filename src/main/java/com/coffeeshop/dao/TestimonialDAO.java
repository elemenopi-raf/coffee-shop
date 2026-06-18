package com.coffeeshop.dao;

import com.coffeeshop.model.Testimonial;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Data access object for testimonials table.
 */
public final class TestimonialDAO {

    /**
     * Retrieves all testimonials ordered by id.
     *
     * @return list of all testimonials
     * @throws Exception if a database error occurs
     */
    public List<Testimonial> getAll() throws Exception {
        List<Testimonial> list = new ArrayList<>();
        String sql = "SELECT id, text, author FROM testimonials ORDER BY id";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Testimonial t = new Testimonial();
                t.setId(rs.getInt("id"));
                t.setText(rs.getString("text"));
                t.setAuthor(rs.getString("author"));
                list.add(t);
            }
        }
        return list;
    }
}
