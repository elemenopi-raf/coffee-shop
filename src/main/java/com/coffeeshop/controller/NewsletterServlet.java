package com.coffeeshop.controller;

import com.coffeeshop.dao.NewsletterDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;

/**
 * Handles POST requests from the newsletter sign-up form.
 */
@WebServlet("/newsletter")
public final class NewsletterServlet extends HttpServlet {

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp)
            throws ServletException, IOException {
        String email = req.getParameter("email");

        if (email == null || email.trim().isEmpty() || !email.contains("@")) {
            resp.sendRedirect(req.getContextPath() + "/");
            return;
        }

        try {
            NewsletterDAO dao = new NewsletterDAO();
            if (dao.exists(email.trim())) {
                req.getSession().setAttribute("newsletterMsg",
                        "You're already subscribed!");
            } else {
                dao.save(email.trim());
                req.getSession().setAttribute("newsletterMsg",
                        "Thanks for subscribing!");
            }
        } catch (Exception e) {
            req.getSession().setAttribute("newsletterMsg",
                    "Something went wrong. Try again.");
        }

        resp.sendRedirect(req.getContextPath() + "/");
    }
}
