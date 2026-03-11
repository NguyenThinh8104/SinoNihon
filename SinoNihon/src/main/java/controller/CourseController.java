package controller;

import dao.CourseDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/courses")
public class CourseController extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            CourseDAO dao = new CourseDAO();
            request.setAttribute("courses", dao.getAllCourses());
            request.getRequestDispatcher("/jsp/courses.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}