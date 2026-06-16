package com.coffeeshop.controller;

import com.coffeeshop.dao.MenuItemDAO;
import com.coffeeshop.dao.TestimonialDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet("")
public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
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
