package com.coffeeshop.dao;

import com.coffeeshop.model.Testimonial;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TestimonialDAO {
    public List<Testimonial> getAll() throws Exception {
        List<Testimonial> list = new ArrayList<>();
        String sql = "SELECT * FROM testimonials ORDER BY id";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
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
