package com.example.webapp;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.ArrayList;

public class PromoteServlet extends HttpServlet {


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/html;charset=UTF-8");
        boolean redirect = checkForAddedClient(request);
        if (checkForAddedProduct(request) || checkForRemovedClient(request) || checkForRemovedProduct(request)) {
            redirect = true;
        }
        if (redirect) {
            RequestDispatcher rd = request.getRequestDispatcher("/promote.jsp");
            rd.forward(request, response);
            return;
        }
        ArrayList<Customer> cust;
        cust=Promote.getCustomers();
        ArrayList<Product>prod;
        prod=Promote.getProducts();
        String message =GenerateEmailMessage(prod);
        String subject="Promotion just for you!!!!";
        String user="pramataritest@gmail.com";
        String pass="zffocnenjhioytwd";
        String mail;
        for(Customer customer : cust){
            mail=customer.getEmail();
            SendMail.send(mail,subject,message,user,pass);
        }

        //PrintWriter out = new PrintWriter(response.getWriter(), true);
        //String client_number = request.getParameter("client_number");
        //String product_number = request.getParameter("product_number");
        //out.println(client_number);
        //out.println(product_number);
        //for (Customer customer : Promote.getCustomers()) {
        //    out.println(customer.getId());
        //}
        //for (Product product : Promote.getProducts()) {
        //    out.println(product.getId());
        //}
        Promote.clearLists();
        RequestDispatcher rd = request.getRequestDispatcher("/promote.jsp");
        rd.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        doPost(request, response);
    }

    private String GenerateEmailMessage(ArrayList<Product> products){
        String message="Here is the list of the products and their id's,that we think you'd like:\n";
        for (Product prod:products){
            message=message + prod.getName()+","+prod.getId()+"\n";
        }
        return message;
    }


    private String getSearchId(String x) {
        String[] y = x.split("=");
        y[1] = y[1].substring(0, y[1].length() - 1);
        return y[1];
    }

    private boolean checkForRemovedClient(HttpServletRequest request) {
        for (Customer customer : Promote.getCustomers()) {
            String cust = request.getParameter(String.format("remove_client%s", customer.getId()));
            if (cust != null) {
                Promote.removeCustomer(customer);
                return true;
            }
        }
        return false;
    }

    private boolean checkForRemovedProduct(HttpServletRequest request) {
        for (Product product : Promote.getProducts()) {
            String prod = request.getParameter(String.format("remove_product%s", product.getId()));
            if (prod != null) {
                Promote.removeProduct(product);
                return true;
            }
        }
        return false;
    }

    private boolean checkForAddedClient(HttpServletRequest request) {
        String cust = request.getParameter("customer");
        boolean f = false;
        if (cust != null) {
            String[] params = cust.split(" ");
            params[2] = getSearchId(params[2]);
            CustomerDAO customerDAO = new CustomerDAO();
            ResultSet rs = customerDAO.searchCustomer(params[2]);
            boolean exists;
            Customer customer = null;
            try {
                exists = rs.next();
                customer = new Customer(params[2], params[0], params[1], rs.getString("Email"));
            } catch (Exception e) {
                exists = false;
            }
            if (exists && !Promote.getCustomers().contains(customer)) {
                Promote.addCustomer(customer);
            }
            try {
                rs.close();
            } catch (Exception ignored) {
            }
            customerDAO.close();
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
            ResultSet rs = productDAO.searchProduct(params[params.length - 1]);
            /*try {
                if (rs.next()) {
                    Product product = new Product(rs.getString("ID"), rs.getString("Name"), rs.getDouble("Price"));
                }
                rs.close();
            } catch (Exception e) {
                return false;
            }*/
            Product product = new Product(params[params.length - 1], name, price);
            if (!Promote.getProducts().contains(product)) {
                Promote.addProduct(product);
            }
            productDAO.close();
            f = true;
        }
        return f;
    }

}
