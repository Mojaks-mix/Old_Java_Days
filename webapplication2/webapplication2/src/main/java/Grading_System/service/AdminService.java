package Grading_System.service;

import Grading_System.dao.CourseDAO;
import Grading_System.dao.GradDAO;
import Grading_System.dao.UserDAO;
import Grading_System.model.Course;
import Grading_System.model.Grad;
import Grading_System.model.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminService {
    private final CourseDAO courseDAO = new CourseDAO();
    private final UserDAO userDAO = new UserDAO();

    public List<Course> getCourses(){
        return courseDAO.getAllCourses();
    }

    public boolean deleteCourse(int courseId){
        return courseDAO.deleteCourse(courseId);
    }

    public List<User> getInstructors(){
        return userDAO.getTeachers();
    }

    public boolean addCourse(String courseName){
        return courseDAO.addCourse(new Course(-1, courseName));
    }

    public boolean updateCourse(String courseName, int courseId){
        return courseDAO.updateCourse(new Course(courseId , courseName));
    }

    public String getCourseName(int courseId){
        return courseDAO.getCourseById(courseId).getCourseName();
    }

    public List<User> getUsers(){
        return userDAO.getAllUsers();
    }

    public String hashPass(String pass){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(pass.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean addUser(User user){
        return userDAO.addUser(user);
    }

    public boolean deleteUser(int userId){
        return userDAO.deleteUser(new User(userId, "", "", ""));
    }

    public User getUser(int Id){
        return userDAO.getUserById(Id);
    }

    public boolean updateUser(User user){
        if(user.getPassword() == null){
            user.setPassword(userDAO.getUserById(user.getUserId()).getPassword());
        }
        return userDAO.updateUser(user);
    }

    public Map<User,List<User>> getCourseInfo(int courseId){
        Map<User,List<User>> courseInfo = new HashMap<>();

        List<User> instructors = userDAO.getInstructorsOfCourse(courseId);

        for(User instructor : instructors) {
            courseInfo.put(instructor, userDAO.getUsersEnrolledInCourse(courseId, instructor.getUserId()));
        }

        return courseInfo;
    }
}
