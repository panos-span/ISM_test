<%@ page import="com.example.webapp.ProductDAO" %>
<%@ page import="java.sql.ResultSet" %>
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
    //response.sendRedirect("http://localhost:8080/WebApp_war_exploded/login.jsp");


    if (!role.equals("Product Manager")) {
%>
<jsp:forward page="index.jsp"/>
<%
    }
    //response.sendRedirect("http://localhost:8080/WebApp_war_exploded/index.jsp");


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

    String prod = request.getParameter("customer");
%>

<div class="container text-center" style="margin-top: 50px">
    <form role="search">
        <div class="row justify-content-center">
            <div class="col-lg-8">
                <div class="input-group mb-3">
                    <div class="form-floating">
                        <input class="form-control" list="search_list" type="search" name="product" id="search"
                               placeholder="Search Existing Product" aria-label="Search">
                        <datalist id="search_list">
                            <%
                                ProductDAO product = new ProductDAO();
                                ResultSet rs = product.getAllProducts();
                                if (rs == null) {
                                    throw new Exception("Error");
                                }

                                while (rs.next()) {
                                    String name = rs.getString("Name");
                                    String id = rs.getString("id");
                            %>
                            <option value="<%=name + " (ID=" + id+")"%>">
                                    <%
                                }
                                rs.close();
                                product.close();
                            %>
                        </datalist>
                        <label for="search" class="text-black">Search Existing Product</label>
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
    <form class="was-validated" onsubmit="return confirm('Do you really want to submit the form?');">
        <div class="row justify-content-center">
            <div class="col-lg-8">
                <div class="form-floating">
                    <input type="text" class="form-control" name="name" id="floatingInputGrid1" placeholder="Name"
                           value="ex: ABCDEFG " required>
                    <label for="floatingInputGrid1" class="text-black">Name</label>
                </div>
            </div>
        </div>
        <br>
        <div class="row justify-content-center">
            <div class="col-4">
                <div class="input-group">
                    <div class="form-floating">
                        <input type="number" min="0.01" step="0.01" name="price" class="form-control"
                               id="floatingInputGrid2"
                               placeholder="Price" value="0" required>
                        <label for="floatingInputGrid2" class="text-black">Price</label>
                    </div>
                    <i class="input-group-text bi bi-currency-euro"></i>
                </div>
            </div>

            <div class="col-4">
                <div class="form-floating">
                    <input type="text" class="form-control" id="floatingInputGrid3" name="category"
                           placeholder="Category"
                           value="ex: Clothing">
                    <label for="floatingInputGrid3" class="text-black">Category</label>
                </div>
            </div>
        </div>
        <br>
        <div class="row justify-content-center">
            <div class="col-lg-8">
                <div class="form-floating">
                <textarea class="form-control" placeholder="Description" name="details" id="floatingTextarea2"
                          style="height: 100px"></textarea>
                    <label for="floatingTextarea2" style="color: black;">Details</label>
                </div>
            </div>
        </div>
        <br>

        <div class="row justify-content-center">
            <div class="d-grid gap-2 col-lg-8 mx-auto">
                <button class="btn btn-primary btn-lg" type="submit"
                        id="button"><%=(prod != null ? (!prod.equals("") ? "Edit" : "Insert") : "Insert")%>
                </button>
            </div>
        </div>

    </form>
</div>


</body>
<!-- Scripts -->
<script src="js/changeButton.js"></script>
<script src="js/jquery.min.js"></script> <!-- jQuery for Bootstrap's JavaScript plugins -->
<script src="js/scripts.js"></script> <!-- Custom scripts -->
<script src="js/bootstrap.bundle.min.js"></script>

</html>