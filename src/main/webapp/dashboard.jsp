
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <%-- [CORRIGIDO] Anti-cache: página protegida não deve ser armazenada pelo browser --%>
    <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate">
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="0">
    <title>Dashboard</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<div class="container">

    <%-- [CORRIGIDO] Exibe nome do usuário vindo da sessão --%>
    <h2>Olá, ${usuarioLogado.nome}! — Minhas Tarefas</h2>

    <%-- [CORRIGIDO] Mensagens de feedback vindas do servlet via redirect+param --%>
    <c:if test="${not empty param.msg}">
        <p class="msg-sucesso">${param.msg}</p>
    </c:if>
    <c:if test="${not empty param.erro}">
        <p class="msg-erro">${param.erro}</p>
    </c:if>

    <%-- Formulário para adicionar nova tarefa --%>
    <form action="tarefa" method="post" onsubmit="return validarTarefa()">
        <%-- [CORRIGIDO] Campo "acao" era ausente — servlet precisa dele para saber o que fazer --%>
        <input type="hidden" name="acao" value="criar">
        <input type="text"   name="titulo"   id="titulo"   placeholder="Título"    required>
        <input type="text"   name="descricao" id="descricao" placeholder="Descrição">
        <button type="submit">Adicionar</button>
    </form>

    <ul>
        <%-- [CORRIGIDO] Antes: t.toString() — agora usa JSTL para acessar os campos reais do objeto Tarefa --%>
        <%-- [CORRIGIDO] Antes: lista sem proteção contra null — c:choose evita NullPointerException --%>
        <c:choose>
            <c:when test="${empty tarefas}">
                <li><em>Nenhuma tarefa cadastrada ainda.</em></li>
            </c:when>
            <c:otherwise>
                <c:forEach var="tarefa" items="${tarefas}">
                    <li class="${tarefa.concluida ? 'tarefa-concluida' : ''}">

                        <%-- Título e descrição (fn:escapeXml previne XSS) --%>
                        <strong>${fn:escapeXml(tarefa.titulo)}</strong>
                        <c:if test="${not empty tarefa.descricao}">
                            — ${fn:escapeXml(tarefa.descricao)}
                        </c:if>
                        <em>(${tarefa.concluida ? 'Concluída' : 'Pendente'})</em>

                        <%-- [CORRIGIDO] Concluir — POST com id correto, antes não havia este botão --%>
                        <form action="tarefa" method="post" style="display:inline">
                            <input type="hidden" name="acao" value="concluir">
                            <input type="hidden" name="id"   value="${tarefa.id}">
                            <button type="submit">${tarefa.concluida ? 'Reabrir' : 'Concluir'}</button>
                        </form>

                        <%-- [CORRIGIDO] Editar — antes era GET com id vazio, agora abre modal --%>
                        <button type="button"
                                onclick="abrirEdicao(${tarefa.id},
                                                     '${fn:escapeXml(tarefa.titulo)}',
                                                     '${fn:escapeXml(tarefa.descricao)}')">
                            Editar
                        </button>

                        <%-- [CORRIGIDO] Excluir — antes era GET com id vazio; agora POST com id correto --%>
                        <form action="tarefa" method="post" style="display:inline"
                              onsubmit="return confirm('Excluir esta tarefa?')">
                            <input type="hidden" name="acao" value="excluir">
                            <input type="hidden" name="id"   value="${tarefa.id}">
                            <button type="submit">Excluir</button>
                        </form>

                    </li>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </ul>

    <%-- [CORRIGIDO] Logout via POST (mais seguro que GET) --%>
    <form action="logout" method="post">
        <button type="submit">Sair</button>
    </form>
</div>

<%-- Modal de Edição --%>
<div id="modalEdicao" style="display:none; position:fixed; inset:0;
     background:rgba(0,0,0,.5); display:none; align-items:center; justify-content:center;">
    <div class="container" style="margin-top:0; width:340px;">
        <h3>Editar Tarefa</h3>
        <form action="tarefa" method="post">
            <input type="hidden" name="acao"   value="editar">
            <input type="hidden" name="id"     id="editId">
            <input type="text"   name="titulo" id="editTitulo"    placeholder="Título" required>
            <input type="text"   name="descricao" id="editDescricao" placeholder="Descrição">
            <button type="submit">Salvar</button>
            <button type="button" onclick="fecharModal()">Cancelar</button>
        </form>
    </div>
</div>

<script src="js/script.js"></script>
</body>
</html>
