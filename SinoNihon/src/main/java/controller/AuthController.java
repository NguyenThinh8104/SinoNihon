package controller;

import dao.UserDAO;
import model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/auth")
public class AuthController extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        try {

            // ===== REGISTER =====
            if ("register".equals(action)) {

                User u = new User();
                u.setUsername(request.getParameter("username"));
                u.setPassword(request.getParameter("password"));
                u.setFullName(request.getParameter("fullname"));
                u.setPhone(request.getParameter("phone"));
                u.setEmail(request.getParameter("email"));

                userDAO.register(u);

                // đăng ký thành công
                response.sendRedirect(request.getContextPath() + "/jsp/login.jsp?register=success");
                return;
            }

            // ===== LOGIN =====
            else if ("login".equals(action)) {

                User u = userDAO.login(
                        request.getParameter("username"),
                        request.getParameter("password")
                );

                if (u != null) {
                    request.getSession().setAttribute("user", u);
                    response.sendRedirect(request.getContextPath() + "/dashboard");
                } else {
                    response.sendRedirect(request.getContextPath() + "/jsp/login.jsp?error=true");
                }
                return;
            }

        } catch (Exception e) {

            // 🔥 BẮT LỖI TRÙNG EMAIL / USERNAME
            if (e.getMessage() != null && e.getMessage().contains("duplicate")) {
                response.sendRedirect(request.getContextPath() + "/jsp/register.jsp?error=duplicate");
            } else {
                e.printStackTrace();
                response.sendRedirect(request.getContextPath() + "/jsp/register.jsp?error=system");
            }
        }
    }
}