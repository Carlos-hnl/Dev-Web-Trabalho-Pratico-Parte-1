package com.taskmanager.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/cadastro")
public class CadastroServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        if (nome != null && email != null && senha != null) {
            // Após o cadastro bem-sucedido, redireciona com parâmetro de sucesso
            response.sendRedirect("login.jsp?cadastro=ok");
        } else {
            request.setAttribute("erro", "Preencha todos os campos corretamente.");
            request.getRequestDispatcher("cadastro.jsp").forward(request, response);
        }
    }
}