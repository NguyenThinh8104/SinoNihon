<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>SinoNihon – Learn Chinese & Japanese</title>
    <style>
        body {
            margin: 0;
            font-family: Arial, Helvetica, sans-serif;
            background-color: #f8f9fa;
            color: #333;
        }

        /* HEADER */
        header {
            background-color: #ffffff;
            padding: 15px 40px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            border-bottom: 1px solid #e0e0e0;
        }

        .logo {
            font-size: 24px;
            font-weight: bold;
            color: #2c3e50;
        }

        nav a {
            margin-left: 20px;
            text-decoration: none;
            color: #2c3e50;
            font-weight: 500;
        }

        nav a.button {
            padding: 8px 16px;
            border-radius: 5px;
            background-color: #3498db;
            color: white;
        }

        /* HERO */
        .hero {
            text-align: center;
            padding: 80px 20px;
            background: linear-gradient(135deg, #eaf2f8, #ffffff);
        }

        .hero h1 {
            font-size: 38px;
            margin-bottom: 10px;
        }

        .hero p {
            font-size: 18px;
            color: #555;
        }

        /* COURSES */
        .courses {
            display: flex;
            justify-content: center;
            gap: 40px;
            padding: 60px 20px;
        }

        .course-box {
            background-color: #ffffff;
            width: 300px;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 5px 15px rgba(0,0,0,0.08);
            text-align: center;
        }

        .course-box h2 {
            margin-bottom: 10px;
        }

        .course-box p {
            color: #666;
        }

        .course-box a {
            display: inline-block;
            margin-top: 15px;
            padding: 10px 18px;
            background-color: #27ae60;
            color: white;
            border-radius: 6px;
            text-decoration: none;
        }

        /* FOOTER */
        footer {
            text-align: center;
            padding: 15px;
            background-color: #ffffff;
            border-top: 1px solid #e0e0e0;
            color: #777;
        }
    </style>
</head>
<body>

<jsp:include page="/jsp/header.jsp"/>


<section class="hero">
    <h1>Learn Chinese & Japanese Online</h1>
    <p>Simple – Effective – Beginner Friendly</p>
</section>

<section class="courses">
    <div class="course-box">
        <h2>🇨🇳 Chinese</h2>
        <p>Learn Mandarin from basic to advanced with clear lessons.</p>
        <a href="jsp/login.jsp">Start Learning</a>
    </div>

    <div class="course-box">
        <h2>🇯🇵 Japanese</h2>
        <p>Master Hiragana, Katakana & Kanji step by step.</p>
        <a href="jsp/login.jsp">Start Learning</a>
    </div>

    <div class="course-box">
        <h2>🚀Entrance Test</h2>
        <p>Take the entrance test now!</p>
        <a href="jsp/login.jsp">Take Test</a>
    </div>
</section>

<footer>
    © 2026 SinoNihon. All rights reserved.
</footer>

<style>
    .ai-widget {
        position: fixed;
        bottom: 30px;
        right: 30px;
        z-index: 1000;
        font-family: Arial, Helvetica, sans-serif;
    }
    .ai-btn {
        width: 60px;
        height: 60px;
        background-color: #3498db;
        color: white;
        border-radius: 50%;
        display: flex;
        justify-content: center;
        align-items: center;
        font-size: 30px;
        cursor: pointer;
        box-shadow: 0 4px 15px rgba(0,0,0,0.2);
        transition: transform 0.3s;
    }
    .ai-btn:hover {
        transform: scale(1.1);
    }
    .ai-popup {
        display: none;
        position: absolute;
        bottom: 80px;
        right: 0;
        width: 300px;
        background: white;
        border-radius: 10px;
        box-shadow: 0 5px 20px rgba(0,0,0,0.15);
        overflow: hidden;
        animation: slideUp 0.3s ease;
    }
    .ai-popup-header {
        background: #1f2a40;
        color: white;
        padding: 15px;
        font-weight: bold;
        display: flex;
        justify-content: space-between;
        align-items: center;
    }
    .ai-popup-close {
        cursor: pointer;
        font-size: 18px;
    }
    .ai-popup-body {
        padding: 20px;
        text-align: center;
        color: #555;
        font-size: 15px;
        line-height: 1.5;
    }
    .ai-popup-body a {
        display: inline-block;
        margin-top: 15px;
        padding: 10px 20px;
        background: #27ae60;
        color: white;
        text-decoration: none;
        border-radius: 6px;
        font-weight: bold;
    }
    @keyframes slideUp {
        from { opacity: 0; transform: translateY(20px); }
        to { opacity: 1; transform: translateY(0); }
    }
</style>

<div class="ai-widget">
    <div class="ai-popup" id="aiPopup">
        <div class="ai-popup-header">
            <span>🤖 SinoNihon AI</span>
            <span class="ai-popup-close" onclick="toggleAIPopup()">✖</span>
        </div>
        <div class="ai-popup-body">
            Bạn cần tư vấn lộ trình học hay sửa lỗi ngữ pháp? Trợ lý AI của chúng tôi luôn sẵn sàng hỗ trợ bạn 24/7!
            <br>
            <a href="<%= request.getContextPath() %>/ai-assistant?feature=LEARNING_PATH">Trải nghiệm ngay</a>
        </div>
    </div>
    <div class="ai-btn" onclick="toggleAIPopup()">🤖</div>
</div>

<script>
    function toggleAIPopup() {
        const popup = document.getElementById('aiPopup');
        popup.style.display = (popup.style.display === 'block') ? 'none' : 'block';
    }

    // Tự động mở popup sau 3 giây để thu hút sự chú ý
    setTimeout(() => {
        const popup = document.getElementById('aiPopup');
        if(popup.style.display !== 'block') {
            popup.style.display = 'block';
        }
    }, 3000);
</script>
</body>
</html>