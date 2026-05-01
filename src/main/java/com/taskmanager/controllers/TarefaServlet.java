package com.taskmanager.controllers;

import com.taskmanager.dao.TarefaDAO;
import com.taskmanager.model.Tarefa;
import com.taskmanager.model.Usuario;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Servlet responsável por todo o CRUD de Tarefas.
 * Camada: Controller (MVC)
 *
 * GET  /dashboard          → lista tarefas do usuário logado
 * POST /tarefa?acao=criar   → cria nova tarefa
 * POST /tarefa?acao=editar  → atualiza tarefa existente
 * POST /tarefa?acao=concluir→ alterna status de conclusão
 * POST /tarefa?acao=excluir → exclui tarefa
 */
@WebServlet({"/dashboard", "/tarefa"})
public class TarefaServlet extends HttpServlet {

    private final TarefaDAO tarefaDAO = new TarefaDAO();

    // ── GET: exibir dashboard com lista de tarefas ─────────────
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Cabeçalhos para evitar cache em páginas protegidas
        resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        resp.setHeader("Pragma", "no-cache");
        resp.setDateHeader("Expires", 0);

        Usuario usuario = getUsuarioSessao(req, resp);
        if (usuario == null) return; // AuthFilter já redireciona

        try {
            List<Tarefa> tarefas = tarefaDAO.listarPorUsuario(usuario.getId());
            req.setAttribute("tarefas", tarefas);
            req.setAttribute("usuario", usuario);

            // Contadores para o dashboard
            long concluidas = tarefas.stream().filter(Tarefa::isConcluida).count();
            req.setAttribute("totalTarefas",    tarefas.size());
            req.setAttribute("tarefasConcluidas", concluidas);
            req.setAttribute("tarefasPendentes", tarefas.size() - concluidas);

            req.getRequestDispatcher("/dashboard.jsp").forward(req, resp);

        } catch (SQLException e) {
            getServletContext().log("Erro ao listar tarefas", e);
            req.setAttribute("erro", "Erro ao carregar tarefas.");
            req.getRequestDispatcher("/dashboard.jsp").forward(req, resp);
        }
    }

    // ── POST: processar ações CRUD ─────────────────────────────
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        Usuario usuario = getUsuarioSessao(req, resp);
        if (usuario == null) return;

        String acao = req.getParameter("acao");

        try {
            switch (acao == null ? "" : acao) {

                case "criar" -> {
                    String titulo    = req.getParameter("titulo");
                    String descricao = req.getParameter("descricao");

                    if (titulo == null || titulo.isBlank()) {
                        redirectComMensagem(req, resp, null, "Título é obrigatório.");
                        return;
                    }

                    Tarefa nova = new Tarefa(usuario.getId(), titulo, descricao);
                    tarefaDAO.criar(nova);
                    redirectComMensagem(req, resp, "Tarefa criada com sucesso!", null);
                }

                case "editar" -> {
                    int    id        = Integer.parseInt(req.getParameter("id"));
                    String titulo    = req.getParameter("titulo");
                    String descricao = req.getParameter("descricao");

                    if (titulo == null || titulo.isBlank()) {
                        redirectComMensagem(req, resp, null, "Título é obrigatório.");
                        return;
                    }

... (63 linhas)