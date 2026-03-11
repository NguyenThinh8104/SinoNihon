package dao;

import model.Question;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TestDAO {
    public int createTest(String title) throws Exception {
        String sql = "INSERT INTO EntranceTests(Title) VALUES (?)";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, title);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
            return -1;
        }
    }

    public void insertQuestions(int testID, List<Question> questions) throws Exception {
        // Fix SQL typo: QuestionTest -> QuestionText
        String sql = "INSERT INTO Questions (TestID, QuestionText, OptionA, OptionB, OptionC, OptionD, CorrectOption) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            for (Question q : questions) {
                ps.setInt(1, testID);
                ps.setString(2, q.getQuestionText());
                ps.setString(3, q.getOptionA());
                ps.setString(4, q.getOptionB());
                ps.setString(5, q.getOptionC());
                ps.setString(6, q.getOptionD());
                ps.setString(7, q.getCorrectOption());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    public List<Question> getQuestionsByTest(int testID) throws Exception {
        List<Question> list = new ArrayList<>();
        String sql = "SELECT * FROM Questions WHERE TestID = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, testID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Question q = new Question();
                    q.setQuestionID(rs.getInt("QuestionID"));
                    q.setTestId(rs.getInt("TestID"));
                    q.setQuestionText(rs.getString("QuestionText"));
                    q.setOptionA(rs.getString("OptionA"));
                    q.setOptionB(rs.getString("OptionB"));
                    q.setOptionC(rs.getString("OptionC"));
                    q.setOptionD(rs.getString("OptionD"));
                    q.setCorrectOption(rs.getString("CorrectOption"));
                    list.add(q);
                }
            }
        }
        return list;
    }
}