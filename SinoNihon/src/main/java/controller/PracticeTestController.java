package controller;

import dao.TestDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Question;
import model.User;

import java.io.IOException;
import java.util.List;

@WebServlet("/practice-test")
public class PracticeTestController extends HttpServlet {

    private final TestDAO testDAO = new TestDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
            return;
        }

        String testIdParam = request.getParameter("testID");

        // TH1: Học viên chọn 1 bài Practice Test cụ thể để làm
        if (testIdParam != null && !testIdParam.trim().isEmpty()) {
            try {
                int testID = Integer.parseInt(testIdParam);
                List<Question> questions = testDAO.getQuestionsByTest(testID);

                request.setAttribute("testTitle", "Practice Test #" + testID);
                request.setAttribute("questions", questions);
                request.setAttribute("testID", testID);
                request.setAttribute("submitAction", request.getContextPath() + "/practice-test"); // Action cho Practice

                request.getRequestDispatcher("/jsp/test-taking.jsp").forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect(request.getContextPath() + "/dashboard?error=load_failed");
            }
        }
        // TH2: Học viên vào mục Practice Test nhưng chưa chọn bài, hiển thị UI để "AI Generate"
        else {
            request.getRequestDispatcher("/jsp/generate-practice.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
            return;
        }

        String action = request.getParameter("action");

        try {
            // HÀNH ĐỘNG 1: Yêu cầu AI tạo bài Practice Test mới
            if ("generate_ai_test".equals(action)) {
                String skill = request.getParameter("skill"); // MultipleChoice hoặc Listening

                // TODO: Gọi OpenAI/Gemini API để lấy mảng JSON câu hỏi dựa trên skill
                // TODO: Dùng TestDAO.createTest() và TestDAO.insertQuestionWithAnswers() để lưu DB

                // MÔ PHỎNG: Giả sử AI tạo xong và lưu vào DB thành công với ID = 2
                int newGeneratedTestID = 2;

                // Điều hướng sang bài test vừa tạo
                response.sendRedirect(request.getContextPath() + "/practice-test?testID=" + newGeneratedTestID);
            }
            // HÀNH ĐỘNG 2: Học viên nộp bài làm Practice Test
            else {
                String testIdParam = request.getParameter("testID");

                // TODO: LOGIC CHẤM ĐIỂM VÀ LƯU VÀO BẢNG TestResult
                // int score = gradeTest(request, Integer.parseInt(testIdParam));
                // testResultDAO.saveResult(user.getUserId(), Integer.parseInt(testIdParam), score);

                response.sendRedirect(request.getContextPath() + "/dashboard?msg=practice_submitted");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/dashboard?error=system");
        }
    }
}
