package com.demowebapp.servlets;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class HelloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/jsp/index.jsp");
        requestDispatcher.forward(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String welcome = null;
        String name = req.getParameter("name");
        if ("".equals(name.trim())) {
            welcome = "World";
        } else {
            welcome = name.trim().toUpperCase();
        }
        HttpSession session = req.getSession();
        session.setAttribute("welcome", welcome);
        res.sendRedirect(this.getServletContext().getContextPath() + "/hello");
    }

}
