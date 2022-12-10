package com.example.webapp;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class ProductServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/html;charset=UTF-8");
        String[] paramNames = {"name", "price", "category", "description"};
        String[] params = new String[4];

        for (int i = 0; i < paramNames.length; i++) {
            params[i] = request.getParameter(paramNames[i]);
        }

        ProductDAO product = new ProductDAO();
        HttpSession session = request.getSession(true);
        String id = (String) session.getAttribute("editP");
        String action;
        if (id != null) {
            product.editProduct(params, id);
            action = "Edit";
        } else {
            product.insertNewProduct(params);
            action = "Insert";
        }
        session.setAttribute("editP", null);
        product.close();

        request.setAttribute("action", action);
        RequestDispatcher rd = request.getRequestDispatcher("/manageProduct.jsp");
        rd.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        doPost(request, response);
    }
}
