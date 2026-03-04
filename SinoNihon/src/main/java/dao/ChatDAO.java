package dao;

import model.ChatHistory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ChatDAO {

    public void saveMessage (ChatHistory chat) throws Exception{
        String sql = "Insert into ChatHistory (UserID, Feature, SenderType, Message) values (?, ?, ?, ?)";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, chat.getUserId());
            ps.setString(2, chat.getFeature());
            ps.setString(3, chat.getSenderType());
            ps.setString(4, chat.getMessage());
            ps.executeUpdate();
        }
    }

    public List<ChatHistory> getHistory (int userId, String feature) throws Exception{
        List<ChatHistory> history = new ArrayList<>();
        String sql = "Select * from ChatHistory where UserID = ? and Feature = ?";

        try (Connection con =  DBContext.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setString(2, feature);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ChatHistory chat = new ChatHistory();
                chat.setChatId(rs.getInt("chatId"));
                chat.setUserId(rs.getInt("UserID"));
                chat.setFeature(rs.getString("Feature"));
                chat.setSenderType(rs.getString("SenderType"));
                chat.setMessage(rs.getString("Message"));
                chat.setCreatedAt(rs.getTimestamp("CreatedAt"));
                history.add(chat);
            }
        }
        return history;
    }

    public void clearHistory(int userId) throws Exception {
        String sql = "DELETE FROM ChatHistory WHERE UserID = ?";

        try (Connection con = DBContext.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.executeUpdate();
        }
    }
}
