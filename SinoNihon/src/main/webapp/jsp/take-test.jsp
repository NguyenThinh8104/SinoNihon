<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List, model.Question" %>
<!DOCTYPE html>
<html>
<head>
  <title>Take Test | SinoNihon</title>
  <style>
    body { font-family: 'Noto Sans JP', 'Noto Sans SC', sans-serif; background: #f4f6f9; margin: 0; }
    .test-container { max-width: 850px; margin: 40px auto; background: #fff; padding: 35px; border-radius: 12px; box-shadow: 0 5px 20px rgba(0,0,0,0.08); }
    h2 { text-align: center; color: #2c3e50; border-bottom: 2px solid #ecf0f1; padding-bottom: 15px; margin-bottom: 30px; }
    .question-card { margin-bottom: 30px; padding: 20px; background: #fafbfc; border-radius: 8px; border: 1px solid #e1e4e8; }
    .q-text { font-size: 18px; font-weight: 600; margin-bottom: 15px; color: #2c3e50; line-height: 1.5; }
    .options label { display: flex; align-items: center; background: #ffffff; padding: 12px 15px; margin-bottom: 10px; border-radius: 6px; cursor: pointer; border: 1px solid #d1d5da; transition: all 0.2s ease; }
    .options label:hover { background: #f1f8ff; border-color: #3498db; }
    .options input[type="radio"] { margin-right: 12px; transform: scale(1.2); cursor: pointer; }
    .submit-btn { display: block; width: 100%; background: #27ae60; color: white; padding: 16px; border: none; border-radius: 8px; font-size: 18px; font-weight: bold; cursor: pointer; margin-top: 30px; transition: background 0.3s ease; }
    .submit-btn:hover { background: #219150; }
    .empty-state { text-align: center; padding: 40px 20px; }
    .empty-state h3 { color: #e74c3c; }
    .back-link { display: inline-block; margin-top: 15px; padding: 10px 20px; background: #3498db; color: white; text-decoration: none; border-radius: 6px; }
    .back-link:hover { background: #2980b9; }
  </style>
</head>
<body>

<div class="test-container">
  <h2>📝 Làm Bài Kiểm Tra</h2>

  <%
    List<Question> questions = (List<Question>) request.getAttribute("questions");
    if (questions != null && !questions.isEmpty()) {
  %>
  <form action="<%= request.getContextPath() %>/entrance-test" method="post" onsubmit="return confirmSubmit()">
    <input type="hidden" name="action" value="submit">
    <input type="hidden" name="testID" value="<%= request.getAttribute("testID") %>">

    <% for (int i = 0; i < questions.size(); i++) {
      Question q = questions.get(i);
    %>
    <div class="question-card">
      <div class="q-text">Câu <%= (i+1) %>: <%= q.getQuestionText() %></div>
      <div class="options">
        <% if(q.getOptionA() != null && !q.getOptionA().trim().isEmpty()) { %>
        <label><input type="radio" name="q_<%= q.getQuestionID() %>" value="A" required> A. <%= q.getOptionA() %></label>
        <% } %>
        <% if(q.getOptionB() != null && !q.getOptionB().trim().isEmpty()) { %>
        <label><input type="radio" name="q_<%= q.getQuestionID() %>" value="B" required> B. <%= q.getOptionB() %></label>
        <% } %>
        <% if(q.getOptionC() != null && !q.getOptionC().trim().isEmpty()) { %>
        <label><input type="radio" name="q_<%= q.getQuestionID() %>" value="C" required> C. <%= q.getOptionC() %></label>
        <% } %>
        <% if(q.getOptionD() != null && !q.getOptionD().trim().isEmpty()) { %>
        <label><input type="radio" name="q_<%= q.getQuestionID() %>" value="D" required> D. <%= q.getOptionD() %></label>
        <% } %>
      </div>
    </div>
    <% } %>

    <button type="submit" class="submit-btn">Nộp Bài (Submit)</button>
  </form>

  <% } else { %>
  <div class="empty-state">
    <h3>⚠ Không tìm thấy câu hỏi cho bài kiểm tra này!</h3>
    <p>Bài kiểm tra có thể chưa được cấu hình đúng hoặc bị lỗi.</p>
    <a href="<%= request.getContextPath() %>/dashboard" target="_parent" class="back-link">Quay lại Dashboard</a>
  </div>
  <% } %>

</div>

<script>
  function confirmSubmit() {
    return confirm("Bạn có chắc chắn muốn nộp bài? Không thể thay đổi đáp án sau khi nộp.");
  }
</script>

</body>
</html>