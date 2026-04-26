package ebookreader;

import java.sql.*;

public class DatabaseManager {

    private static final String URL      = "jdbc:mysql://localhost:3306/ebookreader";
    private static final String USER     = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static boolean loginUser(String username, String password) {
        String sql = "SELECT * FROM users WHERE username=? AND password=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Login error: " + e.getMessage());
            return false;
        }
    }

    public static int getUserId(String username) {
        String sql = "SELECT id FROM users WHERE username=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("id");
        } catch (SQLException e) {
            System.out.println("Get user error: " + e.getMessage());
        }
        return -1;
    }

    public static boolean registerUser(String fullName,
                                        String username,
                                        String password) {
        if (usernameExists(username)) return false;
        String sql = "INSERT INTO users (full_name, username, password) " +
                     "VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, fullName);
            ps.setString(2, username);
            ps.setString(3, password);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Register error: " + e.getMessage());
            return false;
        }
    }

    public static boolean usernameExists(String username) {
        String sql = "SELECT id FROM users WHERE username=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Check username error: " + e.getMessage());
            return false;
        }
    }

    public static boolean addBook(String title, String author,
                                   String filePath, String fileSize,
                                   int userId) {
        String sql = "INSERT INTO books " +
                     "(title, author, file_path, file_size, user_id) " +
                     "VALUES (?,?,?,?,?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, title);
            ps.setString(2, author);
            ps.setString(3, filePath);
            ps.setString(4, fileSize);
            ps.setInt(5, userId);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Add book error: " + e.getMessage());
            return false;
        }
    }

    public static ResultSet getAllBooks(int userId) {
        String sql = "SELECT * FROM books WHERE user_id=? " +
                     "ORDER BY added_date DESC";
        try {
            Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            return ps.executeQuery();
        } catch (SQLException e) {
            System.out.println("Get books error: " + e.getMessage());
            return null;
        }
    }

    public static ResultSet searchBooks(String keyword, int userId) {
        String sql = "SELECT * FROM books WHERE user_id=? " +
                     "AND (title LIKE ? OR author LIKE ?)";
        try {
            Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setString(2, "%" + keyword + "%");
            ps.setString(3, "%" + keyword + "%");
            return ps.executeQuery();
        } catch (SQLException e) {
            System.out.println("Search error: " + e.getMessage());
            return null;
        }
    }

    public static boolean updateStatus(int bookId, String status) {
        String sql = "UPDATE books SET status=? WHERE id=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, bookId);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Update status error: " + e.getMessage());
            return false;
        }
    }

    public static boolean deleteBook(int bookId) {
        String sql = "DELETE FROM books WHERE id=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, bookId);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Delete error: " + e.getMessage());
            return false;
        }
    }

    public static String getFilePath(int bookId) {
        String sql = "SELECT file_path FROM books WHERE id=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, bookId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getString("file_path");
        } catch (SQLException e) {
            System.out.println("Get path error: " + e.getMessage());
        }
        return "";
    }
}