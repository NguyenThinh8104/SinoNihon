<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.User" %>

<%
    User user = (User) session.getAttribute("user");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dashboard</title>

    <style>
        body{
            margin:0;
            font-family:'Noto Sans JP','Noto Sans SC',sans-serif;
            background:#f4f6f9;
        }

        /* ===== Layout ===== */
        .container{
            display:flex;
            min-height:100vh;
        }

        /* ===== Sidebar ===== */
        .sidebar{
            width:250px;
            background:#1f2a40;
            color:white;
            padding:20px;
        }

        .sidebar h2{
            margin-bottom:30px;
        }

        .sidebar a{
            display:block;
            color:white;
            text-decoration:none;
            padding:10px;
            margin-bottom:10px;
            border-radius:6px;
        }

        .sidebar a:hover{
            background:#32445f;
        }

        /* ===== Main Content ===== */
        .content{
            flex:1;
            padding:40px;
        }

        .card{
            background:white;
            padding:30px;
            border-radius:10px;
            box-shadow:0 5px 15px rgba(0,0,0,0.1);
        }
    </style>
</head>

<body>

<div class="container">

    <!-- ===== SIDEBAR ===== -->
    <div class="sidebar">
        <h2>SinoNihon</h2>

        <a href="<%=request.getContextPath()%>/dashboard">🏠 Dashboard</a>

        <!-- 🔥 THÊM LINK COURSES -->
        <a href="<%=request.getContextPath()%>/courses" target="mainFrame">📚 My Courses</a>

        <!-- 🔥 THÊM LINK PROGRESS -->
        <a href="<%=request.getContextPath()%>/progress" target="mainFrame">📊 My Progress</a>

        <a href="<%=request.getContextPath()%>/profile" target="mainFrame">⚙ Update Profile</a>
        <a href="<%=request.getContextPath()%>/logout">🚪 Logout</a>
    </div>

    <!-- ===== CONTENT ===== -->
    <div class="content">
        <iframe id="mainFrame"
                name="mainFrame"
                style="width:100%;height:85vh;border:none;">
        </iframe>
    </div>

</div>

</body>
</html>