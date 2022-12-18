<%@ page import="com.example.webapp.ProductDAO" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.example.webapp.Product" %>
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
    request.setCharacterEncoding("UTF-8");
    String role = (String) session.getAttribute("role");

    if (role == null) {
%>
<jsp:forward page="login.jsp"/>
<%
    }


    if (!role.equals("Product Manager")) {
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
            <strong class="me-auto"><%=action%> Product</strong>
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

    String prod = request.getParameter("product");

    ProductDAO product = new ProductDAO();
    String name = "", category = "", description = "", id = "";
    float price = 0;
    if (prod != null) {
        if (!prod.equals("")) {
            String prod_id = null;
            boolean f = true;
            try {
                prod_id = getSearchId(prod);
            } catch (ArrayIndexOutOfBoundsException ignored) {
                f = false;
%>

<jsp:include page="error_toast.jsp">
    <jsp:param name="error_message" value="Product not properly submitted"/>
</jsp:include>

<%
    }
    ResultSet rs1 = product.searchProduct(prod_id);
    try {

        if (rs1 == null) {
            throw new Exception("Product does not exist");
        }
        if (!rs1.next()) {
            throw new Exception("Product does not exist");
        }
        name = rs1.getString("Name");
        category = rs1.getString("Category");
        id = rs1.getString("ID");
        description = rs1.getString("Description");
        price = rs1.getFloat("Price");

        description = check(description);

        rs1.close();

        session.setAttribute("editP", id);
    } catch (Exception e) {
        session.setAttribute("editP", null);
        if (f) {
%>

<jsp:include page="error_toast.jsp">
    <jsp:param name="error_message" value="<%=e.getMessage()%>"/>
</jsp:include>

<%
                }
            }
        } else {
            session.setAttribute("editP", null);
            prod = null;
        }
    } else {
        session.setAttribute("editP", null);
    }
%>

<div class="container text-center" style="margin-top: 50px">
    <form action="manageProduct.jsp" method="POST">
        <div class="row justify-content-center">
            <div class="col-lg-8">
                <div class="input-group mb-3">
                    <div class="form-floating">
                        <input class="form-control" list="search_list" type="search" name="product" id="search"
                               placeholder="Search Existing Product" aria-label="Search"
                        value="<%=!name.equals("") ? name + " " + "(ID=" + id + ")" : ""%>">
                        <datalist id="search_list">
                            <%
                                ArrayList<Product> products = product.getAllProducts();
                                if (products == null) {
                                    throw new Exception("Error, no products exist");
                                }

                                for (Product prd : products) {
                                    String nameP = prd.getName();
                                    String idP = prd.getId();
                            %>
                            <option value="<%=nameP + " (ID=" + idP+")"%>">
                                    <%
                                }
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
    <form class="was-validated" action="ProductServlet" method="POST" onsubmit="return confirm('Do you really want to submit the form?');">
        <div class="row justify-content-center">
            <div class="col-lg-8">
                <div class="form-floating">
                    <input type="text" class="form-control" name="name" id="floatingInputGrid1" placeholder="Name"
                           value="<%=name%>" required>
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
                               placeholder="Price" value="<%=price%>" required>
                        <label for="floatingInputGrid2" class="text-black">Price</label>
                    </div>
                    <i class="input-group-text bi bi-currency-euro"></i>
                </div>
            </div>

            <div class="col-4">
                <div class="form-floating">
                    <input type="text" class="form-control" id="floatingInputGrid3" name="category"
                           placeholder="Category"
                           value="<%=category%>">
                    <label for="floatingInputGrid3" class="text-black">Category</label>
                </div>
            </div>
        </div>
        <br>
        <div class="row justify-content-center">
            <div class="col-lg-8">
                <div class="form-floating">
                <textarea class="form-control" placeholder="description" name="description" id="description"
                          style="height: 100px"><%=description%></textarea>
                    <label for="description" style="color: black;">Details</label>
                </div>
            </div>
        </div>
        <br>

        <div class="row justify-content-center">
            <div class="d-grid gap-2 col-lg-8 mx-auto">
                <button class="btn btn-primary btn-lg" type="submit"
                        id="button"><%=prod != null ? "Edit" : "Insert"%>
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
            window.location.href = 'manageProduct.jsp';
        }
    });

</script>
<script src="js/jquery.min.js"></script> <!-- jQuery for Bootstrap's JavaScript plugins -->
<script src="js/scripts.js"></script> <!-- Custom scripts -->
<script src="js/bootstrap.bundle.min.js"></script>

</html>