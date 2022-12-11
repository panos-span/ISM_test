<%@ page import="com.example.webapp.CustomerDAO" %>
<%@ page import="com.example.webapp.ProductDAO" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.example.webapp.Product" %>
<%@ page import="com.example.webapp.Customer" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>ERP MANAGER</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
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

    boolean f1 = action != null;
    int hour = LocalDateTime.now().getHour();
    int minutes = LocalDateTime.now().getMinute();
    if (f1) {
%>
<!-- Flexbox container for aligning the toasts -->
<div aria-live="polite" aria-atomic="true" class="toast-container bottom-0 end-0">

    <!-- Then put toasts within -->
    <div class="toast show" role="alert" aria-live="assertive" aria-atomic="true">
        <div class="toast-header">
            <strong class="me-auto">Insert Sale</strong>
            <small><%=hour%>:<%=minutes%>
            </small>
            <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
        </div>
        <div class="toast-body text-black">
            Insert was successful
        </div>
    </div>
</div>

<%
    }
%>


<jsp:include page="header.jsp">
    <jsp:param name="role" value="<%=role%>"/>
</jsp:include>

<div class="text-center" style="margin-top: 50px;">
    <h1 class="text-primary"> Insert sale data</h1>
</div>
<br>
<br>

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

<form class="container text-center was-validated" action="SaleServlet"
      onsubmit="return confirm('Do you really want to submit the form?');">
    <div class="row justify-content-center">
        <div class="col-lg-8">
            <div class="input-group mb-3">
                <label class="input-group-text" for="select_customer">Customer</label>
                <input class="form-control" list="customer_list" name="customer" type="search" id="select_customer"
                       placeholder="Select Customer" aria-label="Search" required>
                <datalist id="customer_list">
                    <%
                        CustomerDAO customer = new CustomerDAO();
                        ArrayList<Customer> customers = customer.getAllCustomers();
                        if (customers == null) {
                            throw new Exception("Error, no customers exist");
                        }

                        for (Customer cust : customers) {
                            String name = cust.getName();
                            String surname = cust.getSurname();
                            String id = cust.getId();

                    %>
                    <option value="<%=name + " " + surname + " (ID=" + id+")"%>">
                            <%
                        }
                                customer.close();
                            %>
                </datalist>
            </div>
        </div>
    </div>
    <br>
    <div class="row justify-content-center">
        <div class="col-lg-8">
            <div class="input-group mb-3">
                <label class="input-group-text" for="select_product">Product</label>
                <input class="form-control" list="products_list" name="product" type="search" id="select_product"
                       placeholder="Select Product" aria-label="Search" required>
                <datalist id="products_list">
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
            </div>
        </div>
    </div>
    <br>
    <div class="row justify-content-center">
        <div class="col col-lg-8">
            <div class="form-floating">
                <input type="date" class="form-control" id="startdateId" name="date" placeholder="Date" value=""
                       required>
                <label for="startdateId" class="text-black">Date</label>
            </div>
        </div>
    </div>
    <br>
    <div class="row justify-content-center">
        <div class="col col-lg-8">
            <div class="form-floating">
                <input type="number" min="1" class="form-control" placeholder="5" name="quantity" id="floatingTextarea2"
                       value="1" required>
                <label for="floatingTextarea2" class="text-black">Quantity</label>
            </div>
        </div>
    </div>
    <br>
    <div class="container text-center">
        <div class="row justify-content-center"></div>
        <div class="d-grid gap-2 col-8 mx-auto">
            <button class="btn btn-primary btn-lg" type="submit">Submit</button>
        </div>

    </div>

</form>

</body>
<!-- Scripts -->
<script src="js/jquery.min.js"></script> <!-- jQuery for Bootstrap's JavaScript plugins -->
<script src="js/scripts.js"></script> <!-- Custom scripts -->
<script src="js/bootstrap.bundle.min.js"></script>
<!-- Calendar Scripts -->
<script src="js/calendar.js"></script>

</html>