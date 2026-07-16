package homeWork.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/CookieDemo")
public class Q19_CookieDemo extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String action = request.getParameter("action");

        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Cookie Demo</title></head><body>");

        if ("add".equals(action)) {
            String key = request.getParameter("key");
            String value = request.getParameter("value");

            if (key != null && value != null && !key.isEmpty() && !value.isEmpty()) {
                Cookie cookie = new Cookie(key, value);
                cookie.setMaxAge(3600);
                response.addCookie(cookie);
                out.println("<h2 style='color:green;'>Cookie Added Successfully!</h2>");
                out.println("<p><b>Name:</b> " + key + "</p>");
                out.println("<p><b>Value:</b> " + value + "</p>");
            } else {
                out.println("<h2 style='color:red;'>Please provide both key and value.</h2>");
            }
        }

        out.println("<h3>All Cookies:</h3>");
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            out.println("<table border='1' cellpadding='10' style='border-collapse:collapse;'>");
            out.println("<tr><th>Cookie Name</th><th>Cookie Value</th><th>Max Age</th></tr>");
            for (Cookie cookie : cookies) {
                out.println("<tr>");
                out.println("<td>" + cookie.getName() + "</td>");
                out.println("<td>" + cookie.getValue() + "</td>");
                out.println("<td>" + cookie.getMaxAge() + " sec</td>");
                out.println("</tr>");
            }
            out.println("</table>");
        } else {
            out.println("<p>No cookies found.</p>");
        }

        out.println("<br><a href='cookie_form.html'>Add New Cookie</a>");
        out.println("</body></html>");
    }
}
