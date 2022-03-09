package com.demowebapp.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.demowebapp.model.Result;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class IndovinaNumeroServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(IndovinaNumeroServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session.getAttribute("number") == null) {
            Integer num = (int) ((Math.random() * 101) + 100);
            session.setAttribute("number", num);
            session.setAttribute("results", new ArrayList<Result>());
            session.setAttribute("found", Boolean.FALSE);
            log.info("New random number: {}", num);
        }
        req.getRequestDispatcher("/WEB-INF/jsp/indovinaNumero.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (req.getParameter("reset") != null) {
            session.removeAttribute("number");
        } else {
            Integer guess = Integer.parseInt(req.getParameter("number"));
            Integer number = (Integer) session.getAttribute("number");
            List<Result> results = (List<Result>) session.getAttribute("results");
            Result newResult = new Result(guess, guess.compareTo(number));
            results.add(newResult);
            if (newResult.getComparaison() == 0) {
                session.setAttribute("found", Boolean.TRUE);
            }
        }
        resp.sendRedirect(this.getServletContext().getContextPath() + "/indovina");
    }

}
