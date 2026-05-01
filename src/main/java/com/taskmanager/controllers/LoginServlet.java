package com.taskmanager.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        // Validação das credenciais
        if ("admin@email.com".equals(email) && "123456".equals(senha)) {
            HttpSession session = request.getSession();
            session.setAttribute("usuarioLogado", email);
            response.sendRedirect("dashboard.jsp");
        } else {
            request.setAttribute("erro", "E-mail ou senha incorretos.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}