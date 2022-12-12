package com.example.webapp;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class HistoryServlet extends HttpServlet {

    private String getSearchId(String x) {
        String[] y = x.split("=");
        y[1] = y[1].substring(0, y[1].length() - 1);
        return y[1];
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/html;charset=UTF-8");
        String[] paramNames = {"customer", "startDate", "endDate"};
        //PrintWriter out = new PrintWriter(response.getWriter(), true);
        String cust = request.getParameter(paramNames[0]);
        String startD = request.getParameter(paramNames[1]);
        String endD = request.getParameter(paramNames[2]);
        String[] params = cust.split(" ");
        String custname = params[0];
        String custsurname = params[1];
        String custId = getSearchId(params[2]);

        String error_message = null;
        try {
            params[0] = getSearchId(params[0]);
        } catch (ArrayIndexOutOfBoundsException ignored) {
            error_message = "Customer not properly submitted";
            request.setAttribute("error", error_message);
            RequestDispatcher rd = request.getRequestDispatcher("/purchaseHistory.jsp");
            rd.forward(request, response);
        }

        CustomerDAO customerDAO = new CustomerDAO();
        ResultSet rs = customerDAO.searchCustomer(custId);
        try {
            if (!rs.next()) {
                error_message = "Client provided does not exist";
                request.setAttribute("error", error_message);
                RequestDispatcher rd = request.getRequestDispatcher("/purchaseHistory.jsp");
                rd.forward(request, response);
                return;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Sale sale = new Sale();
        ResultSet rs2 = sale.getAllCustomerSales(custId, startD, endD);
        request.setAttribute("customerSale", rs2);
        request.setAttribute("cust_name", custname);
        request.setAttribute("cust_surname", custsurname);
        request.setAttribute("SalesArraylist",createSalesArraylist(rs2));

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        doPost(request, response);
    }

    public ArrayList<String> createSalesArraylist(ResultSet rs) {
        try {
            ArrayList<String> customer_sales = new ArrayList<String>();
            while (rs.next()) {
                customer_sales.add(rs.getString("Prod_id"));
                customer_sales.add(rs.getString("Sale_Value"));
                customer_sales.add(rs.getString("Quantity"));
            }
            rs.close();
            return customer_sales;
        } catch (Exception e) {
            return null;
        }
    }

}