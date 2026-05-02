
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<div class="container">
    <h2>Login</h2>

    <%-- [CORRIGIDO] Exibe mensagem de sucesso após cadastro --%>
    <% if ("ok".equals(request.getParameter("cadastro"))) { %>
        <p class="msg-sucesso">Conta criada com sucesso! Faça login.</p>
    <% } %>

    <%-- [CORRIGIDO] Exibe mensagem após logout --%>
    <% if ("ok".equals(request.getParameter("logout"))) { %>
        <p class="msg-info">Você saiu da sua conta.</p>
    <% } %>

    <%-- [CORRIGIDO] Exibe erros enviados pelo LoginServlet --%>
    <% if (request.getAttribute("erro") != null) { %>
        <p class="msg-erro"><%= request.getAttribute("erro") %></p>
    <% } %>

    <form action="login" method="post" onsubmit="return validarLogin()">

        <%-- [CORRIGIDO] Pré-preenche e-mail salvo no cookie "lembrarEmail" --%>
        <input type="email"
               name="email"
               id="email"
               placeholder="Email"
               value="<%= request.getAttribute("emailSalvo") != null ? request.getAttribute("emailSalvo") : "" %>"
               required>

        <input type="password" name="senha" id="senha" placeholder="Senha" required>

        <label>
            <input type="checkbox" name="lembrar"> Lembrar login
        </label>

        <button type="submit">Entrar</button>
    </form>

    <%-- [CORRIGIDO] Era "cadastro.jsp" — agora aponta para o servlet /cadastro --%>
    <a href="cadastro">Criar conta</a>
</div>
<script src="js/script.js"></script>
</body>
</html>
