package Grading_System.dao;

import Grading_System.model.User;

import java.sql.ResultSet;


import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends DAO {

    public UserDAO() {
        super("User");
    }

    public List<User> getAllUsers() {
        //could be improved by pagination
        String sql = "SELECT * FROM " + table;
        List<User> users = new ArrayList<>();

        try (PreparedStatement preparedStatement = conn.prepareStatement(sql);) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int user_id = rs.getInt("user_id");
                String username = rs.getString("username");
                String role = rs.getString("role");
                users.add(new User(user_id, username, null, role));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return users;
    }

    public User getUserById(int userId) {
        User user = null;
        String sql = "SELECT * FROM " + table + " WHERE user_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password_hash"));
                user.setRole(rs.getString("role"));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return user;
    }

    public User getUserByName(String userName) {
        User user = null;
        String sql = "SELECT * FROM " + table + " WHERE username = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password_hash"));
                user.setRole(rs.getString("role"));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return user;
    }

    public boolean addUser(User user) {
        boolean rowAdded = false;
        String sql = "INSERT INTO " + table + " (username, password_hash, role) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRole());

            rowAdded = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            printSQLException(e);
        }

        return rowAdded;
    }

    public boolean updateUser(User user) {
        boolean rowUpdated = false;
        String sql = "UPDATE " + table + " SET username = ?,password_hash= ?, role =? WHERE user_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRole());
            ps.setInt(4, user.getUserId());

            rowUpdated = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            printSQLException(e);
        }
        return rowUpdated;
    }

    public boolean deleteUser(User user) {
        boolean rowDeleted = false;
        String sql = "DELETE FROM " + table + " WHERE user_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, user.getUserId());

            rowDeleted = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            printSQLException(e);
        }
        return rowDeleted;
    }

    public List<User> getTeachers() {
        //could be improved by pagination
        String sql = "SELECT user_id, username FROM " + table + " WHERE role = 'teacher'";
        List<User> users = new ArrayList<>();

        try (PreparedStatement preparedStatement = conn.prepareStatement(sql);) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int user_id = rs.getInt("user_id");
                String username = rs.getString("username");
                users.add(new User(user_id, username, null, "teacher"));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return users;
    }

    public List<User> getInstructorsOfCourse(int courseId){
        List<User> users = new ArrayList<>();
        String stmt = "SELECT DISTINCT u.user_id, u.username " +
                "FROM " + table + " u " +
                "INNER JOIN Grad g ON u.user_id = g.teacher_id " +
                "WHERE g.course_id = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(stmt)) {
            preparedStatement.setInt(1, courseId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                String username = resultSet.getString("username");
                users.add(new User(userId, username, null, null));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }

        return users;
    }

    public List<User> getUsersEnrolledInCourse(int courseId, int teacherId){
        List<User> students = new ArrayList<>();
        String stmt = "SELECT u.user_id, u.username " +
                "FROM " + table + " u " +
                "INNER JOIN Grad g ON u.user_id = g.student_id " +
                "WHERE g.course_id = ? AND g.teacher_id = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(stmt)) {
            preparedStatement.setInt(1, courseId);
            preparedStatement.setInt(2, teacherId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                String username = resultSet.getString("username");
                students.add(new User(userId, username, null, null));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }

        return students;
    }
}
