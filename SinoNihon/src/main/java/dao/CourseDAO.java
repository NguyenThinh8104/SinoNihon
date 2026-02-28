package dao;

import model.Course;
import java.sql.*;
import java.util.*;

public class CourseDAO {

    public List<Course> getAllCourses() throws Exception {
        List<Course> list = new ArrayList<>();
        Connection conn = DBContext.getConnection();
        String sql = "SELECT * FROM Courses";

        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            list.add(new Course(
                    rs.getInt("CourseID"),
                    rs.getString("Title"),
                    rs.getString("Language"),
                    rs.getString("Description")
            ));
        }
        return list;
    }
}