package Grading_System.dao;

import Grading_System.model.Course;
import Grading_System.model.Grad;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GradDAO extends DAO {

    public GradDAO() {
        super("Grad");
    }

    // Method to retrieve a mark by student ID and course ID
    public Grad getGradById(int studentId, int courseId) {
        String query = "SELECT * FROM " + table + " WHERE student_id = ? AND course_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, studentId);
            stmt.setInt(2, courseId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Grad mark = new Grad();
                    mark.setStudentId(rs.getInt("student_id"));
                    mark.setCourseId(rs.getInt("course_id"));
                    mark.setFirstMark(rs.getInt("first_mark"));
                    mark.setSecondMark(rs.getInt("second_mark"));
                    mark.setFinalMark(rs.getInt("final_mark"));
                    mark.setSymbol(rs.getString("symbol"));
                    return mark;
                }
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return null;
    }

    public List<Grad> getGradsByStudentId(int studentId) {
        List<Grad> marks = new ArrayList<>();
        String query = "SELECT * FROM " + table + " WHERE student_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, studentId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Grad mark = new Grad();
                    mark.setStudentId(rs.getInt("student_id"));
                    mark.setCourseId(rs.getInt("course_id"));
                    mark.setFirstMark(rs.getInt("first_mark"));
                    mark.setSecondMark(rs.getInt("second_mark"));
                    mark.setFinalMark(rs.getInt("final_mark"));
                    mark.setSymbol(rs.getString("symbol"));
                    marks.add(mark);
                }
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return marks;
    }

    public boolean addGrad(Grad mark) {
        boolean rowAdded = false;
        String query = "INSERT INTO " + table + " (student_id, course_id, first_mark, second_mark, final_mark, symbol, teacher_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, mark.getStudentId());
            stmt.setInt(2, mark.getCourseId());
            stmt.setInt(3, mark.getFirstMark());
            stmt.setInt(4, mark.getSecondMark());
            stmt.setInt(5, mark.getFinalMark());
            stmt.setString(6, mark.getSymbol());
            stmt.setInt(7, mark.getTeacherID());

            rowAdded = stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            printSQLException(e);
        }

        return rowAdded;
    }

    public boolean updateGrad(Grad mark) {
        boolean rowUpdated = false;

        String query = "UPDATE " + table + " SET first_mark = ?, second_mark = ?, final_mark = ?, symbol = ? WHERE student_id = ? AND course_id = ? AND teacher_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, mark.getFirstMark());
            stmt.setInt(2, mark.getSecondMark());
            stmt.setInt(3, mark.getFinalMark());
            stmt.setString(4, mark.getSymbol());
            stmt.setInt(5, mark.getStudentId());
            stmt.setInt(6, mark.getCourseId());
            stmt.setInt(7, mark.getTeacherID());


            rowUpdated = stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            printSQLException(e);
        }

        return rowUpdated;
    }

    public boolean clearGrad(int studentId, int courseId) {
        boolean rowCleared = false;

        String query = "UPDATE " + table + " SET first_mark = NULL, second_mark = NULL, final_mark = NULL, symbol = NULL WHERE student_id = ? AND course_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, studentId);
            stmt.setInt(2, courseId);

            rowCleared = stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            printSQLException(e);
        }

        return rowCleared;
    }

    public boolean deleteGrad(int studentId, int courseId) {
        boolean rowDeleted = false;

        String query = "DELETE FROM " + table + " WHERE student_id = ? AND course_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, studentId);
            stmt.setInt(2, courseId);

            rowDeleted = stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            printSQLException(e);
        }

        return rowDeleted;
    }

    public List<Grad> getGradsByCourseAndTeacher(int courseId, int teacherId) {
        List<Grad> grads = new ArrayList<>();
        String query = "SELECT * FROM Grad WHERE course_id = ? AND teacher_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setInt(1, courseId);
            stmt.setInt(2, teacherId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Grad mark = new Grad();
                    mark.setStudentId(rs.getInt("student_id"));
                    mark.setCourseId(rs.getInt("course_id"));
                    mark.setFirstMark(rs.getInt("first_mark"));
                    mark.setSecondMark(rs.getInt("second_mark"));
                    mark.setFinalMark(rs.getInt("final_mark"));
                    mark.setSymbol(rs.getString("symbol"));
                    grads.add(mark);
                }
            }
        }catch (SQLException e) {
            printSQLException(e);
        }

        return grads;
    }
}