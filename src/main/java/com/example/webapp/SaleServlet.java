package com.example.webapp;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        String error_message = null;
        try {
            params[0] = getSearchId(params[0]);
        } catch (ArrayIndexOutOfBoundsException ignored) {
            error_message = "Customer not properly submitted";
        }

        try {
            params[1] = getSearchId(params[1]);
        } catch (ArrayIndexOutOfBoundsException ignored) {
            if (error_message != null) {
                error_message = "Customer and Product not properly submitted";
            } else {
                error_message = "Product not properly submitted";
            }
        }

        if (error_message != null) {
            request.setAttribute("error", error_message);
            redirect(request, response);
            return;
        }

        ProductDAO product = new ProductDAO();
        double price = product.getProductPrice(params[1]);
        if (price == -1) {
            error_message = "Product provided does not exist";
            request.setAttribute("error", error_message);
            redirect(request, response);
            return;
        }
        CustomerDAO customerDAO = new CustomerDAO();
        ResultSet rs = customerDAO.searchCustomer(params[0]);
        try {
            if (!rs.next()) {
                error_message = "Client provided does not exist";
                request.setAttribute("error", error_message);
                redirect(request, response);
                return;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        int quantity = Integer.parseInt(params[3]);
        double salevalue = price * quantity;
        params[4] = String.valueOf(salevalue);
        product.close();
        SaleDAO saleDAO = new SaleDAO();
        saleDAO.insertNewSale(params);
        saleDAO.close();
        customerDAO.close();
        request.setAttribute("action", "true");
        redirect(request, response);
    }

    private void redirect(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("/manageSale.jsp");
        rd.forward(request, response);
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        doPost(request, response);
    }
}
