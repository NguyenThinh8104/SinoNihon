<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <title>Change Password | SinoNihon</title>
    <style>
        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background: linear-gradient(135deg, #ffffff, #eaf2f8);
            height: 100vh;
        }

        .box {
            max-width: 420px;
            margin: 80px auto;
            background: #fff;
            padding: 35px;
            border-radius: 12px;
            box-shadow: 0 10px 25px rgba(0,0,0,0.1);
        }

        h2 {
            text-align: center;
            margin-bottom: 25px;
        }

        input {
            width: 100%;
            padding: 10px;
            margin-top: 14px;
            border-radius: 6px;
            border: 1px solid #ccc;
        }

        button {
            width: 100%;
            margin-top: 20px;
            padding: 12px;
            border: none;
            border-radius: 8px;
            background: #3498db;
            color: white;
            font-size: 16px;
        }

        .success { color: #27ae60; text-align: center; margin-top: 10px; }
        .error { color: #e74c3c; text-align: center; margin-top: 10px; }
    </style>
</head>
<body>

<jsp:include page="/jsp/header.jsp"/>

<div class="box">
    <h2>Change Password</h2>

    <form method="post">
        <input type="password" name="oldPassword" placeholder="Old password" required>
        <input type="password" name="newPassword" placeholder="New password" required>
        <input type="password" name="confirmPassword" placeholder="Confirm new password" required>
        <button>Change Password</button>
    </form>

    <% if (request.getAttribute("success") != null) { %>
    <div class="success">Password changed successfully</div>
    <% } %>

    <% if (request.getAttribute("error") != null) { %>
    <div class="error"><%= request.getAttribute("error") %></div>
    <% } %>
</div>

</body>
</html>
