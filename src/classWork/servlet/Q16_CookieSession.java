package classWork.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/CookieSession")
public class Q16_CookieSession extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession(true);

        Cookie[] cookies = request.getCookies();

        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Cookie and Session ID Demo</title></head><body>");
        out.println("<h2>Session and Cookie Information</h2>");

        out.println("<h3>Session Details:</h3>");
        out.println("<table border='1' cellpadding='10' style='border-collapse:collapse;'>");
        out.println("<tr><th>Session ID</th><td>" + session.getId() + "</td></tr>");
        out.println("<tr><th>Is New Session</th><td>" + session.isNew() + "</td></tr>");
        out.println("<tr><th>Creation Time</th><td>" + new java.util.Date(session.getCreationTime()) + "</td></tr>");
        out.println("<tr><th>Last Accessed</th><td>" + new java.util.Date(session.getLastAccessedTime()) + "</td></tr>");
        out.println("</table>");

        out.println("<h3>Cookies:</h3>");
        if (cookies != null && cookies.length > 0) {
            out.println("<table border='1' cellpadding='10' style='border-collapse:collapse;'>");
            out.println("<tr><th>Cookie Name</th><th>Cookie Value</th><th>Max Age</th><th>Path</th></tr>");
            for (Cookie cookie : cookies) {
                out.println("<tr>");
                out.println("<td>" + cookie.getName() + "</td>");
                out.println("<td>" + cookie.getValue() + "</td>");
                out.println("<td>" + cookie.getMaxAge() + " seconds</td>");
                out.println("<td>" + cookie.getPath() + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");
        } else {
            out.println("<p>No cookies found. Session cookie (JSESSIONID) is managed by the container.</p>");
        }

        out.println("<p><b>Servlet Session ID (JSESSIONID):</b> " + session.getId() + "</p>");
        out.println("</body></html>");
    }
}
