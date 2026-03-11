<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*, model.Vocabulary" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Lesson</title>
    <style>
        body{
            font-family: 'Noto Sans JP','Noto Sans SC',sans-serif;
        }
        .vocab-card{
            border:1px solid #ccc;
            padding:15px;
            margin:15px;
            border-radius:8px;
        }
        .word{
            font-size:22px;
        }
    </style>
</head>
<body>

<h2>Vocabulary</h2>

<%
    List<Vocabulary> list = (List<Vocabulary>) request.getAttribute("vocabList");
    for(Vocabulary v : list){
%>

<div class="vocab-card">
    <div class="word"><%=v.getWord()%></div>
    <div>Pronunciation: <%=v.getPronunciation()%></div>
    <div>Meaning: <%=v.getMeaning()%></div>
</div>
<hr>

<form method="post" action="lesson">
    <input type="hidden" name="lessonID"
           value="<%=request.getAttribute("lessonID")%>">

    <input type="hidden" name="courseID"
           value="<%=request.getParameter("courseID")%>">

    <button type="submit"
            style="padding:10px 20px;
                   background:#27ae60;
                   color:white;
                   border:none;
                   border-radius:8px;
                   cursor:pointer;">
        ✅ Hoàn thành bài học
    </button>
</form>
<% } %>

</body>
</html>