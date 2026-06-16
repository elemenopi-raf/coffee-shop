package com.coffeeshop.controller;

import com.coffeeshop.dao.ContactMessageDAO;
import com.coffeeshop.model.ContactMessage;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/contact")
public class ContactServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String message = req.getParameter("message");

        if (name == null || email == null || message == null ||
            name.trim().isEmpty() || email.trim().isEmpty() || message.trim().isEmpty()) {
            req.getSession().setAttribute("formMsg", "Please fill in all fields.");
            req.getSession().setAttribute("formMsgType", "error");
            resp.sendRedirect(req.getContextPath() + "/");
            return;
        }

        if (!email.contains("@")) {
            req.getSession().setAttribute("formMsg", "Please enter a valid email.");
            req.getSession().setAttribute("formMsgType", "error");
            resp.sendRedirect(req.getContextPath() + "/");
            return;
        }

        ContactMessage cm = new ContactMessage();
        cm.setName(name.trim());
        cm.setEmail(email.trim());
        cm.setMessage(message.trim());

        try {
            new ContactMessageDAO().save(cm);
            req.getSession().setAttribute("formMsg", "Thanks, " + name.trim() + "! We'll get back to you soon.");
            req.getSession().setAttribute("formMsgType", "success");
        } catch (Exception e) {
            req.getSession().setAttribute("formMsg", "Something went wrong. Please try again.");
            req.getSession().setAttribute("formMsgType", "error");
        }

        resp.sendRedirect(req.getContextPath() + "/");
    }
}
