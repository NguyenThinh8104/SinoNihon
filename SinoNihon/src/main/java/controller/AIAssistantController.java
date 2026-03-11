package controller;

import com.google.genai.Chat;
import dao.ChatDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.ChatHistory;
import model.User;
import utils.AIService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/ai-assistant")
public class AIAssistantController  extends HttpServlet {
    private final ChatDAO chatDAO = new ChatDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/jsp/login.jsp");
            return;
        }
        User user = (User) session.getAttribute("user");
        String feature = "AI_assistant";

        try {
            List<ChatHistory> history = chatDAO.getHistory(user.getUserId(), feature);
            req.setAttribute("chatHistory", history);
            req.setAttribute("feature", feature);
        } catch (Exception e) {
            e.printStackTrace();
        }

        req.getRequestDispatcher("/jsp/ai-assistant.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        User user = (User) session.getAttribute("user");
        String message = req.getParameter("message");
        String feature = "AI_assistant";

        if (message == null || message.trim().isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            chatDAO.saveMessage(new ChatHistory(user.getUserId(), feature, "USER", message));

            String systemPrompt = utils.PromptReader.readPrompt("prompt/Prompt mẫu câu hỏi.txt");

            String aiResponse = AIService.getAIResponse(systemPrompt, message);

            chatDAO.saveMessage(new ChatHistory(user.getUserId(), feature, "AI", aiResponse));

            out.print(aiResponse);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("Đã xảy ra lỗi hệ thống");
        }
    }
}
