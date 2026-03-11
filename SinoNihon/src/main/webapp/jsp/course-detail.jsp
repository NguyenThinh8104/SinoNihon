<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*,model.Lesson" %>

<html>
<head>
  <meta charset="UTF-8">
  <title>Course Detail</title>

  <style>
    body{
      font-family:'Noto Sans JP','Noto Sans SC',sans-serif;
      background:#f4f6f9;
      margin:0;
    }

    .wrapper{
      max-width:900px;
      margin:40px auto;
    }

    .card{
      background:white;
      padding:25px;
      border-radius:12px;
      box-shadow:0 5px 20px rgba(0,0,0,0.1);
    }

    .lesson-item{
      padding:15px;
      border-bottom:1px solid #eee;
      display:flex;
      justify-content:space-between;
      align-items:center;
    }

    .lesson-item:last-child{
      border-bottom:none;
    }

    .lesson-item a{
      text-decoration:none;
      font-size:18px;
      color:#333;
    }

    .lesson-item a:hover{
      color:#3498db;
    }

    .back{
      margin-top:20px;
      display:inline-block;
      text-decoration:none;
      color:white;
      background:#3498db;
      padding:8px 15px;
      border-radius:6px;
    }
  </style>
</head>

<body>

<div class="wrapper">
  <div class="card">
    <h2>📚 Danh sách bài học</h2>

    <%
      List<Lesson> list = (List<Lesson>) request.getAttribute("lessonList");
      for(Lesson l : list){
    %>

    <div class="lesson-item">
      <div>
        📘 <%=l.getTitle()%>
      </div>

      <div>
        <a href="lesson?id=<%=l.getLessonID()%>&courseID=<%=request.getParameter("id")%>">
          Xem bài
        </a>
      </div>
    </div>

    <% } %>

    <a class="back" href="courses">⬅ Quay lại khóa học</a>

  </div>
</div>

</body>
</html>