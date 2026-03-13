<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>Make Test | SinoNihon Admin</title>
  <style>
    body { font-family: 'Noto Sans JP', 'Noto Sans SC', sans-serif; background: #f4f6f9; margin: 0; }
    .admin-container { padding: 40px; max-width: 1000px; margin: auto; }
    .header-title { color: #2c3e50; border-bottom: 3px solid #3498db; padding-bottom: 10px; margin-bottom: 30px; }

    .grid-layout { display: grid; grid-template-columns: 1fr 1fr; gap: 30px; }

    .card { background: #fff; padding: 30px; border-radius: 12px; box-shadow: 0 5px 15px rgba(0,0,0,0.05); border-top: 5px solid #3498db; }
    .card-ai { border-top: 5px solid #9b59b6; } /* Màu tím cho AI */

    .card h3 { margin-top: 0; color: #34495e; font-size: 20px; }
    .form-group { margin-bottom: 20px; }
    label { display: block; font-weight: 600; margin-bottom: 8px; color: #2c3e50; }
    input[type="text"], select { width: 100%; padding: 12px; border: 1px solid #ddd; border-radius: 8px; box-sizing: border-box; }
    input[type="file"] { width: 100%; padding: 15px; border: 2px dashed #bdc3c7; border-radius: 8px; background: #f8f9fa; cursor: pointer; box-sizing: border-box; }

    .btn { width: 100%; padding: 15px; border: none; border-radius: 8px; font-size: 16px; font-weight: bold; color: white; cursor: pointer; transition: 0.3s; }
    .btn-upload { background: #3498db; }
    .btn-upload:hover { background: #2980b9; }
    .btn-ai { background: #9b59b6; }
    .btn-ai:hover { background: #8e44ad; }

    .error-msg { background: #fdedec; color: #e74c3c; padding: 15px; border-radius: 6px; margin-bottom: 20px; font-weight: bold; border-left: 5px solid #e74c3c; }
    .hint { font-size: 13px; color: #7f8c8d; margin-top: 10px; line-height: 1.5; }
  </style>
</head>
<body>

<div class="admin-container">
  <h2 class="header-title">⚙️ Quản Lý Đề Thi (Make Test)</h2>

  <% if (request.getAttribute("error") != null) { %>
  <div class="error-msg">⚠ <%= request.getAttribute("error") %></div>
  <% } %>

  <div class="grid-layout">
    <div class="card">
      <h3>📄 Tải lên từ Word</h3>
      <p class="hint">Upload đề thi đã soạn sẵn. Hệ thống sẽ tự động bóc tách câu hỏi và đáp án.</p>

      <form action="<%= request.getContextPath() %>/entrance-test" method="post" enctype="multipart/form-data">
        <input type="hidden" name="action" value="upload">

        <div class="form-group">
          <label>File Đề Thi (.docx):</label>
          <input type="file" name="file" accept=".docx" required>
          <div class="hint"><b>Cú pháp chuẩn:</b> Câu 1: ... A. ... B. ... C. ... D. ... Answer: A</div>
        </div>

        <button type="submit" class="btn btn-upload">Tải lên & Lưu Đề Thi</button>
      </form>
    </div>

    <div class="card card-ai">
      <h3>🤖 Sinh đề bằng AI (Sắp ra mắt)</h3>
      <p class="hint">Tính năng sử dụng Trí tuệ nhân tạo để tự động biên soạn đề thi theo cấp độ.</p>

      <form>
        <div class="form-group">
          <label>Ngôn ngữ:</label>
          <select disabled>
            <option>Tiếng Nhật (Japanese)</option>
            <option>Tiếng Trung (Chinese)</option>
          </select>
        </div>
        <div class="form-group">
          <label>Cấp độ / Target:</label>
          <input type="text" placeholder="VD: N5, N4, HSK 1..." disabled>
        </div>

        <button type="button" class="btn btn-ai" style="opacity: 0.6; cursor: not-allowed;">Tạo Đề AI (Coming soon)</button>
      </form>
    </div>
  </div>
</div>

</body>
</html>