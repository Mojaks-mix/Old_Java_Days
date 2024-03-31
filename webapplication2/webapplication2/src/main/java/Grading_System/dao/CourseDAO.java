package Grading_System.dao;

import Grading_System.model.Course;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO extends DAO {

    public CourseDAO() {
        super("Course");
    }

    public List<Course> getAllCourses() {
        //could be improved by pagination
        String sql = "SELECT * FROM " + table;
        List<Course> courses = new ArrayList<>();

        try (PreparedStatement preparedStatement = conn.prepareStatement(sql);) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int course_id = rs.getInt("course_id");
                String course_name = rs.getString("course_name");
                courses.add(new Course(course_id, course_name));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return courses;
    }

    public Course getCourseById(int courseId) {
        Course course = null;
        String sql = "SELECT * FROM " + table + " WHERE course_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, courseId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                course = new Course();
                course.setCourseId(rs.getInt("course_id"));
                course.setCourseName(rs.getString("course_name"));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return course;
    }

    public Course getCourseByName(String courseName) {
        Course course = null;
        String sql = "SELECT * FROM " + table + " WHERE course_name = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, courseName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                course = new Course();
                course.setCourseId(rs.getInt("course_id"));
                course.setCourseName(rs.getString("course_name"));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return course;
    }

    public boolean addCourse(Course course) {
        boolean rowAdded = false;
        String sql = "INSERT INTO " + table + " (course_name) VALUES (?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, course.getCourseName());

            rowAdded = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rowAdded;
    }

    public boolean updateCourse(Course course) {
        boolean rowUpdated = false;
        String sql = "UPDATE " + table + " SET course_name = ? WHERE course_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, course.getCourseName());
            ps.setInt(2, course.getCourseId());

            rowUpdated = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            printSQLException(e);
        }
        return rowUpdated;
    }

    public boolean deleteCourse(int courseId) {
        boolean rowDeleted = false;
        String sql = "DELETE FROM " + table + " WHERE course_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, courseId);

            rowDeleted = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            printSQLException(e);
        }
        return rowDeleted;
    }

    public List<Course> getTeacherCourses(int teacherId) {
        List<Course> courses = new ArrayList<>();

        // SQL query to select distinct course names where the teacher has graded
        String query = "SELECT DISTINCT c.course_id, c.course_name " +
                "FROM " + table + " c " +
                "INNER JOIN Grad g ON c.course_id = g.course_id " +
                "WHERE g.teacher_id = ?";

        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, teacherId);
            ResultSet resultSet = statement.executeQuery();

            // Iterate over the result set and add Course objects to the list
            while (resultSet.next()) {
                int courseId = resultSet.getInt("course_id");
                String courseName = resultSet.getString("course_name");

                Course course = new Course(courseId, courseName);
                courses.add(course);
            }
        }  catch (SQLException e) {
            printSQLException(e);
        }

        return courses;
    }
}