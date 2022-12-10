<%@ page import="com.example.webapp.CustomerDAO" %>
<%@ page import="com.example.webapp.Customer" %>
<%@ page import="java.util.ArrayList" %>
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
        //response.sendRedirect("http://localhost:8080/WebApp_war_exploded/login.jsp");
    }

    if (!role.equals("Salesman")) {
%>
<jsp:forward page="index.jsp"/>
<%
    }
    //response.sendRedirect("http://localhost:8080/WebApp_war_exploded/index.jsp");


%>

<jsp:include page="header.jsp">
    <jsp:param name="role" value="<%=role%>"/>
</jsp:include>


<br>
<br>
<br>

<div class="container text-center">
    <!-- Search -->
    <form action="HistoryServlet" type="POST" class="was-validated"
          onsubmit="return confirm('Do you really want to submit the form?');">
        <div class="row justify-content-center">
            <div class="col-lg-6">

                <div class="form-floating">
                    <input class="form-control" list="search_list" name="customer" type="search" id="search"
                           placeholder="Search Existing Customer" aria-label="Search" required>
                    <datalist id="search_list">
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
                    <label for="search" class="text-black">Select Customer</label>
                </div>
            </div>
        </div>

        <br>
        <br>
        <br>
        <!-- Table -->
        <div class="container text-center">

            <div class="row justify-content-center">
                <div class="col-6">
                    <div class="table-responsive-md">
                        <table class="table bg-white caption-top">
                            <caption class="bg-white text-center"><h4><%= request.getAttribute("customer") == null ? "" : request.getAttribute("cust_name") +" "+  request.getAttribute("cust_surname")%></h4></caption>
                            <thead>
                            <tr>
                                <th scope="col">Product</th>
                                <th scope="col">Price</th>
                                <th scope="col">Quantity</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>

                                <td>093</td>
                                <td>250 <i class="bi bi-currency-euro"></i></td>
                                <td>1</td>
                            </tr>
                            <tr>
                                <td>034</td>
                                <td>32 <i class="bi bi-currency-euro"></i></td>
                                <td>1</td>
                            </tr>
                            <tr>
                                <td>143</td>
                                <td>78 <i class="bi bi-currency-euro"></i></td>
                                <td>1</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>


            <label>Start Date:
                <input type="date" id="startdateId" name="startDate"
                       min="" max="" class="form-control">
            </label>


            <label>End Date:
                <input type="date" id="enddateId" name="endDate"
                       min="" max="" class="form-control">
            </label>

            <br>
            <br>

            <label for="sum" class="form-label">Total: 360<i class="bi bi-currency-euro"></i></label>
            <br>
            <label for="average" class="form-label">Average: 120<i class="bi bi-currency-euro"></i></label>


            <br>
            <br>

            <div class="d-grid gap-2 col-6 mx-auto">
                <button class="btn btn-primary btn-lg" type="submit">Extract</button>
            </div>
        </div>
    </form>
</div>


</body>
<!-- Scripts -->
<script src="js/jquery.min.js"></script> <!-- jQuery for Bootstrap's JavaScript plugins -->
<script src="js/scripts.js"></script> <!-- Custom scripts -->
<script src="js/bootstrap.bundle.min.js"></script>
<!-- Calendar Scripts -->
<script src="js/calendar.js">
</script>

</html>