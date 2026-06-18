package com.coffeeshop.controller;

import com.coffeeshop.dao.ContactMessageDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;

/**
 * Admin dashboard servlet. Displays contact messages.
 * Requires an active admin session.
 */
@WebServlet("/admin")
public final class AdminServlet extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("adminLoggedIn") == null) {
            resp.sendRedirect(req.getContextPath() + "/admin/login");
            return;
        }
        try {
            req.setAttribute("messages", new ContactMessageDAO().findAll());
        } catch (Exception e) {
            req.setAttribute("error",
                    "Could not load messages: " + e.getMessage());
        }
        req.getRequestDispatcher("/WEB-INF/admin.jsp").forward(req, resp);
    }
}
