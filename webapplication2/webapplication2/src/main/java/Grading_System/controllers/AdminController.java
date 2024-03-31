package Grading_System.controllers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import Grading_System.Core.LoginRequired;
import Grading_System.model.User;
import Grading_System.service.AdminService;
import com.google.gson.*;

@WebServlet(urlPatterns = "/admin/*")
@LoginRequired("admin")
public class AdminController extends HttpServlet{
    private AdminService myService;

    public void init(){
        this.myService = new AdminService();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getPathInfo();
        if(action == null){
            viewUsers(request, response);
        }
        else if(action.equals("/courses")){
            viewCourses(request, response);
        }
        else if(action.equals("/add-course")){
            addCourse(request, response);
        }
        else if(action.equals("/edit-course")){
            editCourse(request, response);
        }
        else if(action.equals("/update-course")){
            updateCourse(request, response);
        }
        else if(action.equals("/delete-course")){
            deleteCourse(request, response);
        }
        else if(action.equals("/users")){
            viewUsers(request, response);
        }
        else if(action.equals("/add-user")){
            addUser(request, response);
        }
        else if(action.equals("/edit-user")){
            editUser(request, response);
        }
        else if(action.equals("/update-user")){
            updateUser(request, response);
        }
        else if(action.equals("/delete-user")){
            deleteUser(request, response);
        }
        else if (action.equals("/course-details")) {
            courseDetails(request, response);
        }
        else{
            request.getRequestDispatcher("/WEB-INF/views/error/404.jsp").forward(request, response);
        }
    }

    private void deleteCourse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String courseId = request.getParameter("courseId");
        JsonObject jsonResponse = new JsonObject();
        if (courseId != null) {
            try {
                int id = Integer.parseInt(courseId);
                if (myService.deleteCourse(id)) {
                    jsonResponse.addProperty("status", 200);
                    jsonResponse.addProperty("message", "Course Has Been Deleted");
                } else {
                    jsonResponse.addProperty("status", 500);
                    jsonResponse.addProperty("message", "ERROR, Course IS NOT Deleted");
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
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        response.getWriter().write(new Gson().toJson(jsonResponse));
    }

    private void addCourse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String courseName = request.getParameter("courseName");

        JsonObject jsonResponse = new JsonObject();

        if(myService.addCourse(courseName)) {
            jsonResponse.addProperty("status", 200);
        }
        else{
            jsonResponse.addProperty("status", 500);
            jsonResponse.addProperty("message", "Error, Something went wrong");
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        response.getWriter().write(new Gson().toJson(jsonResponse));
    }

    private void editCourse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        int courseId = Integer.parseInt(request.getParameter("courseId"));

        JsonObject jsonResponse = new JsonObject();

        if(myService.getCourseName(courseId) != null) {
            jsonResponse.addProperty("courseName", myService.getCourseName(courseId));
            jsonResponse.addProperty("courseId", courseId);
            jsonResponse.addProperty("status", 200);
        }
        else{
            jsonResponse.addProperty("status", 500);
            jsonResponse.addProperty("message", "Error, Something went wrong");
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        response.getWriter().write(new Gson().toJson(jsonResponse));
    }

    private void updateCourse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String courseName = request.getParameter("courseName");
        int courseId = Integer.parseInt(request.getParameter("courseId"));
        JsonObject jsonResponse = new JsonObject();

        if(myService.updateCourse(courseName, courseId)) {
            jsonResponse.addProperty("status", 200);
        }
        else{
            jsonResponse.addProperty("status", 500);
            jsonResponse.addProperty("message", "Error, Something went wrong");
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        response.getWriter().write(new Gson().toJson(jsonResponse));
    }

    private void viewCourses(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        request.setAttribute("courses", myService.getCourses());
        request.getRequestDispatcher("/WEB-INF/views/admin/courses.jsp").forward(request, response);
    }

    private void viewUsers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        request.setAttribute("users", myService.getUsers());
        request.getRequestDispatcher("/WEB-INF/views/admin/users.jsp").forward(request, response);
    }

    private void addUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String userName = request.getParameter("userName");
        String password_hash = myService.hashPass(request.getParameter("pass"));
        String role = request.getParameter("userRole");

        JsonObject jsonResponse = new JsonObject();

        if(myService.addUser(new User(-1, userName, password_hash, role))) {
            jsonResponse.addProperty("status", 200);
        }
        else{
            jsonResponse.addProperty("status", 500);
            jsonResponse.addProperty("message", "Error, Something went wrong");
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        response.getWriter().write(new Gson().toJson(jsonResponse));
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userId");
        JsonObject jsonResponse = new JsonObject();
        if (userId != null) {
            try {
                int id = Integer.parseInt(userId);
                if (myService.deleteUser(id)) {
                    jsonResponse.addProperty("status", 200);
                    jsonResponse.addProperty("message", "User Has Been Deleted");
                } else {
                    jsonResponse.addProperty("status", 500);
                    jsonResponse.addProperty("message", "ERROR, User IS NOT Deleted");
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
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        response.getWriter().write(new Gson().toJson(jsonResponse));
    }

    private void editUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        int userId = Integer.parseInt(request.getParameter("userId"));

        JsonObject jsonResponse = new JsonObject();

        User user = myService.getUser(userId);
        if(user != null) {
            jsonResponse.addProperty("userName", user.getUserName());
            jsonResponse.addProperty("userId", user.getUserId());
            jsonResponse.addProperty("role", user.getRole());
            jsonResponse.addProperty("status", 200);
        }
        else{
            jsonResponse.addProperty("status", 500);
            jsonResponse.addProperty("message", "Error, Something went wrong");
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        response.getWriter().write(new Gson().toJson(jsonResponse));
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        int userId = Integer.parseInt(request.getParameter("userId"));
        String userName = request.getParameter("userName");
        String role = request.getParameter("userRole");

        JsonObject jsonResponse = new JsonObject();

        if(myService.updateUser(new User(userId, userName, null, role))) {
            jsonResponse.addProperty("status", 200);
        }
        else{
            jsonResponse.addProperty("status", 500);
            jsonResponse.addProperty("message", "Error, Something went wrong");
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        response.getWriter().write(new Gson().toJson(jsonResponse));
    }

    private void courseDetails(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        int courseId = Integer.parseInt(request.getParameter("courseId"));
        request.setAttribute("courseInfo", myService.getCourseInfo(courseId));
        request.setAttribute("courseName", myService.getCourseName(courseId));
        request.setAttribute("courseId", courseId);
        request.getRequestDispatcher("/WEB-INF/views/admin/course_details.jsp").forward(request, response);
    }
}
