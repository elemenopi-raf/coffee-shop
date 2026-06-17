package com.coffeeshop.controller;

import com.coffeeshop.dao.ContactMessageDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            req.setAttribute("messages", new ContactMessageDAO().findAll());
        } catch (Exception e) {
            req.setAttribute("error", "Could not load messages: " + e.getMessage());
        }
        req.getRequestDispatcher("/WEB-INF/admin.jsp").forward(req, resp);
    }
}