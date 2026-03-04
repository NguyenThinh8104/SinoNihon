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
        String feature = req.getParameter("feature");

        if (!"LEARNING_PATH".equals(feature) && !"GRAMMAR".equals(feature)) {
            feature = "LEARNING_PATH";
        }

        try{
            List<ChatHistory> history = chatDAO.getHistory(user.getUserId(), feature);
            req.setAttribute("chatHistory", history);
            req.setAttribute("feature", feature);
        } catch (Exception e) {
            throw new RuntimeException(e);
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
        String feature = req.getParameter("feature");

        if (message == null || message.trim().isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            chatDAO.saveMessage(new ChatHistory(user.getUserId(), feature, "USER", message));

            String systemPrompt = "";
            if ("LEARNING_PATH".equals(feature)) {
                systemPrompt = "Bạn là tư vấn viên giáo dục tại SinoNihon. TRẢ LỜI CỰC KỲ NGẮN GỌN, ĐI THẲNG VÀO TRỌNG TÂM. "
                        + "1. Hỏi từ vựng/dịch thuật: Chỉ đưa ra nghĩa, cách đọc và 1 ví dụ. KHÔNG giải thích dông dài. "
                        + "2. Xin lộ trình học: Chỉ gạch đầu dòng các bước chính, súc tích nhất có thể. "
                        + "3. KHÔNG chào hỏi vòng vo, KHÔNG thêm lời khuyên nếu người dùng không hỏi."
                        + "4. Những thứ khác không liên quan đến 3 ý trên và không phải tiếng Trung / Tiếng Nhật thì LỊCH SỰ xin phép không trả lời";
            } else if ("GRAMMAR".equals(feature)) {
                systemPrompt = "Bạn là giáo viên ngữ pháp (Trung/Nhật) tại SinoNihon. TRẢ LỜI CỰC KỲ NGẮN GỌN. "
                        + "Định dạng bắt buộc: 1. Câu đúng -> 2. Lỗi sai -> 3. Giải thích (Tối đa 2 câu). "
                        + "Tuyệt đối không giải thích lan man, không dùng từ ngữ dư thừa.";
            }

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
