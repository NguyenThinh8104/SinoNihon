<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>Kết quả làm bài | SinoNihon</title>
  <style>
    body { font-family: 'Noto Sans JP', sans-serif; background: #f4f6f9; margin: 0; }
    .result-box { max-width: 500px; margin: 80px auto; background: #fff; padding: 40px; border-radius: 12px; text-align: center; box-shadow: 0 10px 25px rgba(0,0,0,0.1); }
    .score { font-size: 48px; font-weight: bold; color: #27ae60; margin: 20px 0; }
    .back-btn { display: inline-block; padding: 10px 20px; background: #3498db; color: white; text-decoration: none; border-radius: 5px; margin-top: 20px; }
  </style>
</head>
<body>
<jsp:include page="/jsp/header.jsp"/>
<div class="result-box">
  <h2>🎉 Hoàn Thành Bài Kiểm Tra</h2>
  <p>Điểm số của bạn là:</p>
  <div class="score"><%= request.getAttribute("score") %> / <%= request.getAttribute("total") %></div>
  <p>Chúc mừng bạn đã hoàn thành bài thi!</p>
  <a href="${pageContext.request.contextPath}/dashboard" class="back-btn" target="_parent">Quay lại Dashboard</a>
</div>
</body>
</html>