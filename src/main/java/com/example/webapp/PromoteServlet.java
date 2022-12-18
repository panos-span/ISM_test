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

/**
 * <p>PromoteServlet class.</p>
 *
 * @author ismgroup52
 * @version $Id: $1.0
 */
public class PromoteServlet extends HttpServlet {

    private final String subject = "Promotion just for you!!!!";
    private final String host = "smtp.gmail.com";
    private final String port = "465";
    private final String user = "";

    /** {@inheritDoc} */
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

        ArrayList<Customer> cust = Promote.getCustomers();
        ArrayList<Product> prod = Promote.getProducts();
        String x = null;
        if (cust.size() == 0) {
            x = "customers";
        } else if (prod.size() == 0) {
            x = "products";
        }

        if (x != null) {
            String error_message = String.format("You must enter %s for promotion", x);
            request.setAttribute("error", error_message);
            RequestDispatcher rd = request.getRequestDispatcher("/promote.jsp");
            rd.forward(request, response);
            return;
        }
        final String message = GenerateEmailMessage(prod);
        SendMail.send(user, host, port, cust, subject, message);
        Promote.clearLists();
        request.setAttribute("action", "Promotion");
        RequestDispatcher rd = request.getRequestDispatcher("/promote.jsp");
        rd.forward(request, response);
    }

    /** {@inheritDoc} */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        doPost(request, response);
    }

    private String GenerateEmailMessage(ArrayList<Product> products) {
        String message = "Here is the list of the products and their prices,that we think you'd like:\n";
        for (Product prod : products) {
            message += prod.getName() + ", " + prod.getPrice() + "€" + "\n";
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
            try {
                params[2] = getSearchId(params[2]);
            } catch (ArrayIndexOutOfBoundsException ignored) {
                return true;
            }
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


    private boolean checkForAddedProduct(HttpServletRequest request) {
        String prod = request.getParameter("product");
        boolean f = false;
        if (prod != null) {
            String[] params = prod.split(" ");
            try {
                params[params.length - 1] = getSearchId(params[params.length - 1]);
            } catch (ArrayIndexOutOfBoundsException ignored) {
                return true;
            }
            ProductDAO productDAO = new ProductDAO();
            ResultSet rs = productDAO.searchProduct(params[params.length - 1]);
            Product product;
            try {
                if (rs.next()) {
                    product = new Product(rs.getString("ID"), rs.getString("Name"), rs.getDouble("Price"));
                } else {
                    return true;
                }
                rs.close();
            } catch (Exception e) {
                try {
                    rs.close();
                } catch (SQLException ignored) {
                }
                return true;
            }
            if (!Promote.getProducts().contains(product)) {
                Promote.addProduct(product);
            }
            productDAO.close();
            f = true;
        }
        return f;
    }

}
