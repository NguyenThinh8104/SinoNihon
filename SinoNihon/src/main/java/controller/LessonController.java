package controller;

import dao.LessonDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/lesson")
public class LessonController extends HttpServlet {

    // ===== CHỈ HIỂN THỊ LESSON (KHÔNG MARK DONE) =====
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int lessonID = Integer.parseInt(request.getParameter("id"));

        try {
            LessonDAO dao = new LessonDAO();
            request.setAttribute("lessonID", lessonID);
            request.setAttribute("vocabList", dao.getVocabularyByLesson(lessonID));
            request.getRequestDispatcher("/jsp/lesson.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ===== CHỈ MARK DONE KHI BẤM NÚT =====
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int lessonID = Integer.parseInt(request.getParameter("lessonID"));
        int courseID = Integer.parseInt(request.getParameter("courseID"));

        HttpSession session = request.getSession();
        model.User user = (model.User) session.getAttribute("user");

        try {
            LessonDAO dao = new LessonDAO();
            dao.markLessonDone(user.getUserId(), lessonID);
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("course-detail?id=" + courseID);
    }
}