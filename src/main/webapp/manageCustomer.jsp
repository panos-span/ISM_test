<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.example.webapp.CustomerDAO" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.time.LocalDateTime" %>
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
        //response.sendRedirect("http://localhost:8080/WebApp_war_exploded/index.jsp");
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
            <strong class="me-auto"><%=action%> Client</strong>
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

<%!
    private String getSearchId(String x) {
        String[] y = x.split("=");
        y[1] = y[1].substring(0, y[1].length() - 1);
        return y[1];
    }

    private String check(String x) {
        if (x == null) {
            return "";
        }
        return x;
    }
%>

<%

    String cust = request.getParameter("customer");

    CustomerDAO customer = new CustomerDAO();
    String Name = "", Surname = "", Id = "", Vat = "", Email = "", Details = "";
    String[] Address = {"", ""};
    String[] phones = {"", ""};
    if (cust != null) {
        if (!cust.equals("")) {
            String cust_id = getSearchId(cust);
            ResultSet rs1 = customer.searchCustomer(cust_id);
            if (rs1 == null) {
                throw new Exception("Client does not exist");
            }
            if (!rs1.next()) {
                throw new Exception("Client does not exist");
            }

            Name = rs1.getString("Name");
            Surname = rs1.getString("Surname");
            Vat = rs1.getString("VAT");
            Id = rs1.getString("Id");
            Address = rs1.getString("Address").split(", ", 2);
            Email = rs1.getString("Email");
            Details = rs1.getString("Details");

            Details = check(Details);
            Vat = check(Vat);

            rs1.close();

            ResultSet rs2 = customer.searchCustomerPhones(cust_id);
            int index = 0;
            while (rs2.next()) {
                phones[index] = rs2.getString("Phone");
                index++;
            }
            session.setAttribute("edit", Id);
            rs2.close();
        }
    }

%>

<div class="container text-center" style="margin-top: 50px">
    <form action="manageCustomer.jsp" type="POST">
        <div class="row justify-content-center">
            <div class="col-lg-8">
                <div class="input-group mb-3">
                    <div class="form-floating">
                        <input class="form-control" list="search_list" name="customer" type="search" id="search"
                               placeholder="Search Existing Customer" aria-label="Search"
                               value="<%= !Name.equals("") ? Name + " " + Surname + " (ID=" + Id + ")" : ""%>">
                        <datalist id="search_list">
                            <%
                                ResultSet rs = customer.getAllCustomers();
                                if (rs == null) {
                                    throw new Exception("Error");
                                }

                                while (rs.next()) {
                                    String name = rs.getString("Name");
                                    String surname = rs.getString("Surname");
                                    String id = rs.getString("id");

                            %>
                            <option value="<%=name + " " + surname + " (ID=" + id + ")"%>">
                                    <%
                                }
                                rs.close();
                                customer.close();

                            %>
                        </datalist>
                        <label for="search" class="text-black">Search Existing Customer</label>
                    </div>
                    <button class="btn input-group-text bg-success" type="submit"><i
                            class="bi bi-search"></i></button>
                </div>
            </div>
        </div>
    </form>

    <br>
    <hr>
    <br>
    <form class="was-validated" action="CustomerServlet" type="POST"
          onsubmit="return confirm('Do you really want to submit the form?');">
        <div class="row g-2">
            <div class="col-md">
                <div class="form-floating">
                    <input type="text" class="form-control" name="name" id="floatingInputGrid1" placeholder="Name"
                           value="<%=Name%>" required>
                    <label for="floatingInputGrid1" class="text-black">Name</label>
                </div>
            </div>
            <div class="col-md">
                <div class="form-floating">
                    <input type="text" class="form-control" name="surname" id="floatingInputGrid2" placeholder="Surname"
                           value="<%=Surname%>" required>
                    <label for="floatingInputGrid2" class="text-black">Surname</label>
                </div>
            </div>
        </div>
        <br>
        <div class="row g-2">
            <div class="col-md">
                <div class="form-floating">
                    <input type="text" class="form-control" name="vat" id="floatingInputGrid3" placeholder="VAT"
                           value="<%=Vat%>">
                    <label for="floatingInputGrid3" class="text-black">VAT</label>
                </div>
            </div>
            <div class="col-md">
                <div class="form-floating">
                    <input type="email" class="form-control" name="email" id="floatingInputGrid4"
                           placeholder="name@example.com"
                           value="<%=Email%>" required>
                    <label for="floatingInputGrid4" class="text-black">E-mail</label>
                </div>
            </div>
        </div>
        <br>
        <div class="row g-2">
            <div class="col-md">
                <div class="form-floating">
                    <input type="tel" class="form-control" name="phone_1" id="floatingInputGrid5" placeholder="Phone1"
                           value="<%=phones[0]%>" required>
                    <label for="floatingInputGrid5" class="text-black">Phone 1</label>
                </div>
            </div>
            <div class="col-md">
                <div class="form-floating">
                    <input type="tel" class="form-control" name="phone_2" id="floatingInputGrid6" placeholder="Phone2"
                           value="<%=phones[1]%>">
                    <label for="floatingInputGrid6" class="text-black">Phone 2</label>
                </div>
            </div>
        </div>
        <br>
        <div class="row g-2">
            <div class="col-md">
                <div class="form-floating">
                    <input type="text" class="form-control" name="street" id="floatingInputGrid7" placeholder="Street"
                           value="<%=Address[0]%>" required>
                    <label for="floatingInputGrid7" class="text-black">Street</label>
                </div>
            </div>
            <div class="col-md">
                <div class="form-floating">
                    <input type="text" class="form-control" name="city" id="floatingInputGrid8" placeholder="City"
                           value="<%=Address[1]%>" required>
                    <label for="floatingInputGrid8" class="text-black">City</label>
                </div>
            </div>
        </div>
        <br>
        <div class="form-floating">
            <textarea type="text" class="form-control" placeholder="Details" name="details" id="floatingTextarea2"
                      style="height: 100px"><%=Details%></textarea>
            <label for="floatingTextarea2" class="text-black">Details</label>
        </div>
        <br>
        <div class="container text-center">
            <div class="d-grid gap-2 col-6 mx-auto">
                <button class="btn btn-primary btn-lg" type="submit">Submit
                </button>
            </div>
        </div>
    </form>
</div>


</body>
<!-- Scripts -->
<script src="js/jquery.min.js"></script> <!-- jQuery for Bootstrap's JavaScript plugins -->
<script src="js/scripts.js"></script> <!-- Custom scripts -->
<script src="js/bootstrap.bundle.min.js"></script>
</html>