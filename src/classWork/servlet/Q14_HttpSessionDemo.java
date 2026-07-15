package classWork.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/HttpSessionDemo")
public class Q14_HttpSessionDemo extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String name = request.getParameter("name");
        String email = request.getParameter("email");

        HttpSession session = request.getSession(true);
        session.setAttribute("userName", name);
        session.setAttribute("userEmail", email);
        session.setMaxInactiveInterval(300);

        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>HttpSession Demo</title></head><body>");
        out.println("<h2 style='color:green;'>Session Created Successfully!</h2>");
        out.println("<p><b>Session ID:</b> " + session.getId() + "</p>");
        out.println("<p><b>Name stored in session:</b> " + session.getAttribute("userName") + "</p>");
        out.println("<p><b>Email stored in session:</b> " + session.getAttribute("userEmail") + "</p>");
        out.println("<p><b>Max Inactive Interval:</b> " + session.getMaxInactiveInterval() + " seconds</p>");
        out.println("<br><a href='session_form.html'>Create New Session</a>");
        out.println("</body></html>");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession(false);

        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>HttpSession Demo</title></head><body>");

        if (session != null && session.getAttribute("userName") != null) {
            out.println("<h2>Existing Session Found</h2>");
            out.println("<p><b>Session ID:</b> " + session.getId() + "</p>");
            out.println("<p><b>Name:</b> " + session.getAttribute("userName") + "</p>");
            out.println("<p><b>Email:</b> " + session.getAttribute("userEmail") + "</p>");
        } else {
            out.println("<h2>No Active Session</h2>");
            out.println("<p>Please submit the form to create a session.</p>");
            out.println("<a href='session_form.html'>Go to Form</a>");
        }

        out.println("</body></html>");
    }
}
