package Grading_System.controllers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import Grading_System.model.User;
import Grading_System.service.AuthService;
import com.google.gson.*;

@WebServlet(urlPatterns = "/auth/*")
public class AuthController extends HttpServlet{
    private AuthService myService;

    public void init(){
        this.myService = new AuthService();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getPathInfo();
        if(action == null){
            logIn(request, response);
        }
        else if(action.equals("/login")){
            logIn(request, response);
        }
        else if(action.equals("/verify")){
            verify(request, response);
        }
        else if(action.equals("/logout")){
            logOut(request, response);
        }
        else{
            request.getRequestDispatcher("/WEB-INF/views/error/404.jsp").forward(request, response);
        }
    }
    private void logIn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }

    private void logOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        request.getSession().invalidate();
        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }

    private void verify(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String username = request.getParameter("userName");
        String password = myService.hashPass(request.getParameter("password"));

        if(username != null && password != null) {
            if (myService.verify(username, password)) {
                User user = myService.getUserByName(username);
                request.getSession().setAttribute("user_id", user.getUserId());
                request.getSession().setAttribute("username", user.getUserName());
                switch (user.getRole()) {
                    case "admin":
                        response.sendRedirect( "/admin");
                        break;
                    case "teacher":
                        response.sendRedirect( "/teacher");
                        break;
                    case "student":
                        response.sendRedirect( "/student");
                        break;
                }
            } else {
                request.setAttribute("errorMessage", "Incorrect credentials");
                request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            }
        }
        else{
            request.setAttribute("errorMessage", "Username and password are required");
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
        }
    }
}
