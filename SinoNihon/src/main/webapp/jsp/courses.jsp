<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*, model.Course" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Courses</title>
    <style>
        body { font-family: 'Noto Sans JP', 'Noto Sans SC', sans-serif; }
        .course-card {
            border:1px solid #ddd;
            padding:20px;
            margin:20px;
            border-radius:10px;
        }
    </style>
</head>
<body>

<h2>Danh sách khóa học</h2>

<%
    List<Course> list = (List<Course>) request.getAttribute("courses");
    for(Course c : list){
%>
<div class="course-card">
    <h3><%=c.getTitle()%></h3>
    <p><%=c.getDescription()%></p>
    <a href="course-detail?id=<%=c.getCourseID()%>">Xem khóa học</a>
</div>
<% } %>

</body>
</html>