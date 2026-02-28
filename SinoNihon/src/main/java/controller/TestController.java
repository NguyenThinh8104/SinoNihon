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
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

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
                if (questions.isEmpty()) {
                    request.setAttribute("error", "Bài kiểm tra này không có câu hỏi nào!");
                }
                request.setAttribute("questions", questions);
                request.setAttribute("testID", testID);
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
                request.setAttribute("error", "Vui lòng chọn file hợp lệ!");
                request.getRequestDispatcher("/jsp/upload-test.jsp").forward(request, response);
                return;
            }

            List<Question> questions = new ArrayList<>();
            Question currentQues = null;

            try (XWPFDocument document = new XWPFDocument(filePart.getInputStream())) {
                for (XWPFParagraph p : document.getParagraphs()) {
                    String text = p.getText().trim();
                    if (text.isEmpty()) continue;

                    // Support regex: Q1:, 1:, Câu 1:
                    if (text.matches("^(Q?\\d+|Câu \\d+):.*")) {
                        currentQues = new Question();
                        currentQues.setQuestionText(text.replaceFirst("^(Q?\\d+|Câu \\d+):", "").trim());
                    } else if (currentQues != null) {
                        // Defensive Programming: Check null avoiding NullPointerException
                        if (text.startsWith("A.") || text.startsWith("A:")) currentQues.setOptionA(text.substring(2).trim());
                        else if (text.startsWith("B.") || text.startsWith("B:")) currentQues.setOptionB(text.substring(2).trim());
                        else if (text.startsWith("C.") || text.startsWith("C:")) currentQues.setOptionC(text.substring(2).trim());
                        else if (text.startsWith("D.") || text.startsWith("D:")) currentQues.setOptionD(text.substring(2).trim());
                        else if (text.startsWith("Answer:") || text.startsWith("Answer :")) {
                            currentQues.setCorrectOption(text.replaceAll("^Answer\\s*:\\s*", "").trim());
                            questions.add(currentQues);
                            currentQues = null; // Reset to avoid overriding
                        } else {
                            // Cố gắng bắt các đáp án ghi trên cùng 1 dòng lỗi formatting
                            if (currentQues.getOptionA() == null && !text.startsWith("Answer")) {
                                currentQues.setOptionA(text); // Fallback for bad formats like Q4
                            }
                        }
                    }
                }
            }

            if (questions.isEmpty()) {
                request.setAttribute("error", "Không tìm thấy câu hỏi nào! Vui lòng kiểm tra lại định dạng file.");
                request.getRequestDispatcher("/jsp/upload-test.jsp").forward(request, response);
                return;
            }

            int testID = testDAO.createTest("Entrance Test - " + System.currentTimeMillis());
            testDAO.insertQuestions(testID, questions);

            // Bug Fix: &testID= instead of &id=
            response.sendRedirect(request.getContextPath() + "/entrance-test?action=take&testID=" + testID);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi xử lý file Word. Đảm bảo file đúng định dạng .docx!");
            request.getRequestDispatcher("/jsp/upload-test.jsp").forward(request, response);
        }
    }

    private void handleSubmit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int testID = Integer.parseInt(request.getParameter("testID"));
            List<Question> questions = testDAO.getQuestionsByTest(testID);
            int score = 0;

            for (int i = 0; i < questions.size(); i++) {
                // Fetch the option user picked using questionID
                String userAnswer = request.getParameter("q_" + questions.get(i).getQuestionID());
                if (userAnswer != null && userAnswer.equalsIgnoreCase(questions.get(i).getCorrectOption())) {
                    score++;
                }
            }

            request.setAttribute("score", score);
            request.setAttribute("total", questions.size());
            request.getRequestDispatcher("/jsp/test-result.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/dashboard");
        }
    }
}