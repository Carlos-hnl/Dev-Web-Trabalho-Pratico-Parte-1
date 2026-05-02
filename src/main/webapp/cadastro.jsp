
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Cadastro</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<div class="container">
    <h2>Cadastro</h2>

    <%-- [CORRIGIDO] Exibe erros enviados pelo CadastroServlet --%>
    <% if (request.getAttribute("erro") != null) { %>
        <p class="msg-erro"><%= request.getAttribute("erro") %></p>
    <% } %>

    <form action="cadastro" method="post" onsubmit="return validarCadastro()">
        <input type="text"    name="nome"  id="nome"  placeholder="Nome"  required>
        <input type="email"   name="email" id="email" placeholder="Email" required>
        <input type="password" name="senha" id="senha" placeholder="Senha (mín. 6 caracteres)" required>
        <button type="submit">Cadastrar</button>
    </form>

    <a href="login">Já tem conta? Faça login</a>
</div>
<script src="js/script.js"></script>
</body>
</html>
