package com.coffeeshop.controller;

import com.coffeeshop.dao.MenuItemDAO;
import com.coffeeshop.dao.TestimonialDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;

/**
 * Handles requests to the home page. Loads menu items and testimonials
 * from the database and forwards to index.jsp.
 */
@WebServlet("")
public final class HomeServlet extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            req.setAttribute("menuItems", new MenuItemDAO().getAll());
            req.setAttribute("testimonials", new TestimonialDAO().getAll());
        } catch (Exception e) {
            throw new ServletException("Failed to load data", e);
        }
        req.getRequestDispatcher("/WEB-INF/index.jsp").forward(req, resp);
    }
}
