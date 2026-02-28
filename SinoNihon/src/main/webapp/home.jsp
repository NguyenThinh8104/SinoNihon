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
        <a href="jsp/register.jsp">Start Learning</a>
    </div>

    <div class="course-box">
        <h2>🇯🇵 Japanese</h2>
        <p>Master Hiragana, Katakana & Kanji step by step.</p>
        <a href="jsp/register.jsp">Start Learning</a>
    </div>
</section>

<footer>
    © 2026 SinoNihon. All rights reserved.
</footer>

</body>
</html>
