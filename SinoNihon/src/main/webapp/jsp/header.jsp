<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.User" %>

<%
    User user = (User) session.getAttribute("user");
%>

<style>
    /* ===== HEADER ===== */
    .header {
        background-color: #ffffff;
        padding: 15px 40px;
        display: flex;
        justify-content: space-between;
        align-items: center;
        border-bottom: 1px solid #e0e0e0;
        position: sticky;
        top: 0;
        z-index: 1000;
    }

    .logo {
        font-size: 24px;
        font-weight: bold;
        color: #2c3e50;
        letter-spacing: 0.5px;
    }

    .nav {
        display: flex;
        align-items: center;
        gap: 20px;
    }

    .nav a {
        text-decoration: none;
        color: #2c3e50;
        font-weight: 500;
        font-size: 15px;
        transition: color 0.2s ease;
    }

    .nav a:hover {
        color: #3498db;
    }

    .nav .btn {
        padding: 8px 16px;
        border-radius: 6px;
        background-color: #3498db;
        color: #ffffff;
    }

    .nav .btn:hover {
        background-color: #2980b9;
        color: #ffffff;
    }

    /* ===== USER DROPDOWN ===== */
    .user-box {
        position: relative;
        display: flex;
        align-items: center;
        gap: 6px;
        cursor: pointer;
        font-weight: 500;
        color: #2c3e50;
        user-select: none;
    }

    .user-box:hover {
        color: #3498db;
    }

    .avatar {
        width: 34px;
        height: 34px;
        border-radius: 50%;
        background: #ecf0f1;
        display: flex;
        align-items: center;
        justify-content: center;
        font-weight: bold;
        color: #2c3e50;
    }

    .dropdown {
        display: none;
        position: absolute;
        right: 0;
        top: 48px;
        background: #ffffff;
        border-radius: 10px;
        box-shadow: 0 8px 25px rgba(0,0,0,0.15);
        width: 190px;
        overflow: hidden;
        animation: fadeIn 0.2s ease;
    }

    .dropdown a {
        display: block;
        padding: 12px 16px;
        text-decoration: none;
        color: #333;
        font-size: 14px;
    }

    .dropdown a:hover {
        background-color: #f4f6f8;
    }

    .dropdown a.logout {
        color: #e74c3c;
        font-weight: 500;
    }

    @keyframes fadeIn {
        from { opacity: 0; transform: translateY(-5px); }
        to { opacity: 1; transform: translateY(0); }
    }
</style>

<header class="header">
    <div class="logo">SinoNihon</div>

    <nav class="nav">
        <a href="<%= request.getContextPath() %>/dashboard">Dashboard</a>

        <% if (user == null) { %>
        <!-- CHƯA LOGIN -->
        <a href="<%= request.getContextPath() %>/jsp/login.jsp">Login</a>
        <a href="<%= request.getContextPath() %>/jsp/register.jsp" class="btn">Register</a>
        <% } else { %>
        <!-- ĐÃ LOGIN -->
        <div class="user-box" onclick="toggleDropdown()">
            <div class="avatar">
                <%= user.getFullName().substring(0,1).toUpperCase() %>
            </div>
            <span><%= user.getFullName() %></span>

            <div class="dropdown" id="dropdown">
                <a href="<%= request.getContextPath() %>/profile">Update profile</a>
                <a href="<%= request.getContextPath() %>/logout" class="logout">Logout</a>
            </div>
        </div>
        <% } %>
    </nav>
</header>

<script>
    function toggleDropdown() {
        const d = document.getElementById("dropdown");
        d.style.display = (d.style.display === "block") ? "none" : "block";
    }

    window.addEventListener("click", function (e) {
        if (!e.target.closest(".user-box")) {
            const d = document.getElementById("dropdown");
            if (d) d.style.display = "none";
        }
    });
</script>
