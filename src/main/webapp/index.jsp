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
    request.setCharacterEncoding("UTF-8");
    String role = (String) session.getAttribute("role");

%>

<jsp:include page="header.jsp">
    <jsp:param name="role" value="<%=role%>"/>
</jsp:include>

<%
    String toogler = "";
    if (role == null) {
        toogler = "disabled";
        role = "";
    }
    if (!role.equals("")) {
%>
<div class="text-center" style="margin-top: 100px">
    <h1> ERP MANAGER</h1>
</div>

<div class="grid-container2">
    <div class="grid-item grid-item-1">
        <a class="nav-link <%=toogler%> <%=(role.equals("Salesman") ? "active" : "disabled")%>" href="customers.jsp">
            <button class="button">Customers
            </button>
        </a>
    </div>
    <div class="grid-item grid-item-2">
        <a class="nav-link <%=toogler%> <%=(role.equals("Product Manager") ? "active" : "disabled")%>"
           href="manageProduct.jsp">
            <button class="button"> Manage Product
            </button>
        </a>
    </div>
    <div class="grid-item grid-item-3">
        <a class="nav-link <%=toogler%> <%=(role.equals("Salesman") ? "active" : "disabled")%>" href="promote.jsp">
            <button class="button">Promote
            </button>
        </a>
    </div>
</div>
<%
} else {
%>
<div class="container text-center" style="margin-top: 100px">
    <h1> Please login to continue</h1>
    <a class="navbar-brand" href="login.jsp"><img src="images/logo-3.png" alt="Badge" width="402"></a>
</div>
<%
    }
%>
</body>
<!-- Scripts -->
<script src="js/jquery.min.js"></script> <!-- jQuery for Bootstrap's JavaScript plugins -->
<script src="js/scripts.js"></script> <!-- Custom scripts -->
<script src="js/bootstrap.bundle.min.js"></script>
</html>