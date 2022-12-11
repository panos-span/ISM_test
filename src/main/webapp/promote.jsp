<%@ page import="com.example.webapp.CustomerDAO" %>
<%@ page import="com.example.webapp.ProductDAO" %>
<%@ page import="com.example.webapp.Customer" %>
<%@ page import="com.example.webapp.Promote" %>
<%@ page import="com.example.webapp.Product" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>ERP MANAGER</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.9.1/font/bootstrap-icons.css">
    <!--logo-->
    <link rel="icon" href="images/logo.png">

</head>
<body class="dark-mode">

<%
    String role = (String) session.getAttribute("role");

    if (role == null) {
%>
<jsp:forward page="login.jsp"/>
<%

    }

    if (!role.equals("Salesman")) {
%>
<jsp:forward page="index.jsp"/>
<%

    }

    String action = (String) request.getAttribute("action");

    if (action == null) {
        action = "";
    }

    boolean f1 = !action.equals("");
    int hour = LocalDateTime.now().getHour();
    int minutes = LocalDateTime.now().getMinute();
    if (f1) {
%>
<!-- Flexbox container for aligning the toasts -->
<div aria-live="polite" aria-atomic="true" class="toast-container bottom-0 end-0">

    <!-- Then put toasts within -->
    <div class="toast show" role="alert" aria-live="assertive" aria-atomic="true">
        <div class="toast-header">
            <strong class="me-auto"><%=action%></strong>
            <small><%=hour%>:<%=minutes%>
            </small>
            <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
        </div>
        <div class="toast-body text-black">
            <%=action%> was successful
        </div>
    </div>
</div>

<%
    }
%>


<jsp:include page="header.jsp">
    <jsp:param name="role" value="<%=role%>"/>
</jsp:include>

<%
    if (request.getAttribute("error") != null) {
        String error_message = (String) request.getAttribute("error");
%>
<jsp:include page="error_toast.jsp">
    <jsp:param name="error_message" value="<%=error_message%>"/>
</jsp:include>
<%
    }
%>

<!-- Search -->
<div class="container text-center" style="margin-top: 100px">
    <form role="search" action="PromoteServlet" type="POST" class="was-validated">
        <div class="row justify-content-center">
            <div class="col-lg-8">
                <div class="input-group mb-3">
                    <div class="form-floating">
                        <input class="form-control" name="customer" list="search_customers_list" type="search"
                               id="search_customers"
                               placeholder="Add Customer" aria-label="Search" required>
                        <datalist id="search_customers_list">
                            <%
                                CustomerDAO customer = new CustomerDAO();
                                ArrayList<Customer> customers = customer.getAllCustomers();
                                if (customers == null) {
                                    throw new Exception("Error");
                                }

                                for (Customer cst : customers) {
                                    String name = cst.getName();
                                    String surname = cst.getSurname();
                                    String id = cst.getId();

                            %>
                            <option value="<%=name + " " + surname + " (ID=" + id + ")"%>">
                                    <%
                                }
                                customer.close();
                            %>
                        </datalist>
                        <label for="search_customers" class="text-black">Add Customers</label>
                    </div>
                    <button class="btn input-group-text bg-success" type="submit"><i
                            class="bi bi-plus-lg"></i></button>
                </div>
            </div>
        </div>
    </form>
    <form role="search" action="PromoteServlet" type="POST" class="was-validated">
        <div class="row justify-content-center">
            <div class="col-lg-8">
                <div class="input-group mb-3">
                    <div class="form-floating">
                        <input class="form-control" list="search_products_list" name="product" type="search"
                               id="search_products"
                               placeholder="Add Product" aria-label="Search" required>
                        <datalist id="search_products_list">
                            <%
                                ProductDAO product = new ProductDAO();
                                ArrayList<Product> products = product.getAllProducts();
                                if (products == null) {
                                    throw new Exception("Error, no products exist");
                                }

                                for (Product prd : products) {
                                    String name = prd.getName();
                                    String id = prd.getId();
                            %>
                            <option value="<%=name + " (ID=" + id+")"%>">
                                    <%
                                }
                                product.close();
                            %>
                        </datalist>
                        <label for="search_products" class="text-black">Add Products</label>
                    </div>
                    <button class="btn input-group-text bg-success" type="submit"><i
                            class="bi bi-plus-lg"></i></button>
                </div>
            </div>
        </div>
    </form>
</div>

<br>
<br>
<br>

<div class="container text-center">
    <form action="PromoteServlet" type="POST" onsubmit="myFunction();">
        <!-- Tables-->
        <div class="row justify-content-evenly">
            <div class="col-5">
                <div class="table-responsive-md" style="height:275px;overflow:auto;">
                    <table class="table bg-white caption-top" id="client_table">
                        <caption class="bg-white text-center"><h4>List of Clients </h4></caption>
                        <thead>
                        <tr>
                            <th scope="col">#Id</th>
                            <th scope="col">Name</th>
                            <th scope="col">Surname</th>
                            <th scope="col"></th>
                        </tr>
                        </thead>
                        <tbody>
                        <%
                            for (Customer cust : Promote.getCustomers()) {
                        %>
                        <tr>
                            <th scope="row"><%=cust.getId()%>
                            </th>
                            <td><%=cust.getName()%>
                            </td>
                            <td><%=cust.getSurname()%>
                            </td>
                            <td>
                                <button type="submit" class="btn btn-danger small" value="<%=cust.toString()%>"
                                        name="remove_client<%=cust.getId()%>"><i
                                        class="bi bi-dash-lg"></i></button>
                            </td>
                        </tr>
                        <%
                            }
                        %>
                        <input type="hidden" class="visually-hidden" name="client_number" id="client_number" value="">
                        </tbody>
                    </table>
                </div>
            </div>

            <div class="col-5">
                <div class="table-responsive-md" style="height:275px;overflow:auto;">
                    <table class="table bg-white caption-top" id="product_table">
                        <caption class="bg-white text-center"><h4>List of Products </h4></caption>
                        <thead>
                        <tr>
                            <th scope="col">#Id</th>
                            <th scope="col">Name</th>
                            <th scope="col">Price</th>
                            <th scope="col"></th>
                        </tr>
                        </thead>
                        <tbody>
                        <%
                            for (Product prod : Promote.getProducts()) {
                        %>
                        <tr>
                            <th scope="row"><%=prod.getId()%>
                            </th>
                            <td><%=prod.getName()%>
                            </td>
                            <td><%=prod.getPrice()%> <i class="bi bi-currency-euro"></i></td>
                            <td>
                                <button type="submit" class="btn btn-danger small" value="<%=prod.toString()%>"
                                        name="remove_product<%=prod.getId()%>"><i
                                        class="bi bi-dash-lg"></i></button>
                            </td>
                        </tr>
                        <%
                            }
                        %>
                        <input type="hidden" class="visually-hidden" name="product_number" id="product_number" value="">
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <br>
        <br>

        <div class="d-grid gap-2 col-8 mx-auto">
            <button class="btn btn-primary btn-lg" type="submit"
                    onclick="return confirm('Do you really want to submit the form?');">Submit
            </button>
        </div>

    </form>
</div>
</body>
<!-- Scripts -->
<script src="js/jquery.min.js"></script> <!-- jQuery for Bootstrap's JavaScript plugins -->
<script src="js/scripts.js"></script> <!-- Custom scripts -->
<script src="js/bootstrap.bundle.min.js"></script>
<script>
    function myFunction() {
        var client_number = document.getElementById("client_table").rows.length;
        var product_number = document.getElementById("product_table").rows.length;
        var c = document.getElementById("client_number")
        c.value = client_number - 1
        var p = document.getElementById("product_number")
        p.value = product_number - 1
    }

</script>
</html>