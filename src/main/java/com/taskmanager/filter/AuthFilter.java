package com.taskmanager.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        String path = req.getRequestURI();
        
        // Permite acesso livre ao Login, Cadastro e arquivos de estilo (CSS/JS)
        boolean isLogin = path.endsWith("login.jsp") || path.endsWith("login");
        boolean isCadastro = path.endsWith("cadastro.jsp") || path.endsWith("cadastro");
        boolean isStatic = path.contains("/css/") || path.contains("/js/");

        boolean logado = (session != null && session.getAttribute("usuarioLogado") != null);

        if (logado || isLogin || isCadastro || isStatic) {
            chain.doFilter(request, response);
        } else {
            res.sendRedirect(req.getContextPath() + "/login.jsp");
        }
    }
}