package dao;

import model.User;
import java.sql.*;

public class UserDAO {

    public void register(User u) throws Exception {
        String sql = """
            INSERT INTO Users (Username, Password, FullName, Phone, Email, IsVerified)
            VALUES (?, ?, ?, ?, ?, 1)
        """;

        try (Connection con = DBContext.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, u.getUsername());
            ps.setString(2, u.getPassword());
            ps.setString(3, u.getFullName());
            ps.setString(4, u.getPhone());
            ps.setString(5, u.getEmail());

            ps.executeUpdate();
        }
    }

    public User login(String username, String password) throws Exception {
        String sql = "SELECT * FROM Users WHERE Username=? AND Password=?";

        try (Connection con = DBContext.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User u = new User();
                u.setUserId(rs.getInt("User_id"));
                u.setUsername(rs.getString("Username"));
                u.setFullName(rs.getString("FullName"));
                u.setPhone(rs.getString("Phone"));
                u.setEmail(rs.getString("Email"));
                u.setRole(rs.getString("Role"));
                return u;
            }
        }
        return null;
    }

    public void updateProfile(User u) throws Exception {
        String sql = """
            UPDATE Users
            SET FullName = ?, Phone = ?, Email = ?
            WHERE User_id = ?
        """;

        try (Connection con = DBContext.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, u.getFullName());
            ps.setString(2, u.getPhone());
            ps.setString(3, u.getEmail());
            ps.setInt(4, u.getUserId());

            ps.executeUpdate();
        }
    }

    // ✅ CHECK PASSWORD CŨ
    public boolean checkOldPassword(int userId, String oldPassword) throws Exception {
        String sql = "SELECT 1 FROM Users WHERE User_id=? AND Password=?";

        try (Connection con = DBContext.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setString(2, oldPassword);

            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
    }

    // ✅ UPDATE PASSWORD
    public void changePassword(int userId, String newPassword) throws Exception {
        String sql = "UPDATE Users SET Password=? WHERE User_id=?";

        try (Connection con = DBContext.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, newPassword);
            ps.setInt(2, userId);
            ps.executeUpdate();
        }
    }
}
