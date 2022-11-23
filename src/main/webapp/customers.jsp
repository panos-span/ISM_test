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
%>

<jsp:include page="header.jsp">
    <jsp:param name="role" value="<%=role%>"/>
</jsp:include>


<div class="text-center" style="margin-top: 100px">
    <h1>Actions</h1>
</div>

<div class="grid-container2">
    <div class="grid-item grid-item-1">
        <a href="manageCustomer.jsp">
            <button class="button">Manage Customer
            </button>
        </a>
    </div>
    <div class="grid-item grid-item-2">
        <a href="purchaseHistory.jsp">
            <button class="button">Customer History
            </button>
        </a>
    </div>
    <div class="grid-item grid-item-3">
        <a href="manageSale.jsp">
            <button class="button">Manage Sale
            </button>
        </a>
    </div>
</div>

</body>
<!-- Scripts -->
<script src="js/jquery.min.js"></script> <!-- jQuery for Bootstrap's JavaScript plugins -->
<script src="js/scripts.js"></script> <!-- Custom scripts -->
<script src="js/bootstrap.bundle.min.js"></script>
</html>