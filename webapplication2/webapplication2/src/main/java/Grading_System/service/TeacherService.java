package Grading_System.service;

import Grading_System.dao.CourseDAO;
import Grading_System.dao.GradDAO;
import Grading_System.dao.UserDAO;
import Grading_System.model.Course;
import Grading_System.model.Grad;
import Grading_System.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeacherService {
    private final GradDAO gradDAO = new GradDAO();
    private final CourseDAO courseDAO = new CourseDAO();
    private final UserDAO userDAO = new UserDAO();
    public List<Course> getCourses(int teacherId){
        return courseDAO.getTeacherCourses(teacherId);
    }

    public Map<String, Grad> getCourseStudents(int courseId, int teacherId){
        List<Grad> grads = gradDAO.getGradsByCourseAndTeacher(courseId, teacherId);
        Map<String, Grad> studentsGradsMap = new HashMap<>();

        for (Grad grad : grads) {
            User user = userDAO.getUserById(grad.getStudentId());
            if (user != null) {
                studentsGradsMap.put(user.getUserName(), grad);
            }
        }

        return studentsGradsMap;
    }

    public Grad getStudentGrad(int courseId, int studentId){
        Grad grad = gradDAO.getGradById(studentId, courseId);

        return grad;
    }

    public boolean markStudent(Grad mark){
        return gradDAO.updateGrad(mark);
    }
}
