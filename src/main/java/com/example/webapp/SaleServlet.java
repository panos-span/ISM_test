package com.example.webapp;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SaleServlet extends HttpServlet {
    private String getSearchId(String x) {
        String[] y = x.split("=");
        y[1] = y[1].substring(0, y[1].length() - 1);
        return y[1];
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/html;charset=UTF-8");
        String[] paramNames = {"customer", "product", "date", "quantity"};
        String[] params = new String[5];
        for (int i = 0; i < 4; i++) {
            params[i] = request.getParameter(paramNames[i]);
        }
        params[0] = getSearchId(params[0]);
        params[1] = getSearchId(params[1]);
        ProductDAO product = new ProductDAO();
        double price = product.getProductPrice(params[1]);
        int quantity = Integer.parseInt(params[3]);
        double salevalue = price * quantity;
        params[4] = String.valueOf(salevalue);
        product.close();
        Sale sale = new Sale();
        sale.insertNewSale(params);
        sale.close();
        request.setAttribute("action", "true");
        RequestDispatcher rd = request.getRequestDispatcher("/manageSale.jsp");
        rd.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        doPost(request, response);
    }
}
