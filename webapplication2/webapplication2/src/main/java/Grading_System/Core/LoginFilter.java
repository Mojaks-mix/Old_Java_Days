package Grading_System.Core;

import Grading_System.service.AuthService;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebFilter(filterName = "LoginFilter", urlPatterns = {"/admin/*", "/teacher/*", "/student/*"})
public class LoginFilter implements Filter {
    private AuthService myService = new AuthService();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        HttpSession session = httpRequest.getSession(false);

        if (session == null || session.getAttribute("user_id") == null) {
            httpResponse.sendRedirect("http://localhost:8080/auth/login");
            return;
        }

        // Get user role from session
        String userRole = getUserRoleFromDatabase((Integer) session.getAttribute("user_id"));

        // Check if the requested servlet requires a specific role
        String servletPath = httpRequest.getServletPath();
        if (servletPath.startsWith("/admin") && !"admin".equals(userRole)) {
            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        } else if (servletPath.startsWith("/teacher") && !"teacher".equals(userRole)) {
            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        } else if (servletPath.startsWith("/student") && !"student".equals(userRole)) {
            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        chain.doFilter(request, response);
    }

    private String getUserRoleFromDatabase(Integer userId) {
        return myService.getUserRole(userId);
    }

}
