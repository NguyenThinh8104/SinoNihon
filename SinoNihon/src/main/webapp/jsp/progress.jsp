<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.User,dao.LessonDAO" %>

<%
  User user = (User) session.getAttribute("user");
  LessonDAO dao = new LessonDAO();

  int courseID = 1;  // demo 1 khóa (bạn có thể loop nếu muốn)
  int done = dao.countCompleted(user.getUserId(), courseID);
  int total = dao.totalLesson(courseID);

  int percent = 0;
  if(total != 0){
    percent = done * 100 / total;
  }
%>

<html>
<head>
  <meta charset="UTF-8">
  <title>Progress</title>
  <style>
    .progress-box{
      width:400px;
      background:#ddd;
      border-radius:20px;
    }
    .progress-bar{
      background:#2ecc71;
      padding:10px;
      color:white;
      border-radius:20px;
    }
  </style>
</head>
<body>

<h2>Tiến độ Tiếng Trung Cơ Bản</h2>

<div class="progress-box">
  <div class="progress-bar" style="width:<%=percent%>%">
    <%=percent%>% Completed
  </div>
</div>

<p>Hoàn thành: <%=done%> / <%=total%> bài</p>

</body>
</html>