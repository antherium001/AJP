package homeWork.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/RedirectToHTML")
public class Q20_RedirectToHTML extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Redirect Demo</title></head><body>");
        out.println("<h2>Redirect to HTML Page</h2>");
        out.println("<p>Click below to redirect to the target HTML page:</p>");
        out.println("<a href='redirect_target.html'>Go to Target Page</a>");
        out.println("<br><br>");
        out.println("<p>Or click to redirect via sendRedirect():</p>");
        out.println("<form action='RedirectToHTMLRedir' method='get'>");
        out.println("<input type='submit' value='Redirect Now'>");
        out.println("</form>");
        out.println("</body></html>");
    }
}
