package controller;

import dao.LessonDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/course-detail")
public class CourseDetailController extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int courseID = Integer.parseInt(request.getParameter("id"));

        try {
            LessonDAO dao = new LessonDAO();
            request.setAttribute("lessonList", dao.getLessonsByCourse(courseID));
            request.setAttribute("courseID", courseID);
            request.getRequestDispatcher("/jsp/course-detail.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}