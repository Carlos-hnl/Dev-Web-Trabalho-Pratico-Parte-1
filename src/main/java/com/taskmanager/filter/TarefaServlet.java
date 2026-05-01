package com.taskmanager.filter

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/tarefa")
public class TarefaServlet extends HttpServlet {
    
    // O método POST recebe os dados do formulário (Criar, Editar, Excluir)
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String acao = request.getParameter("acao");
        
        // Aqui você integraria com o DAO da Pessoa 2
        // Exemplo de lógica para o seu trabalho:
        if ("criar".equals(acao)) {
            String titulo = request.getParameter("titulo");
            String data = request.getParameter("data");
            System.out.println("Salvando tarefa: " + titulo + " para o dia " + data);
        }
        
        // Após processar, volta para o dashboard
        response.sendRedirect("dashboard.jsp?msg=sucesso");
    }

    // O método GET busca as tarefas para exibir no calendário
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Aqui você buscaria a lista de tarefas do banco
        // request.setAttribute("tarefas", listaDeTarefas);
        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
    }
}