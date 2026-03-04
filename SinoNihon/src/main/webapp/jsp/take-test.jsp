<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List, model.Question" %>
<!DOCTYPE html>
<html>
<head>
  <title>Làm bài kiểm tra | SinoNihon</title>
  <style>
    body { font-family: 'Noto Sans JP', sans-serif; background: #f4f6f9; margin: 0; }
    .test-container { max-width: 800px; margin: 40px auto; background: #fff; padding: 30px; border-radius: 10px; box-shadow: 0 5px 15px rgba(0,0,0,0.1); }
    h2 { text-align: center; color: #2c3e50; border-bottom: 2px solid #ecf0f1; padding-bottom: 10px; }
    .question-card { margin-bottom: 25px; }
    .q-text { font-size: 18px; font-weight: bold; margin-bottom: 10px; color: #34495e; }
    .options label { display: block; background: #f8f9fa; padding: 10px; margin-bottom: 8px; border-radius: 5px; cursor: pointer; border: 1px solid #ddd; }
    .submit-btn { display: block; width: 100%; background: #27ae60; color: white; padding: 15px; border: none; border-radius: 8px; font-size: 18px; cursor: pointer; margin-top: 20px; }
  </style>
</head>
<body>
<jsp:include page="/jsp/header.jsp"/>
<div class="test-container">
  <h2>📝 Làm bài kiểm tra năng lực</h2>
  <%
    List<Question> questions = (List<Question>) request.getAttribute("questions");
    if (questions != null && !questions.isEmpty()) {
  %>
  <form action="${pageContext.request.contextPath}/entrance-test?action=submit" method="post">
    <input type="hidden" name="testID" value="<%= request.getAttribute("testID") %>">
    <% for (int i = 0; i < questions.size(); i++) { Question q = questions.get(i); %>
    <div class="question-card">
      <div class="q-text">Câu <%= (i+1) %>: <%= q.getQuestionText() %></div>
      <div class="options">
        <% if(q.getOptionA() != null) { %><label><input type="radio" name="q_<%= q.getQuestionID() %>" value="A" required> A. <%= q.getOptionA() %></label><% } %>
        <% if(q.getOptionB() != null) { %><label><input type="radio" name="q_<%= q.getQuestionID() %>" value="B" required> B. <%= q.getOptionB() %></label><% } %>
        <% if(q.getOptionC() != null) { %><label><input type="radio" name="q_<%= q.getQuestionID() %>" value="C" required> C. <%= q.getOptionC() %></label><% } %>
        <% if(q.getOptionD() != null) { %><label><input type="radio" name="q_<%= q.getQuestionID() %>" value="D" required> D. <%= q.getOptionD() %></label><% } %>
      </div>
    </div>
    <% } %>
    <button type="submit" class="submit-btn">Nộp Bài (Submit)</button>
  </form>
  <% } %>
</div>
</body>
</html>