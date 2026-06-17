package com.coffeeshop.dao;

import com.coffeeshop.model.MenuItem;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuItemDAO {
    public List<MenuItem> getAll() throws Exception {
        List<MenuItem> items = new ArrayList<>();
        String sql = "SELECT id, name, price, category, image_url FROM menu_items ORDER BY " +
            "CASE category WHEN 'HOT' THEN 1 WHEN 'COLD' THEN 2 WHEN 'PASTRY' THEN 3 END, id";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                MenuItem item = new MenuItem();
                item.setId(rs.getInt("id"));
                item.setName(rs.getString("name"));
                item.setPrice(rs.getBigDecimal("price"));
                item.setCategory(rs.getString("category"));
                item.setImageUrl(rs.getString("image_url"));
                items.add(item);
            }
        }
        return items;
    }
}
