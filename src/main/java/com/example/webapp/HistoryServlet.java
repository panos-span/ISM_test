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

        String cust = request.getParameter(paramNames[0]);
        String startD = request.getParameter(paramNames[1]);
        String endD = request.getParameter(paramNames[2]);

        if (startD.equals("")) {
            startD = null;
        }
        if (endD.equals("")) {
            endD = null;
        }

        String custId;
        String error_message;
        try {
            custId = getSearchId(cust);
        } catch (ArrayIndexOutOfBoundsException ignored) {
            error_message = "Customer not properly submitted";
            request.setAttribute("error", error_message);
            RequestDispatcher rd = request.getRequestDispatcher("/purchaseHistory.jsp");
            rd.forward(request, response);
            return;
        }
        String[] params = cust.split(" ");
        String custname = params[0];
        String custsurname = params[1];

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
        } finally {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        customerDAO.close();
        SaleDAO saleDAO = new SaleDAO();
        ResultSet rs2 = saleDAO.getAllCustomerSales(custId, startD, endD);
        //request.setAttribute("customerSale", rs2);
        request.setAttribute("cust_name", custname);
        request.setAttribute("cust_surname", custsurname);
        request.setAttribute("SalesArraylist", createSalesArraylist(rs2));
        saleDAO.close();
        RequestDispatcher rd = request.getRequestDispatcher("/purchaseHistory.jsp");
        rd.forward(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        doPost(request, response);
    }

    public ArrayList<Sale> createSalesArraylist(ResultSet rs) {
        try {
            ArrayList<Sale> customer_sales = new ArrayList<>();
            while (rs.next()) {
                customer_sales.add(new Sale(rs.getString("Prod_id"),
                        rs.getDouble("Sale_Value"), rs.getInt("Quantity")));
            }
            rs.close();
            return customer_sales;
        } catch (Exception e) {
            return null;
        }
    }

}