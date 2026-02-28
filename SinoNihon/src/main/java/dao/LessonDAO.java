package dao;

import model.Lesson;
import model.Vocabulary;

import java.sql.*;
import java.util.*;

public class LessonDAO {

    public List<Lesson> getLessonsByCourse(int courseID) throws Exception {
        List<Lesson> list = new ArrayList<>();
        Connection conn = DBContext.getConnection();
        String sql = "SELECT * FROM Lessons WHERE CourseID=?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, courseID);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            list.add(new Lesson(
                    rs.getInt("LessonID"),
                    rs.getInt("CourseID"),
                    rs.getString("Title")
            ));
        }
        return list;
    }

    public List<Vocabulary> getVocabularyByLesson(int lessonID) throws Exception {
        List<Vocabulary> list = new ArrayList<>();
        Connection conn = DBContext.getConnection();

        String sql = "SELECT * FROM Vocabulary WHERE LessonID=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, lessonID);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            list.add(new Vocabulary(
                    rs.getInt("VocabID"),
                    rs.getString("Word"),
                    rs.getString("Meaning"),
                    rs.getString("Pronunciation")
            ));
        }
        return list;
    }

    // ===== SAVE LESSON PROGRESS =====
    public void markLessonDone(int userID, int lessonID) throws Exception {

        Connection conn = DBContext.getConnection();

        String sql = "IF NOT EXISTS (SELECT * FROM UserLessonProgress WHERE UserID=? AND LessonID=?) " +
                "INSERT INTO UserLessonProgress (UserID, LessonID) VALUES (?,?)";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, userID);
        ps.setInt(2, lessonID);
        ps.setInt(3, userID);
        ps.setInt(4, lessonID);

        ps.executeUpdate();
    }

    // ===== COUNT COMPLETED LESSON =====
    public int countCompleted(int userID, int courseID) throws Exception {

        Connection conn = DBContext.getConnection();

        String sql =
                "SELECT COUNT(*) FROM UserLessonProgress ul " +
                        "JOIN Lessons l ON ul.LessonID = l.LessonID " +
                        "WHERE ul.UserID=? AND l.CourseID=?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, userID);
        ps.setInt(2, courseID);

        ResultSet rs = ps.executeQuery();
        if (rs.next()) return rs.getInt(1);

        return 0;
    }

    // ===== TOTAL LESSON =====
    public int totalLesson(int courseID) throws Exception {

        Connection conn = DBContext.getConnection();

        String sql = "SELECT COUNT(*) FROM Lessons WHERE CourseID=?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, courseID);

        ResultSet rs = ps.executeQuery();
        if (rs.next()) return rs.getInt(1);

        return 0;
    }
}