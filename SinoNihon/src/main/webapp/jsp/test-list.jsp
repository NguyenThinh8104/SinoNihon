<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List, model.EntranceTest, model.User" %>
<!DOCTYPE html>
<html>
<head>
  <title>Danh sách đề thi</title>
  <style>
    body { font-family: 'Noto Sans JP', 'Noto Sans SC', sans-serif; background: #f4f6f9; margin: 0; padding: 20px; }
    .page-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 30px; border-bottom: 2px solid #ecf0f1; padding-bottom: 15px; }
    .page-header h2 { color: #2c3e50; margin: 0; }
    .test-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(280px, 1fr)); gap: 20px; }
    .test-card { background: white; padding: 25px; border-radius: 10px; box-shadow: 0 5px 15px rgba(0,0,0,0.05); border-top: 4px solid #3498db; transition: 0.2s ease; }
    .test-card:hover { transform: translateY(-5px); box-shadow: 0 8px 25px rgba(0,0,0,0.15); }
    .test-title { font-size: 18px; font-weight: bold; color: #2c3e50; margin-bottom: 15px; }
    .test-meta { font-size: 13px; color: #7f8c8d; margin-bottom: 20px; }
    .take-btn { display: block; text-align: center; background: #27ae60; color: white; padding: 12px 0; text-decoration: none; border-radius: 6px; font-weight: bold; }
    .take-btn:hover { background: #219150; }
  </style>
</head>
<body>

<%
  String successMsg = (String) request.getSession().getAttribute("success");
  if (successMsg != null) {
%>
<script>alert("✅ <%= successMsg %>");</script>
<%
    request.getSession().removeAttribute("success");
  }
%>

<div class="page-header">
  <h2>📚 Danh sách Bài Kiểm Tra (Entrance Tests)</h2>
</div>

<div class="test-grid">
  <%
    List<EntranceTest> tests = (List<EntranceTest>) request.getAttribute("testList");
    if (tests != null && !tests.isEmpty()) {
      for (EntranceTest t : tests) {
  %>
  <div class="test-card">
    <div class="test-title"><%= t.getTitle() %></div>
    <div class="test-meta">
      🗓 Ngày tạo: <%= t.getCreatedAt() != null ? t.getCreatedAt().toString().substring(0, 16) : "Mới nhất" %><br>
      🏷 Nguồn: <%= t.getSource() %>
    </div>
    <a href="<%= request.getContextPath() %>/entrance-test?action=take&testID=<%= t.getTestID() %>" class="take-btn">Bắt đầu làm bài ➔</a>
  </div>
  <%      }
  } else {
  %>
  <div style="grid-column: 1 / -1; text-align: center; color: #7f8c8d; padding: 40px; background: white; border-radius: 10px;">
    Hiện tại chưa có bài kiểm tra nào trên hệ thống.
  </div>
  <% } %>
</div>

</body>
</html>