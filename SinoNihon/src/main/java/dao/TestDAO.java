package dao;

import model.EntranceTest;
import model.Question;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TestDAO {


    public int createTestWithQuestions(EntranceTest test, List<Question> questions) throws Exception {
        String insertTestSql = "INSERT INTO EntranceTests (Title, CreatedBy, Source) VALUES (?, ?, ?)";
        String insertQuestionSql = "INSERT INTO Questions (TestID, QuestionText, OptionA, OptionB, OptionC, OptionD, CorrectOption) VALUES (?, ?, ?, ?, ?, ?, ?)";

        Connection con = null;
        try {
            con = DBContext.getConnection();

            // Disable auto-commit to start a transaction
            con.setAutoCommit(false);

            // STEP 1: Insert the EntranceTest
            PreparedStatement psTest = con.prepareStatement(insertTestSql, java.sql.Statement.RETURN_GENERATED_KEYS);
            psTest.setString(1, test.getTitle());
            psTest.setInt(2, test.getCreatedBy());
            psTest.setString(3, test.getSource());
            psTest.executeUpdate();

            // Retrieve the auto-generated TestID
            int testId = -1;
            try (ResultSet rs = psTest.getGeneratedKeys()) {
                if (rs.next()) {
                    testId = rs.getInt(1);
                }
            }

            if (testId == -1) {
                throw new Exception("Failed to generate TestID.");
            }

            // STEP 2: Insert the Questions using Batch Processing
            PreparedStatement psQuestion = con.prepareStatement(insertQuestionSql);
            for (Question q : questions) {
                psQuestion.setInt(1, testId);
                psQuestion.setString(2, q.getQuestionText());
                psQuestion.setString(3, q.getOptionA());
                psQuestion.setString(4, q.getOptionB());
                psQuestion.setString(5, q.getOptionC());
                psQuestion.setString(6, q.getOptionD());
                psQuestion.setString(7, q.getCorrectOption());
                psQuestion.addBatch(); // Add to batch for bulk insertion
            }
            psQuestion.executeBatch(); // Execute all inserts at once

            // STEP 3: Commit transaction if everything is successful
            con.commit();

            // TRẢ VỀ ID CỦA BÀI TEST ĐỂ CONTROLLER SỬ DỤNG
            return testId;

        } catch (Exception e) {
            // Rollback if any error occurs
            if (con != null) {
                con.rollback();
            }
            throw new Exception("Transaction failed. Rolled back changes: " + e.getMessage());
        } finally {
            // Restore default commit behavior and close connection
            if (con != null) {
                con.setAutoCommit(true);
                con.close();
            }
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
                    q.setTestID(rs.getInt("TestID"));
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

    public List<EntranceTest> getAllTests() throws Exception {
        List<EntranceTest> list = new ArrayList<>();
        String sql = "SELECT * FROM EntranceTests ORDER BY CreatedAt DESC"; // Lấy bài mới nhất lên đầu

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                EntranceTest test = new EntranceTest();
                test.setTestID(rs.getInt("TestID"));
                test.setTitle(rs.getString("Title"));
                test.setCreatedAt(rs.getTimestamp("CreatedAt"));
                test.setCreatedBy(rs.getInt("CreatedBy"));
                test.setSource(rs.getString("Source"));
                list.add(test);
            }
        }
        return list;
    }
}