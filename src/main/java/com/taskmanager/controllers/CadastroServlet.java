package com.taskmanager.filter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/cadastro")
public class CadastroServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Coleta os dados do formulário de cadastro.jsp
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        // Aqui, na prática, você chamaria o seu DAO para salvar no banco de dados
        // Exemplo de lógica de sucesso:
        if (nome != null && email != null && senha != null) {
            // Após cadastrar, redireciona para o login informando que deu certo
            response.sendRedirect("login.jsp?cadastro=ok");
        } else {
            request.setAttribute("erro", "Erro ao realizar cadastro. Verifique os campos.");
            request.getRequestDispatcher("cadastro.jsp").forward(request, response);
        }
    }
}