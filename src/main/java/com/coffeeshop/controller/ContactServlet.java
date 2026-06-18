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
        boolean isAjax = "XMLHttpRequest".equals(req.getHeader("X-Requested-With"));

        if (name == null || email == null || message == null ||
            name.trim().isEmpty() || email.trim().isEmpty() || message.trim().isEmpty()) {
            if (isAjax) {
                writeJson(resp, "error", "Please fill in all fields.");
            } else {
                req.getSession().setAttribute("formMsg", "Please fill in all fields.");
                req.getSession().setAttribute("formMsgType", "error");
                resp.sendRedirect(req.getContextPath() + "/");
            }
            return;
        }

        if (!email.contains("@")) {
            if (isAjax) {
                writeJson(resp, "error", "Please enter a valid email.");
            } else {
                req.getSession().setAttribute("formMsg", "Please enter a valid email.");
                req.getSession().setAttribute("formMsgType", "error");
                resp.sendRedirect(req.getContextPath() + "/");
            }
            return;
        }

        ContactMessage cm = new ContactMessage();
        cm.setName(name.trim());
        cm.setEmail(email.trim());
        cm.setMessage(message.trim());

        try {
            new ContactMessageDAO().save(cm);
            if (isAjax) {
                writeJson(resp, "success", "Thanks, " + name.trim() + "! We'll get back to you soon.");
            } else {
                req.getSession().setAttribute("formMsg", "Thanks, " + name.trim() + "! We'll get back to you soon.");
                req.getSession().setAttribute("formMsgType", "success");
                resp.sendRedirect(req.getContextPath() + "/");
            }
        } catch (Exception e) {
            if (isAjax) {
                writeJson(resp, "error", "Something went wrong. Please try again.");
            } else {
                req.getSession().setAttribute("formMsg", "Something went wrong. Please try again.");
                req.getSession().setAttribute("formMsgType", "error");
                resp.sendRedirect(req.getContextPath() + "/");
            }
        }
    }

    private void writeJson(HttpServletResponse resp, String type, String message) throws IOException {
        resp.setContentType("application/json");
        resp.getWriter().write("{\"type\":\"" + type + "\",\"message\":\"" + message + "\"}");
    }
}
