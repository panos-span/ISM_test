<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.example.webapp.CustomerDAO" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.example.webapp.Customer" %>
<%@ page import="java.sql.ResultSet" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
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
    request.setCharacterEncoding("UTF-8");
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
    if (request.getAttribute("error") != null) {
        String error_message = (String) request.getAttribute("error");
%>
<jsp:include page="error_toast.jsp">
    <jsp:param name="error_message" value="<%=error_message%>"/>
</jsp:include>
<%
    }
%>

<%

    String cust = request.getParameter("customer");
    boolean flag = false;
    CustomerDAO customer = new CustomerDAO();
    String Name = "", Surname = "", Id = "", Vat = "", Email = "", Details = "";
    String[] Address = {"", ""};
    String[] phones = {"", ""};
    if (cust != null) {
        if (!cust.equals("")) {
            String cust_id = null;
            boolean f = true;
            try {
                cust_id = getSearchId(cust);
            } catch (ArrayIndexOutOfBoundsException ignored) {
                f = false;
%>
<jsp:include page="error_toast.jsp">
    <jsp:param name="error_message" value="Customer not properly submitted"/>
</jsp:include>
<%
    }
    ResultSet rs1 = customer.searchCustomer(cust_id);
    try {

        if (rs1 == null) {
            throw new Exception("Customer does not exist");
        }
        if (!rs1.next()) {
            throw new Exception("Customer does not exist");
        }
        flag = true;
        Name = rs1.getString("Name");
        Surname = rs1.getString("Surname");
        Vat = rs1.getString("VAT");
        Id = rs1.getString("ID");
        Address = rs1.getString("Address").split(", ", 2);
        Email = rs1.getString("Email");
        Details = rs1.getString("Details");

        Details = check(Details);
        Vat = check(Vat);

        rs1.close();

        ResultSet rs2 = customer.searchCustomerPhones(Id);
        while (rs2.next()) {
            phones[rs2.getInt("POSITION") - 1] = rs2.getString("Phone");
        }

        session.setAttribute("edit", Id);
        rs2.close();
    } catch (Exception e) {
        session.setAttribute("edit", null);
        if (f) {
%>
<jsp:include page="error_toast.jsp">
    <jsp:param name="error_message" value="<%=e.getMessage()%>"/>
</jsp:include>
<%
                }
            }
        } else {
            session.setAttribute("edit", null);
            cust = null;
        }
    } else {
        session.setAttribute("edit", null);
    }

%>

<div class="container text-center" style="margin-top: 50px">
    <form action="manageCustomer.jsp" method="POST">
        <div class="row justify-content-center">
            <div class="col-lg-8">
                <div class="input-group mb-3">
                    <div class="form-floating">
                        <input class="form-control" list="search_list" name="customer" type="search" id="search"
                               placeholder="Search Existing Customer"
                               aria-label="Search"
                               value="<%= !Name.equals("") ? Name + " " + Surname + " (ID=" + Id + ")" : ""%>">
                        <datalist id="search_list">
                            <%
                                ArrayList<Customer> customers = customer.getAllCustomers();
                                if (customers == null) {
                                    throw new Exception("Error, no customers exist");
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
    <form class="was-validated" action="CustomerServlet" method="POST"
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
                <button class="btn btn-primary btn-lg" type="submit"
                        id="button"><%=(cust != null ? (flag ? "Edit" : "Insert") : "Insert")%>
                </button>
            </div>
        </div>
    </form>
</div>


</body>
<!-- Scripts -->
<script>

    btn = document.getElementById("button")
    search = document.getElementById("search")
    search.addEventListener('change', (event) => {
        if (search.value !== "") {
        } else {
            window.location.href = 'manageCustomer.jsp';
        }
    });

</script>
<script src="js/jquery.min.js"></script> <!-- jQuery for Bootstrap's JavaScript plugins -->
<script src="js/scripts.js"></script> <!-- Custom scripts -->
<script src="js/bootstrap.bundle.min.js"></script>
</html>