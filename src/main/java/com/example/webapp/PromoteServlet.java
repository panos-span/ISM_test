package com.example.webapp;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class PromoteServlet extends HttpServlet {


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/html;charset=UTF-8");
        boolean redirect = checkForAddedClient(request);
        if (checkForAddedProduct(request)) {
            redirect = true;
        }
        if (redirect) {
            RequestDispatcher rd = request.getRequestDispatcher("/promote.jsp");
            rd.forward(request, response);
        }

        PrintWriter out = new PrintWriter(response.getWriter(), true);
        String client_number = request.getParameter("client_number");
        String product_number = request.getParameter("product_number");
        for (int i = 1; i <= Integer.parseInt(client_number); i++) {
            out.println(request.getParameter(String.format("remove_client%d", i)));
        }
        for (int i = 1; i <= Integer.parseInt(product_number); i++) {
            out.println(request.getParameter(String.format("remove_product%d", i)));
        }
        /*out.println();
        for (Customer cust : Promote.getCustomers()){
            out.println(cust);
        }
        System.out.println("--------------------------------");
        for (Product prod : Promote.getProducts()){
            out.println(prod);
        }*/
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        doPost(request, response);
    }

    private String getSearchId(String x) {
        String[] y = x.split("=");
        y[1] = y[1].substring(0, y[1].length() - 1);
        return y[1];
    }

    private boolean checkForAddedClient(HttpServletRequest request) {
        String cust = request.getParameter("customer");
        boolean f = false;
        if (cust != null) {
            String[] params = cust.split(" ");
            params[2] = getSearchId(params[2]);
            Customer customer = new Customer(params[2], params[0], params[1]);
            if (!Promote.getCustomers().contains(customer)) {
                Promote.addCustomer(customer);
            }
            f = true;
        }
        return f;
    }

    /**
     * !Note: while checking if product/customer exists, you can get name and other useful elements
     *
     * @param request
     * @return
     */
    private boolean checkForAddedProduct(HttpServletRequest request) {
        String prod = request.getParameter("product");
        boolean f = false;
        if (prod != null) {
            String[] params = prod.split(" ");
            params[params.length - 1] = getSearchId(params[params.length - 1]);
            ProductDAO productDAO = new ProductDAO();
            double price = productDAO.getProductPrice(params[params.length - 1]);
            String name = "";
            for (int i = 0; i < params.length - 1; i++) {
                name += params[i] + " ";
            }
            Product product = new Product(params[params.length - 1], name, price);
            if(!Promote.getProducts().contains(product)) {
                Promote.addProduct(product);
            }
            f = true;
        }
        return f;
    }

}
