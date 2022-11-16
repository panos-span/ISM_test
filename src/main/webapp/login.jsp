<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Login Form</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
    <!--logo-->
    <link rel="icon" href="images/logo.png">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.9.1/font/bootstrap-icons.css">

</head>
<%
    request.setCharacterEncoding("UTF-8");
    String role = (String) session.getAttribute("role");

    if (!(role == null)) {
        response.sendRedirect("http://localhost:8080/WebApp_war_exploded/index.jsp");
        return;
    }
%>
<%
    //String failed = (String) session.getAttribute("failed");
    String failed = (String) request.getAttribute("failed");
    boolean f = Boolean.parseBoolean(failed);
%>

<body class="dark-mode">

<div class="container center-screen">
    <form action="LoginServlet" type="POST" class="row g-3 needs-validation">
        <%
            if (f) {
        %>
        <div class="alert alert-danger text-center" role="alert">
            <i class="bi bi-x-circle-fill"></i> Invalid username or password
        </div>
        <%
            }
        %>

        <h1>Login</h1>
        <div class="input-group mb-3">
            <span class="input-group-text">@</span>
            <div class="form-floating">
                <input type="text" class="form-control" name="username" id="username" placeholder="Username" required>
                <label for="username" class="text-black">Username</label>
            </div>
        </div>
        <div class="input-group mb-3">
            <div class="form-floating">
                <input type="password" class="form-control" id="password" name="password" placeholder="Password"
                       value="" aria-label="Password"
                       aria-describedby="basic-addon1" required>
                <label for="password" class="text-black">Password</label>
            </div>
            <span class="input-group-text">
                <i class="bi bi-eye-slash" id="togglePassword" style="cursor: pointer"></i>
            </span>
        </div>
        <button type="submit" class="btn btn-primary text-center">Submit</button>
    </form>
</div>

</body>

<script src="js/bootstrap.bundle.min.js"></script>
<!-- Toggle eye script-->
<script>
    const togglePassword = document.querySelector("#togglePassword");
    const password = document.querySelector("#password");

    togglePassword.addEventListener("click", function () {
        // toggle the type attribute
        const type = password.getAttribute("type") === "password" ? "text" : "password";
        password.setAttribute("type", type);

        // toggle the icon
        this.classList.toggle("bi-eye");
    });
</script>

</html>