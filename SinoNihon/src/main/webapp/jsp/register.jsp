<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Register | SinoNihon</title>
    <%
        String error = request.getParameter("error");
        if ("duplicate".equals(error)) {
    %>
    <p style="color:red;">⚠ Email hoặc Username đã tồn tại!</p>
    <%
        }
        if ("system".equals(error)) {
    %>
    <p style="color:red;">⚠ Lỗi hệ thống, vui lòng thử lại!</p>
    <%
        }
    %>
    <style>
        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background: linear-gradient(135deg, #ffffff, #eaf2f8);
            height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .box {
            background: #fff;
            width: 380px;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 10px 25px rgba(0,0,0,0.1);
        }

        h2 {
            text-align: center;
            margin-bottom: 20px;
            color: #2c3e50;
        }

        input {
            width: 100%;
            padding: 10px;
            margin-top: 12px;
            border: 1px solid #ccc;
            border-radius: 6px;
            font-size: 14px;
        }

        button {
            width: 100%;
            padding: 10px;
            margin-top: 20px;
            border: none;
            border-radius: 6px;
            background-color: #27ae60;
            color: white;
            font-size: 16px;
            cursor: pointer;
        }

        button:hover {
            background-color: #219150;
        }

        .link {
            text-align: center;
            margin-top: 15px;
            font-size: 14px;
        }

        .link a {
            color: #3498db;
            text-decoration: none;
        }
    </style>
</head>
<body>

<div class="box">
    <h2>Create Account</h2>

    <form action="${pageContext.request.contextPath}/auth" method="post">
        <input type="hidden" name="action" value="register"/>

        <input name="username" placeholder="Username" required />
        <input name="password" type="password" placeholder="Password" required />
        <input name="fullname" placeholder="Full name" required />
        <input name="phone" placeholder="Phone" required />
        <input name="email" type="email" placeholder="Email" required />

        <button>Register</button>
    </form>


    <div class="link">
        Already have an account?
        <a href="login.jsp">Login</a>
    </div>
</div>

</body>
</html>
