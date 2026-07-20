package homeWork.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/ShoppingCart")
public class Q40c_ShoppingCartServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession(false);
        String user = null;
        if (session != null) {
            user = (String) session.getAttribute("loggedUser");
        }

        if (user == null) {
            response.sendRedirect("Q40b_login.jsp");
            return;
        }

        @SuppressWarnings("unchecked")
        ArrayList<String> cart = (ArrayList<String>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }

        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Shopping Cart</title></head>");
        out.println("<body>");
        out.println("<h2>Q40c - Shopping Cart</h2>");
        out.println("<h3 style='color:green;'>Welcome, " + user + "!</h3>");
        out.println("<p><b>Session ID:</b> " + session.getId() + "</p>");
        out.println("<p><b>Cart Items:</b> " + cart.size() + "</p>");

        if (cart.isEmpty()) {
            out.println("<p style='color:gray;'>Your cart is empty.</p>");
        } else {
            out.println("<table border='1' cellpadding='10' cellspacing='0'"
                    + " style='border-collapse:collapse; font-family:Arial;'>");
            out.println("<tr style='background-color:#4CAF50; color:white;'>");
            out.println("<th>#</th><th>Item</th></tr>");
            for (int i = 0; i < cart.size(); i++) {
                out.println("<tr><td>" + (i + 1) + "</td><td>" + cart.get(i) + "</td></tr>");
            }
            out.println("</table>");
        }

        out.println("<br>");
        out.println("<a href='Q40_products.html'>Continue Shopping</a> | ");

        if (!cart.isEmpty()) {
            out.println("<form action='ShoppingCart' method='post' style='display:inline;'>");
            out.println("<input type='hidden' name='action' value='clear'>");
            out.println("<input type='submit' value='Clear Cart'>");
            out.println("</form>");
        }

        out.println("<br><br><a href='Q40b_login.jsp'>Logout</a>");
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loggedUser") == null) {
            response.sendRedirect("Q40b_login.jsp");
            return;
        }

        String action = request.getParameter("action");

        @SuppressWarnings("unchecked")
        ArrayList<String> cart = (ArrayList<String>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }

        if ("add".equals(action)) {
            String[] items = request.getParameterValues("items");
            if (items != null) {
                for (String item : items) {
                    cart.add(item);
                }
            }
        } else if ("clear".equals(action)) {
            cart.clear();
        }

        response.sendRedirect("ShoppingCart");
    }
}
