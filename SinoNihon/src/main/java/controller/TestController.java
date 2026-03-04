package controller;

import dao.TestDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.Question;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.IOException;
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
        String action = request.getParameter("action");
        if ("take".equals(action)) {
            try {
                int testID = Integer.parseInt(request.getParameter("testID"));
                List<Question> questions = testDAO.getQuestionsByTest(testID);
                request.setAttribute("questions", questions);
                request.setAttribute("testID", testID);
                // GỌI FILE JSP ĐỂ LÀM BÀI
                request.getRequestDispatcher("/jsp/take-test.jsp").forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect(request.getContextPath() + "/dashboard");
            }
        } else {
            request.getRequestDispatcher("/jsp/upload-test.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("upload".equals(action)) {
            handleUpload(request, response);
        } else if ("submit".equals(action)) {
            handleSubmit(request, response);
        }
    }

    private void handleUpload(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Part filePart = request.getPart("file");
            if (filePart == null || filePart.getSize() == 0) {
                request.setAttribute("error", "File trống hoặc chưa được chọn.");
                request.getRequestDispatcher("/jsp/upload-test.jsp").forward(request, response);
                return;
            }

            List<Question> questions = new ArrayList<>();
            Question currentQues = null;

            // Dùng XWPFWordExtractor để giải quyết triệt để lỗi định dạng ngắt dòng
            try (XWPFDocument document = new XWPFDocument(filePart.getInputStream());
                 XWPFWordExtractor extractor = new XWPFWordExtractor(document)) {

                String fullText = extractor.getText();
                String[] lines = fullText.split("\\r?\\n"); // Cắt từng dòng để phân tích

                for (String line : lines) {
                    String text = line.trim();
                    if (text.isEmpty()) continue;

                    // Xử lý câu hỏi: Q1:, Q2:, 10:
                    if (text.matches("^(Q?\\d+|Câu \\d+)\\s*[:\\.].*")) {
                        currentQues = new Question();
                        currentQues.setQuestionText(text.replaceFirst("^(Q?\\d+|Câu \\d+)\\s*[:\\.]\\s*", "").trim());
                    }
                    else if (currentQues != null) {
                        // Xử lý dòng Answer: A
                        if (text.matches("^(Answer|Đáp án)\\s*[:\\.].*")) {
                            currentQues.setCorrectOption(text.replaceAll("^(Answer|Đáp án)\\s*[:\\.]\\s*", "").trim());
                            questions.add(currentQues);
                            currentQues = null; // Hoàn tất 1 câu, reset cho câu sau
                        }
                        else {
                            // THUẬT TOÁN XỬ LÝ 4 ĐÁP ÁN TRÊN CÙNG 1 DÒNG (Đúng như ảnh em chụp)
                            int idxA = text.indexOf("A."); if (idxA == -1) idxA = text.indexOf("A:");
                            int idxB = text.indexOf("B."); if (idxB == -1) idxB = text.indexOf("B:");
                            int idxC = text.indexOf("C."); if (idxC == -1) idxC = text.indexOf("C:");
                            int idxD = text.indexOf("D."); if (idxD == -1) idxD = text.indexOf("D:");

                            if (idxA != -1 && idxB != -1) {
                                // Cắt lấy chuỗi giữa A và B, B và C, C và D
                                if (idxC != -1 && idxD != -1) {
                                    currentQues.setOptionA(text.substring(idxA + 2, idxB).trim());
                                    currentQues.setOptionB(text.substring(idxB + 2, idxC).trim());
                                    currentQues.setOptionC(text.substring(idxC + 2, idxD).trim());
                                    currentQues.setOptionD(text.substring(idxD + 2).trim());
                                } else {
                                    currentQues.setOptionA(text.substring(idxA + 2, idxB).trim());
                                    currentQues.setOptionB(text.substring(idxB + 2).trim());
                                }
                            } else {
                                // Fallback cho trường hợp mỗi đáp án 1 dòng
                                if (text.startsWith("A.") || text.startsWith("A:")) currentQues.setOptionA(text.substring(2).trim());
                                else if (text.startsWith("B.") || text.startsWith("B:")) currentQues.setOptionB(text.substring(2).trim());
                                else if (text.startsWith("C.") || text.startsWith("C:")) currentQues.setOptionC(text.substring(2).trim());
                                else if (text.startsWith("D.") || text.startsWith("D:")) currentQues.setOptionD(text.substring(2).trim());
                            }
                        }
                    }
                }
            }

            // Nếu thuật toán phân tích không ra câu nào, báo lỗi rành mạch
            if (questions.isEmpty()) {
                request.setAttribute("error", "Lỗi: File Word chưa đúng chuẩn. Hãy chắc chắn có Q1:, A. B. C. D. và Answer: !");
                request.getRequestDispatcher("/jsp/upload-test.jsp").forward(request, response);
                return;
            }

            // Lưu vào CSDL
            int testID = testDAO.createTest("Entrance Test - " + System.currentTimeMillis());
            testDAO.insertQuestions(testID, questions);

            // Chuyển hướng sang trang làm bài!
            response.sendRedirect(request.getContextPath() + "/entrance-test?action=take&testID=" + testID);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi Server: Không thể đọc file.");
            request.getRequestDispatcher("/jsp/upload-test.jsp").forward(request, response);
        }
    }

    private void handleSubmit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

            request.setAttribute("score", score);
            request.setAttribute("total", questions.size());
            // CHUYỂN SANG TRANG KẾT QUẢ
            request.getRequestDispatcher("/jsp/test-result.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/dashboard");
        }
    }
}