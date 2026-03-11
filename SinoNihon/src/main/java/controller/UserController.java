package controller;

import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.User;

import java.io.IOException;

@WebServlet("/profile")
public class UserController extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User u = (session != null) ? (User) session.getAttribute("user") : null;

        if (u == null) {
            response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
            return;
        }

        request.setAttribute("user", u);
        request.getRequestDispatcher("/jsp/profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User u = (User) session.getAttribute("user");

        if (u == null) {
            response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
            return;
        }

        try {
            u.setFullName(request.getParameter("fullname"));
            u.setPhone(request.getParameter("phone"));
            u.setEmail(request.getParameter("email"));

            userDAO.updateProfile(u);
            session.setAttribute("user", u);

            request.setAttribute("success", true);

        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("user", u);
        request.getRequestDispatcher("/jsp/profile.jsp").forward(request, response);
    }
}
