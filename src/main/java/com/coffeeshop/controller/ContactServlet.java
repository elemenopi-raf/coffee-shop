package com.coffeeshop.controller;

import com.coffeeshop.dao.ContactMessageDAO;
import com.coffeeshop.model.ContactMessage;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;

/**
 * Handles POST requests from the contact form. Saves the contact
 * message and responds with JSON for AJAX requests or a redirect
 * with flash message for regular form submissions.
 */
@WebServlet("/contact")
public final class ContactServlet extends HttpServlet {

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp)
            throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String message = req.getParameter("message");
        boolean isAjax = "XMLHttpRequest".equals(req.getHeader("X-Requested-With"));

        if (name == null || email == null || message == null
                || name.trim().isEmpty()
                || email.trim().isEmpty()
                || message.trim().isEmpty()) {
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
                writeJson(resp, "success",
                        "Thanks, " + name.trim() + "! We'll get back to you soon.");
            } else {
                req.getSession().setAttribute("formMsg",
                        "Thanks, " + name.trim() + "! We'll get back to you soon.");
                req.getSession().setAttribute("formMsgType", "success");
                resp.sendRedirect(req.getContextPath() + "/");
            }
        } catch (Exception e) {
            if (isAjax) {
                writeJson(resp, "error", "Something went wrong. Please try again.");
            } else {
                req.getSession().setAttribute("formMsg",
                        "Something went wrong. Please try again.");
                req.getSession().setAttribute("formMsgType", "error");
                resp.sendRedirect(req.getContextPath() + "/");
            }
        }
    }

    /**
     * Writes a JSON response for AJAX requests.
     *
     * @param resp    the HTTP response
     * @param type    the response type (success or error)
     * @param msg     the response message
     * @throws IOException if an I/O error occurs
     */
    private void writeJson(final HttpServletResponse resp, final String type, final String msg)
            throws IOException {
        resp.setContentType("application/json");
        resp.getWriter().write("{\"type\":\"" + type + "\",\"message\":\"" + msg + "\"}");
    }
}
