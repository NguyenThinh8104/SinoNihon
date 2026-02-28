package controller;

import dao.DBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/test-db")
public class TestDBServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            if (DBContext.getConnection() != null) {
                out.println("<h2>Connected to SQL Server successfully 🎉</h2>");
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h2>Connection FAILED ❌</h2>");
        }
    }
}
