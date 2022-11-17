<%@ page contentType="text/html; charset=UTF-8" %>

<%
    request.setCharacterEncoding("UTF-8");
    String role = (String) session.getAttribute("role");

    boolean f = role == null;
    String toogler = "active";
    if (f) {
        toogler = "disabled";
        role = "";
    }
%>

<nav class="navbar navbar-expand-lg bg-primary">
    <div class="container-fluid">
        <a class="navbar-brand" href="index.jsp"><img src="images/logo.png" alt="Badge" width="60px"></a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link <%=toogler%>" aria-current="page" href="index.jsp">Home</a>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle <%=toogler%> <%=(role.equals("Salesman") ? "active" : "d-none")%>"
                       href="customers.jsp" role="button"
                       data-bs-toggle="dropdown"
                       aria-expanded="false">
                        Customers
                    </a>
                    <ul class="dropdown-menu">
                        <li><a class="dropdown-item" href="manageCustomer.jsp">Manage Customer</a></li>
                        <li><a class="dropdown-item" href="purchaseHistory.jsp">Customer History</a></li>
                        <li><a class="dropdown-item" href="manageSale.jsp">Manage Sale</a></li>
                    </ul>
                </li>
                <li class="nav-item">
                    <a class="nav-link <%=toogler%> <%=(role.equals("Product Manager") ? "active" : "d-none")%>"
                       aria-current="page" href="manageProduct.jsp">Manage Product</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link <%=toogler%> <%=(role.equals("Salesman") ? "active" : "d-none")%>"
                       aria-current="page" href="promote.jsp">Promote</a>
                </li>
            </ul>
            <%
                if (f) {

            %>
            <div class="nav-item btn-group bg-primary">
                <a class="nav-link text-black bg-primary" href="login.jsp" role="button"
                   aria-expanded="false">
                    Login
                </a>
            </div>
            <%
              } else {
            %>
            <div class="nav-item btn-group bg-primary">
                <a class="nav-link dropdown-toggle text-black bg-primary" href="#" role="button"
                   data-bs-toggle="dropdown"
                   aria-expanded="false">
                    <%=role%>
                </a>
                <ul class="dropdown-menu dropdown-menu-end">
                    <a class="dropdown-item" data-bs-toggle="modal" data-bs-target="#myModal" href="login.jsp">Sign
                        out</a>
                </ul>
            </div>
            <%
                }
            %>

        </div>
    </div>
</nav>

<!-- The Modal -->
<div class="modal fade" id="myModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <!-- Modal Header -->
            <div class="modal-header">
                <h4 class="modal-title" style="color: black">Sign Out</h4>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>

            <!-- Modal body -->
            <div class="modal-body" style="color: black">
                Are you sure you want to sign out?
            </div>
            <form action="LogoutServlet" type="POST">
                <!-- Modal footer -->
                <div class="modal-footer">
                    <button type="button" class="btn btn-danger" data-bs-dismiss="modal">Close</button>
                    <a href="#" style="color: white">
                        <button type="submit" class="btn btn-primary">Sign Out</button>
                    </a>
                </div>
            </form>

        </div>
    </div>
</div>
