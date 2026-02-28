<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.User" %>

<%
    User u = (User) request.getAttribute("user");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Update Profile | SinoNihon</title>
    <style>
        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background: linear-gradient(135deg, #eaf2f8, #ffffff);
            min-height: 100vh;
        }

        .container {
            max-width: 520px;
            margin: 50px auto;
            background: #ffffff;
            padding: 35px;
            border-radius: 12px;
            box-shadow: 0 12px 30px rgba(0,0,0,0.1);
        }

        h2 {
            text-align: center;
            margin-bottom: 25px;
        }

        label {
            display: block;
            margin-top: 14px;
        }

        input {
            width: 100%;
            padding: 10px;
            margin-top: 6px;
            border-radius: 6px;
            border: 1px solid #ccc;
        }

        button {
            width: 100%;
            margin-top: 18px;
            padding: 12px;
            border: none;
            border-radius: 8px;
            background: #3498db;
            color: white;
            font-size: 16px;
            cursor: pointer;
        }

        .secondary {
            background: #2ecc71;
        }

        .success {
            text-align: center;
            color: #27ae60;
            margin-top: 12px;
        }
    </style>
</head>
<body>

<jsp:include page="/jsp/header.jsp"/>

<div class="container">
    <h2>Update Profile</h2>

    <form method="post">
        <label>Username</label>
        <input value="<%= u.getUsername() %>" disabled>

        <label>Full name</label>
        <input name="fullname" value="<%= u.getFullName() %>" required>

        <label>Phone</label>
        <input name="phone" value="<%= u.getPhone() %>" required>

        <label>Email</label>
        <input name="email" value="<%= u.getEmail() %>" required>

        <button>Save profile</button>
    </form>

    <% if (request.getAttribute("success") != null) { %>
    <div class="success">Profile updated successfully</div>
    <% } %>

    <!-- CHANGE PASSWORD BUTTON -->
    <form action="${pageContext.request.contextPath}/change-password" method="get">
        <button class="secondary">Change Password</button>
    </form>
</div>

</body>
</html>
