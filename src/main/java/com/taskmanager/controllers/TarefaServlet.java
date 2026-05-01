package com.taskmanager.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/tarefa")
public class TarefaServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String acao = request.getParameter("acao");
        
        if ("criar".equals(acao)) {
            // Lógica para salvar nova tarefa no calendário
            String titulo = request.getParameter("titulo");
            String data = request.getParameter("data");
            System.out.println("Tarefa agendada: " + titulo + " para " + data);
        }
        
        response.sendRedirect("dashboard.jsp?msg=sucesso");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Encaminha para o dashboard exibindo as tarefas existentes
        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
    }
}