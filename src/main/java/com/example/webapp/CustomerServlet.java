package com.example.webapp;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import jakarta.servlet.http.HttpSession;
import java.io.PrintWriter;

public class CustomerServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/html;charset=UTF-8");
        String[] paramNames = {"name", "surname", "vat", "email", "details"};
        String[] phoneNames = {"phone_1", "phone_2"};
        String[] params = new String[6];
        String[] phones = new String[2];
        params[3] = request.getParameter("street") + ", " + request.getParameter("city");
        int inputs = 0;
        for (int i = 0; i <= paramNames.length; i++) {
            if (i == 3) {
                continue;
            }
            params[i] = request.getParameter(paramNames[inputs]);
            inputs++;
        }

        inputs = 0;
        for (String phone : phoneNames) {
            phones[inputs] = request.getParameter(phone);
            inputs++;
        }

        CustomerDAO customer = new CustomerDAO();
        HttpSession session = request.getSession(true);
        String id = (String) session.getAttribute("edit");
        String action;
        if (id != null) {
            customer.editCustomer(params, phones, id);
            action = "Edit";
        } else {
            customer.insertNewCustomer(params, phones);
            action = "Insert";
        }
        session.setAttribute("edit", null);
        customer.close();

        request.setAttribute("action", action);
        RequestDispatcher rd = request.getRequestDispatcher("/manageCustomer.jsp");
        rd.forward(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        /*
         * If receive any get request redirect user back to login form.
         */
        doPost(request, response);
    }

}