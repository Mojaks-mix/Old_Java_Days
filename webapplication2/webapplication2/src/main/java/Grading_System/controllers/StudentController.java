package Grading_System.controllers;

import Grading_System.Core.LoginRequired;
import Grading_System.service.StudentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.google.gson.*;

@WebServlet(urlPatterns = "/student/*")
@LoginRequired("student")
public class StudentController extends HttpServlet{
    private StudentService myService;

    public void init() {
        this.myService = new StudentService();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getPathInfo();

        if(action == null){
            getStudentGrads(request, response);
        }
        else if(action.equals("/drop")){
            drop(request, response);
        }
        else{
            request.getRequestDispatcher("/WEB-INF/views/error/404.jsp").forward(request, response);
        }
    }

    private void drop(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String courseId = request.getParameter("courseId");
        JsonObject jsonResponse = new JsonObject();
        if (courseId != null) {
            try {
                int id = Integer.parseInt(courseId);
                if (myService.dropCourse((Integer) request.getSession().getAttribute("user_id"), id)) {
                    jsonResponse.addProperty("status", 200);
                    jsonResponse.addProperty("message", "Course Has Been Dropped");
                } else {
                    jsonResponse.addProperty("status", 500);
                    jsonResponse.addProperty("message", "ERROR, Course IS NOT Dropped");
                }
            } catch (NumberFormatException e) {
                jsonResponse.addProperty("status", 500);
                jsonResponse.addProperty("message", "Invalid ID format");
                response.getWriter().write(new Gson().toJson(jsonResponse));
            }
        } else {
            jsonResponse.addProperty("status", 500);
            jsonResponse.addProperty("message", "ID parameter is missing");
        }

        response.getWriter().write(new Gson().toJson(jsonResponse));
    }

    private void getStudentGrads(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        request.setAttribute("grads", myService.getStudentGrads((Integer) request.getSession().getAttribute("user_id")));
        request.setAttribute("average", myService.getStudentAverage((Integer) request.getSession().getAttribute("user_id")));
        request.getRequestDispatcher("/WEB-INF/views/student/index.jsp").forward(request, response);
    }
}
