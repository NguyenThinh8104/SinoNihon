package controller;

import dao.TestDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.EntranceTest;
import model.Question;
import model.User;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/entrance-test")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2,  // 2MB
        maxFileSize = 1024 * 1024 * 10,       // 10MB
        maxRequestSize = 1024 * 1024 * 50     // 50MB
)
public class TestController extends HttpServlet {

    private final TestDAO testDAO = new TestDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
            return;
        }

        String action = request.getParameter("action");

        try {
            if ("take".equals(action)) {
                // HỌC VIÊN LÀM BÀI
                int testID = Integer.parseInt(request.getParameter("testID"));
                List<Question> questions = testDAO.getQuestionsByTest(testID);

                if (questions == null || questions.isEmpty()) {
                    throw new Exception("Bài kiểm tra không tồn tại hoặc chưa có câu hỏi.");
                }

                request.setAttribute("questions", questions);
                request.setAttribute("testID", testID);
                request.getRequestDispatcher("/jsp/take-test.jsp").forward(request, response);

            } else if ("upload".equals(action)) {
                // UPLOAD BÀI (Chỉ Admin/Mentor mới được vào)
                if (!"Admin".equalsIgnoreCase(user.getRole()) && !"Mentor".equalsIgnoreCase(user.getRole())) {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied. Chỉ Admin/Mentor mới có quyền upload.");
                    return;
                }
                request.getRequestDispatcher("/jsp/upload-test.jsp").forward(request, response);

            } else {
                // MẶC ĐỊNH LÀ ACTION="list" HOẶC RỖNG -> HIỂN THỊ DANH SÁCH BÀI TEST
                List<EntranceTest> tests = testDAO.getAllTests();
                request.setAttribute("testList", tests);
                request.getRequestDispatcher("/jsp/test-list.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print("<h2 style='color:red;'>⚠ Lỗi Hệ Thống: " + e.getMessage() + "</h2>");
            response.getWriter().print("<p>Sếp hãy check lại Terminal/Console của IntelliJ để xem chi tiết nhé!</p>");
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8"); // Đảm bảo tiếng Việt không bị lỗi font
        String action = request.getParameter("action");

        if ("upload".equals(action)) {
            handleUpload(request, response);
        } else if ("submit".equals(action)) {
            handleSubmit(request, response);
        }
    }

    private void handleUpload(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");

        // Bảo mật 2 lớp: Chặn post request trái phép từ Postman/Curl
        if (user == null || (!"Admin".equalsIgnoreCase(user.getRole()) && !"Mentor".equalsIgnoreCase(user.getRole()))) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied.");
            return;
        }

        try {
            Part filePart = request.getPart("file");
            if (filePart == null || filePart.getSize() == 0 || !filePart.getSubmittedFileName().endsWith(".docx")) {
                request.setAttribute("error", "Vui lòng chọn file định dạng .docx hợp lệ.");
                request.getRequestDispatcher("/jsp/upload-test.jsp").forward(request, response);
                return;
            }

            List<Question> questions = new ArrayList<>();
            Question currentQues = null;

            try (InputStream is = filePart.getInputStream();
                 XWPFDocument document = new XWPFDocument(is);
                 XWPFWordExtractor extractor = new XWPFWordExtractor(document)) {

                String fullText = extractor.getText();
                String[] lines = fullText.split("\\r?\\n");

                for (String line : lines) {
                    String text = line.trim();
                    if (text.isEmpty()) continue;

                    // Bắt đầu một câu hỏi mới
                    if (text.matches("^(Q?\\d+|Câu \\d+)\\s*[:\\.].*")) {
                        currentQues = new Question();
                        currentQues.setQuestionText(text.replaceFirst("^(Q?\\d+|Câu \\d+)\\s*[:\\.]\\s*", "").trim());
                    }
                    else if (currentQues != null) {
                        // Nhận diện đáp án đúng
                        if (text.matches("^(Answer|Đáp án)\\s*[:\\.].*")) {
                            currentQues.setCorrectOption(text.replaceAll("^(Answer|Đáp án)\\s*[:\\.]\\s*", "").trim().toUpperCase());
                            questions.add(currentQues);
                            currentQues = null;
                        }
                        else {
                            // THUẬT TOÁN PHÂN TÍCH ĐÁP ÁN AN TOÀN HƠN (Safe Bounds Checking)
                            int idxA = Math.max(text.indexOf("A."), text.indexOf("A:"));
                            int idxB = Math.max(text.indexOf("B."), text.indexOf("B:"));
                            int idxC = Math.max(text.indexOf("C."), text.indexOf("C:"));
                            int idxD = Math.max(text.indexOf("D."), text.indexOf("D:"));

                            if (idxA != -1 && idxB != -1 && idxA < idxB) { // Phải đảm bảo A nằm trước B
                                if (idxC != -1 && idxD != -1 && idxB < idxC && idxC < idxD) {
                                    currentQues.setOptionA(text.substring(idxA + 2, idxB).trim());
                                    currentQues.setOptionB(text.substring(idxB + 2, idxC).trim());
                                    currentQues.setOptionC(text.substring(idxC + 2, idxD).trim());
                                    currentQues.setOptionD(text.substring(idxD + 2).trim());
                                } else {
                                    currentQues.setOptionA(text.substring(idxA + 2, idxB).trim());
                                    currentQues.setOptionB(text.substring(idxB + 2).trim());
                                }
                            } else {
                                // Fallback: Đáp án nằm trên từng dòng
                                if (text.startsWith("A.") || text.startsWith("A:")) currentQues.setOptionA(text.substring(2).trim());
                                else if (text.startsWith("B.") || text.startsWith("B:")) currentQues.setOptionB(text.substring(2).trim());
                                else if (text.startsWith("C.") || text.startsWith("C:")) currentQues.setOptionC(text.substring(2).trim());
                                else if (text.startsWith("D.") || text.startsWith("D:")) currentQues.setOptionD(text.substring(2).trim());
                            }
                        }
                    }
                }
            }

            if (questions.isEmpty()) {
                request.setAttribute("error", "Định dạng file sai. Hãy đảm bảo cú pháp: Câu 1: ... A. ... B. ... Answer: A");
                request.getRequestDispatcher("/jsp/upload-test.jsp").forward(request, response);
                return;
            }

            // Gọi Transaction DAO an toàn
            EntranceTest test = new EntranceTest();
            test.setTitle("Entrance Test - " + System.currentTimeMillis());
            test.setCreatedBy(user.getUserId());
            test.setSource("Word");

            int testID = testDAO.createTestWithQuestions(test, questions);

            // Chuyển hướng sang trang làm bài
            request.getSession().setAttribute("success", "Tải đề thi thành công! Bài test đã sẵn sàng cho học viên.");
            response.sendRedirect(request.getContextPath() + "/entrance-test?action=list");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi Server: " + e.getMessage());
            request.getRequestDispatcher("/jsp/upload-test.jsp").forward(request, response);
        }
    }

    private void handleSubmit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
            return;
        }

        try {
            int testID = Integer.parseInt(request.getParameter("testID"));
            List<Question> questions = testDAO.getQuestionsByTest(testID);
            int score = 0;

            for (Question q : questions) {
                String userAnswer = request.getParameter("q_" + q.getQuestionID());
                if (userAnswer != null && userAnswer.equalsIgnoreCase(q.getCorrectOption())) {
                    score++;
                }
            }

            // Tích hợp: Tương lai bạn cần viết hàm lưu điểm vào Database ở đây
            // testDAO.saveTestResult(testID, user.getUserId(), score, questions.size());

            request.setAttribute("score", score);
            request.setAttribute("total", questions.size());
            request.getRequestDispatcher("/jsp/test-result.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/dashboard");
        }
    }
}