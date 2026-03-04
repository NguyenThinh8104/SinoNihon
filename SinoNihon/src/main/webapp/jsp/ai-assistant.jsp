<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*, model.ChatHistory" %>
<%
    String feature = (String) request.getAttribute("feature");
    List<ChatHistory> history = (List<ChatHistory>) request.getAttribute("chatHistory");
    String title = "LEARNING_PATH".equals(feature) ? "Tư vấn lộ trình học" : "Sửa lỗi ngữ pháp";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>AI Assistant | SinoNihon</title>
    <style>
        body {
            font-family: 'Noto Sans JP', 'Noto Sans SC', sans-serif;
            background: #f4f6f9;
            margin: 0;
            padding: 20px;
        }
        .chat-container {
            max-width: 850px;
            margin: 0 auto;
            background: #fff;
            border-radius: 12px;
            box-shadow: 0 10px 25px rgba(0,0,0,0.05);
            display: flex;
            flex-direction: column;
            height: 85vh;
            overflow: hidden;
        }
        .chat-header {
            padding: 20px;
            background: #1f2a40;
            color: white;
            text-align: center;
            font-weight: bold;
            font-size: 18px;
            letter-spacing: 0.5px;
        }
        .chat-box {
            flex: 1;
            padding: 20px;
            overflow-y: auto;
            background: #fdfdfd;
            scroll-behavior: smooth;
        }
        .message {
            margin-bottom: 20px;
            display: flex;
        }
        .message.user {
            justify-content: flex-end;
        }
        .message-content {
            max-width: 75%;
            padding: 14px 18px;
            border-radius: 16px;
            line-height: 1.6;
            font-size: 15px;
            white-space: pre-wrap; /* Quan trọng: Tự động nhận diện ký tự \n để xuống dòng */
        }
        .user .message-content {
            background: #3498db;
            color: white;
            border-bottom-right-radius: 2px;
        }
        .ai .message-content {
            background: #eaf2f8;
            color: #2c3e50;
            border-bottom-left-radius: 2px;
            border: 1px solid #d6eaf8;
        }
        .chat-input-area {
            padding: 15px 20px;
            background: #fff;
            border-top: 1px solid #eee;
            display: flex;
            gap: 10px;
            align-items: center;
        }
        .chat-input-area input {
            flex: 1;
            padding: 14px;
            border: 1px solid #ccc;
            border-radius: 8px;
            font-size: 15px;
            outline: none;
            transition: border 0.2s;
        }
        .chat-input-area input:focus {
            border-color: #3498db;
        }
        .chat-input-area button {
            padding: 14px 24px;
            background: #27ae60;
            color: white;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            font-size: 15px;
            font-weight: bold;
            transition: background 0.2s;
        }
        .chat-input-area button:hover {
            background: #219150;
        }
        .chat-input-area button:disabled {
            background: #95a5a6;
            cursor: not-allowed;
        }
        .loading {
            display: none;
            color: #7f8c8d;
            font-style: italic;
            margin-bottom: 15px;
            font-size: 14px;
        }
        .loading span { animation: blink 1.4s infinite both; }
        .loading span:nth-child(2) { animation-delay: 0.2s; }
        .loading span:nth-child(3) { animation-delay: 0.4s; }
        @keyframes blink { 0% { opacity: 0.2; } 20% { opacity: 1; } 100% { opacity: 0.2; } }
    </style>
</head>
<body>

<div class="chat-container">
    <div class="chat-header">🤖 SinoNihon AI - <%= title %></div>

    <div class="chat-box" id="chatBox">
        <% if(history != null && !history.isEmpty()) {
            for(ChatHistory msg : history) { %>
        <div class="message <%= msg.getSenderType().toLowerCase() %>">
            <div class="message-content"><%= msg.getMessage() %></div>
        </div>
        <%  }
        } else { %>
        <div class="message ai">
            <div class="message-content">Xin chào! Tôi là Trợ lý ảo của SinoNihon. Hãy nhập <%= "LEARNING_PATH".equals(feature) ? "mục tiêu học tập của bạn" : "câu cần sửa ngữ pháp" %> để tôi hỗ trợ nhé!</div>
        </div>
        <% } %>

        <div class="loading" id="loadingIndicator">SinoNihon AI đang suy nghĩ<span>.</span><span>.</span><span>.</span></div>
    </div>

    <div class="chat-input-area">
        <input type="text" id="userInput" placeholder="Nhập câu hỏi của bạn tại đây..." onkeypress="handleKeyPress(event)">
        <button id="sendBtn" onclick="sendMessage()">Gửi</button>
    </div>
</div>

<script>
    const chatBox = document.getElementById("chatBox");
    const userInput = document.getElementById("userInput");
    const sendBtn = document.getElementById("sendBtn");
    const loadingIndicator = document.getElementById("loadingIndicator");
    const feature = "<%= feature %>";

    chatBox.scrollTop = chatBox.scrollHeight;

    function handleKeyPress(e) {
        if (e.key === 'Enter' && !sendBtn.disabled) {
            sendMessage();
        }
    }

    function createMessageBubble(sender) {
        const msgDiv = document.createElement("div");
        msgDiv.className = "message " + sender;
        const contentDiv = document.createElement("div");
        contentDiv.className = "message-content";
        msgDiv.appendChild(contentDiv);
        chatBox.insertBefore(msgDiv, loadingIndicator);
        return contentDiv;
    }

    // Hiệu ứng Typewriter an toàn (dùng textContent chống rác HTML)
    function typeWriterEffect(element, text, speed = 15) {
        let i = 0;
        element.textContent = "";

        function typing() {
            if (i < text.length) {
                element.textContent += text.charAt(i);
                i++;
                chatBox.scrollTop = chatBox.scrollHeight;
                setTimeout(typing, speed);
            } else {
                sendBtn.disabled = false;
                userInput.disabled = false;
                userInput.focus();
            }
        }
        typing();
    }

    function sendMessage() {
        const text = userInput.value.trim();
        if (!text) return;

        userInput.disabled = true;
        sendBtn.disabled = true;

        const userContent = createMessageBubble("user");
        userContent.textContent = text;
        userInput.value = "";

        loadingIndicator.style.display = "block";
        chatBox.scrollTop = chatBox.scrollHeight;

        const formData = new URLSearchParams();
        formData.append("message", text);
        formData.append("feature", feature);

        fetch('<%= request.getContextPath() %>/ai-assistant', {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' },
            body: formData.toString()
        })
            .then(response => {
                if (!response.ok) throw new Error("Server error");
                return response.text();
            })
            .then(data => {
                loadingIndicator.style.display = "none";
                const aiContent = createMessageBubble("ai");
                typeWriterEffect(aiContent, data);
            })
            .catch(error => {
                loadingIndicator.style.display = "none";
                const aiContent = createMessageBubble("ai");
                aiContent.textContent = "Xin lỗi, hệ thống đang bận. Vui lòng thử lại sau.";
                sendBtn.disabled = false;
                userInput.disabled = false;
            });
    }
</script>

</body>
</html>