package com.coffeeshop.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet({"/admin/login", "/admin/logout"})
public class AdminLoginServlet extends HttpServlet {
    private static final String ADMIN_USER = "admin";
    private static final String ADMIN_PASS = "admin123";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String path = req.getServletPath();
        if ("/admin/logout".equals(path)) {
            doLogout(req, resp);
            return;
        }
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("adminLoggedIn") != null) {
            resp.sendRedirect(req.getContextPath() + "/admin");
            return;
        }
        req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String path = req.getServletPath();
        if ("/admin/logout".equals(path)) {
            doLogout(req, resp);
            return;
        }
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (ADMIN_USER.equals(username) && ADMIN_PASS.equals(password)) {
            req.getSession().setAttribute("adminLoggedIn", true);
            resp.sendRedirect(req.getContextPath() + "/admin");
        } else {
            req.setAttribute("loginError", "Invalid username or password.");
            req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);
        }
    }

    private void doLogout(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        resp.sendRedirect(req.getContextPath() + "/admin/login");
    }
}
