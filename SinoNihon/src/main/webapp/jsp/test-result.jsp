<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>Kết quả làm bài</title>
  <style>
    body { font-family: 'Noto Sans JP', sans-serif; background: #f4f6f9; margin: 0; padding-top: 50px; }
    .result-card { background: #fff; width: 100%; max-width: 500px; padding: 40px; margin: auto; border-radius: 12px; box-shadow: 0 10px 30px rgba(0,0,0,0.1); text-align: center; border-top: 5px solid #27ae60; }
    .icon { font-size: 60px; margin-bottom: 10px; }
    h2 { color: #2c3e50; margin-bottom: 5px; font-size: 24px; }
    .subtitle { color: #7f8c8d; font-size: 16px; margin-bottom: 30px; }
    .score-box { background: #f8f9fa; border: 2px dashed #27ae60; border-radius: 10px; padding: 20px; margin-bottom: 30px; }
    .score-number { font-size: 56px; font-weight: bold; color: #27ae60; }
    .score-total { font-size: 24px; color: #95a5a6; }
    .back-btn { display: inline-block; width: 100%; padding: 14px 0; background: #3498db; color: white; text-decoration: none; border-radius: 8px; font-size: 16px; font-weight: bold; box-sizing: border-box; }
    .back-btn:hover { background: #2980b9; }
  </style>
</head>
<body>

<script>
  alert("🎉 Nộp bài thành công! Hệ thống đang chấm điểm...");
</script>

<div class="result-card">
  <div class="icon">🎉</div>
  <h2>Hoàn Thành Bài Kiểm Tra</h2>
  <div class="subtitle">Dưới đây là kết quả bài thi của bạn</div>

  <div class="score-box">
    <div style="font-weight: bold; color: #34495e; margin-bottom: 10px;">ĐIỂM SỐ</div>
    <span class="score-number"><%= request.getAttribute("score") %></span>
    <span class="score-total">/ <%= request.getAttribute("total") %></span>
  </div>

  <a href="<%= request.getContextPath() %>/dashboard" target="_parent" class="back-btn">Quay lại Dashboard</a>
</div>

</body>
</html>