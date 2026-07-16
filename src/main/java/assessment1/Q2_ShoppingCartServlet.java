package assessment1;

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
public class Q2_ShoppingCartServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession(false);
        ArrayList<String> cart = (session != null) ? (ArrayList<String>) session.getAttribute("cart") : null;

        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Shopping Cart</title></head><body>");
        out.println("<h2 style='text-align:center; color:#333;'>Your Shopping Cart</h2>");

        if (cart != null && !cart.isEmpty()) {
            out.println("<table border='1' cellpadding='10' cellspacing='0' "
                    + "style='margin:auto; border-collapse:collapse; font-family:Arial;'>");
            out.println("<tr style='background-color:#4CAF50; color:white;'>");
            out.println("<th>#</th><th>Item</th>");
            out.println("</tr>");
            for (int i = 0; i < cart.size(); i++) {
                out.println("<tr><td>" + (i + 1) + "</td><td>" + cart.get(i) + "</td></tr>");
            }
            out.println("</table>");
            out.println("<p style='text-align:center; font-family:Arial;'>Total items in cart: <b>" + cart.size() + "</b></p>");
        } else {
            out.println("<p style='text-align:center; font-family:Arial; color:#999;'>Cart is empty. Add some items!</p>");
        }

        out.println("<br><div style='text-align:center; font-family:Arial;'>");
        out.println("<a href='shopping_cart.html'>Add More Items</a>");
        out.println(" | ");
        out.println("<form action='ShoppingCart' method='post' style='display:inline;'>");
        out.println("<input type='hidden' name='action' value='clear'>");
        out.println("<input type='submit' value='Clear Cart' style='color:red; background:none; border:none; cursor:pointer; font-size:16px; text-decoration:underline;'>");
        out.println("</form>");
        out.println("</div>");

        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String action = request.getParameter("action");

        HttpSession session = request.getSession(true);
        ArrayList<String> cart = (ArrayList<String>) session.getAttribute("cart");

        if (cart == null) {
            cart = new ArrayList<>();
        }

        if ("clear".equals(action)) {
            cart.clear();
            session.setAttribute("cart", cart);

            out.println("<!DOCTYPE html>");
            out.println("<html><head><title>Cart Cleared</title></head><body>");
            out.println("<h2 style='text-align:center; color:red;'>Cart has been cleared!</h2>");
            out.println("<p style='text-align:center; font-family:Arial;'>");
            out.println("<a href='shopping_cart.html'>Add Items</a>");
            out.println("</p>");
            out.println("</body></html>");
            return;
        }

        String[] items = request.getParameterValues("items");

        if (items != null) {
            for (String item : items) {
                cart.add(item);
            }
        }

        session.setAttribute("cart", cart);

        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Items Added</title></head><body>");
        out.println("<h2 style='text-align:center; color:green;'>Items Added to Cart!</h2>");

        if (items != null) {
            out.println("<p style='text-align:center; font-family:Arial;'>Added " + items.length + " item(s):</p>");
            out.println("<ul style='font-family:Arial;'>");
            for (String item : items) {
                out.println("<li>" + item + "</li>");
            }
            out.println("</ul>");
        } else {
            out.println("<p style='text-align:center; font-family:Arial;'>No items selected.</p>");
        }

        out.println("<p style='text-align:center; font-family:Arial;'>Total items in cart: <b>" + cart.size() + "</b></p>");
        out.println("<p style='text-align:center; font-family:Arial;'>");
        out.println("<a href='ShoppingCart'>View Cart</a> | ");
        out.println("<a href='shopping_cart.html'>Add More Items</a>");
        out.println("</p>");
        out.println("</body></html>");
    }
}
