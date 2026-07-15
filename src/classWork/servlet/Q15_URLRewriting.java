package classWork.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/URLRewriting")
public class Q15_URLRewriting extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String name = request.getParameter("name");
        String city = request.getParameter("city");

        HttpSession session;
        if (name != null && city != null) {
            session = request.getSession(true);
            session.setAttribute("urlName", name);
            session.setAttribute("urlCity", city);
        } else {
            session = request.getSession(false);
        }

        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>URL Rewriting Demo</title></head><body>");

        if (session != null && session.getAttribute("urlName") != null) {
            out.println("<h2>Session Data (via URL Rewriting)</h2>");
            out.println("<p><b>Session ID:</b> " + session.getId() + "</p>");
            out.println("<p><b>Name:</b> " + session.getAttribute("urlName") + "</p>");
            out.println("<p><b>City:</b> " + session.getAttribute("urlCity") + "</p>");

            String rewrittenURL = response.encodeURL("URLRewriting");
            out.println("<p><b>Rewritten URL:</b> " + rewrittenURL + "</p>");
            out.println("<p><a href='" + rewrittenURL + "'>Refresh (preserves session via URL)</a></p>");
        } else {
            out.println("<h2>No data found. Please submit via the form.</h2>");
            out.println("<a href='urlrewrite_form.html'>Go to Form</a>");
        }

        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
