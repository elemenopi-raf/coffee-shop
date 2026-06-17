package com.coffeeshop.controller;

import com.coffeeshop.dao.NewsletterDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/newsletter")
public class NewsletterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String email = req.getParameter("email");

        if (email == null || email.trim().isEmpty() || !email.contains("@")) {
            resp.sendRedirect(req.getContextPath() + "/");
            return;
        }

        try {
            NewsletterDAO dao = new NewsletterDAO();
            if (dao.exists(email.trim())) {
                req.getSession().setAttribute("newsletterMsg", "You're already subscribed!");
            } else {
                dao.save(email.trim());
                req.getSession().setAttribute("newsletterMsg", "Thanks for subscribing!");
            }
        } catch (Exception e) {
            req.getSession().setAttribute("newsletterMsg", "Something went wrong. Try again.");
        }

        resp.sendRedirect(req.getContextPath() + "/");
    }
}
