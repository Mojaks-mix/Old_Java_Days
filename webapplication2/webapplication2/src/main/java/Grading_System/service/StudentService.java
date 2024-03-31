package Grading_System.service;

import Grading_System.dao.CourseDAO;
import Grading_System.dao.GradDAO;
import Grading_System.model.Course;
import Grading_System.model.Grad;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentService {
    private final GradDAO gradDAO = new GradDAO();
    private final CourseDAO courseDAO = new CourseDAO();

    public float getStudentAverage(int studentId){
        float average = 0, sum = 0;
        List<Grad> grads = gradDAO.getGradsByStudentId(studentId);
        for (Grad grad : grads) {
            switch (grad.getSymbol()){
                case "A":
                    sum += 4;
                    break;
                case "B":
                    sum += 3;
                    break;
                case "C":
                    sum += 2;
                    break;
                case "D":
                    sum += 1;
                    break;
                case "F":
                    sum += 0;
                    break;
            }
        }

        if(sum > 0){
            average = sum / grads.size();
        }
        return average;
    }

    public Map<String, Grad> getStudentGrads(int studentId) {
        List<Grad> grads = gradDAO.getGradsByStudentId(studentId);
        Map<String, Grad> studentGradsMap = new HashMap<>();

        for (Grad grad : grads) {
            Course course = courseDAO.getCourseById(grad.getCourseId());
            if (course != null) {
                studentGradsMap.put(course.getCourseName(), grad);
            }
        }

        return studentGradsMap;
    }
    public boolean dropCourse(int studentId, int courseId) {
        return gradDAO.deleteGrad(studentId, courseId);
    }
}
