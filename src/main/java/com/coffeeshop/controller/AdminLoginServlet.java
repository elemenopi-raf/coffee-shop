package com.coffeeshop.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;

/**
 * Handles admin login and logout requests.
 */
@WebServlet({"/admin/login", "/admin/logout"})
public final class AdminLoginServlet extends HttpServlet {

    /** The admin username. */
    private static final String ADMIN_USER = "admin";

    /** The admin password. */
    private static final String ADMIN_PASS = "admin123";

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp)
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
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp)
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

    /**
     * Invalidates the session and redirects to the login page.
     *
     * @param req the HTTP request
     * @param resp the HTTP response
     * @throws IOException if an I/O error occurs
     */
    private void doLogout(final HttpServletRequest req, final HttpServletResponse resp)
            throws IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        resp.sendRedirect(req.getContextPath() + "/admin/login");
    }
}
