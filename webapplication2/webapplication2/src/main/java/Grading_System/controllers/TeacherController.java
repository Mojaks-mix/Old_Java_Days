package Grading_System.controllers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import Grading_System.Core.LoginRequired;
import Grading_System.model.Grad;
import Grading_System.service.TeacherService;
import com.google.gson.*;

@WebServlet(urlPatterns = "/teacher/*")
@LoginRequired("teacher")
public class TeacherController extends HttpServlet{

    private TeacherService myService;

    public void init(){
        this.myService = new TeacherService();
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getPathInfo();
        if(action == null){
            getTeacherCourses(request, response);
        }
        else if(action.equals("/course-students")){
            getCourseStudents(request, response);
        }
        else if(action.equals("/mark-student")){
            markStudent(request, response);
        }
        else if(action.equals("/update-mark")){
            updateStudentMark(request, response);
        }
        else{
            request.getRequestDispatcher("/WEB-INF/views/error/404.jsp").forward(request, response);
        }
    }

    private void getTeacherCourses(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        request.setAttribute("courses", myService.getCourses((Integer) request.getSession().getAttribute("user_id")));
        request.getRequestDispatcher("/WEB-INF/views/teacher/index.jsp").forward(request, response);
    }

    private void getCourseStudents(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        int courseId = Integer.parseInt(request.getParameter("courseId"));
        request.setAttribute("grads" ,myService.getCourseStudents(courseId, (Integer) request.getSession().getAttribute("user_id")));
        request.getRequestDispatcher("/WEB-INF/views/teacher/course_students.jsp").forward(request, response);
    }

    private void markStudent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        // Retrieve parameters from the URL
        int courseId = Integer.parseInt(request.getParameter("courseId"));
        int studentId = Integer.parseInt(request.getParameter("studentId"));

        JsonObject jsonResponse = new JsonObject();
        Gson gson = new Gson();

        if (myService.getStudentGrad(courseId, studentId) != null) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            response.getWriter().write(gson.toJson(myService.getStudentGrad(courseId, studentId)));
        }
        else {
            jsonResponse.addProperty("status", 422);
            jsonResponse.addProperty("message", "Error, Some thing went wrong");
        }
    }

    private void updateStudentMark(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        int courseId = Integer.parseInt(request.getParameter("courseId"));
        int studentId = Integer.parseInt(request.getParameter("studentId"));
        int teacherID = (Integer) request.getSession().getAttribute("user_id");
        int firstMark = Integer.parseInt((request.getParameter("firstMark") == null) ? "0" : request.getParameter("firstMark"));
        int secondMark = Integer.parseInt((request.getParameter("secondMark") == null) ? "0" : request.getParameter("secondMark"));
        int finalMark = Integer.parseInt((request.getParameter("finalMark") == null) ? "0" : request.getParameter("finalMark"));
        String symbol = request.getParameter("symbol");

        Grad mark = new Grad(studentId, teacherID, courseId, firstMark, secondMark, finalMark, symbol);

        JsonObject jsonResponse = new JsonObject();

        if(myService.markStudent(mark) && (request.getParameter("updated").equals("true"))) {
            jsonResponse.addProperty("status", 200);
        }
        else{
            jsonResponse.addProperty("status", 500);
            jsonResponse.addProperty("message", "Error, Something went wrong");
        }
        response.getWriter().write(new Gson().toJson(jsonResponse));
    }
}
