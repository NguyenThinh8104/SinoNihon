<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <title>Upload Test | SinoNihon</title>
  <style>
    .upload-box {
      max-width: 500px;
      margin: 50px auto;
      padding: 30px;
      background: #fff;
      border-radius: 10px;
      box-shadow: 0 10px 25px rgba(0,0,0,0.1);
      text-align: center;
    }
    input[type="file"] { margin: 20px 0; }
    .error-msg { color: #e74c3c; font-weight: bold; margin-bottom: 15px; }
    .submit-btn {
      background: #27ae60; color: white; padding: 10px 25px;
      border: none; border-radius: 5px; cursor: pointer; font-size: 16px;
    }
    .submit-btn:hover { background: #219150; }
  </style>
</head>
<body style="background: #f4f6f9; font-family: Arial, sans-serif; margin: 0;">
<jsp:include page="/jsp/header.jsp"/>

<div class="upload-box">
  <h2>Nhập bài test từ file Word</h2>
  <p style="font-size: 14px; color: #666;">Hỗ trợ định dạng .docx (Ví dụ: Q1:, A., B., Answer:)</p>

  <% if (request.getAttribute("error") != null) { %>
  <div class="error-msg">⚠ <%= request.getAttribute("error") %></div>
  <% } %>

  <form action="${pageContext.request.contextPath}/entrance-test?action=upload" method="post" enctype="multipart/form-data">
    <input type="file" name="file" accept=".docx" required><br>
    <button type="submit" class="submit-btn">Tải lên và tạo bài test</button>
  </form>
</div>
</body>
</html>