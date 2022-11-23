<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.time.LocalDateTime" %>

<%
    int hour = LocalDateTime.now().getHour();
    int minutes = LocalDateTime.now().getMinute();
%>

<div aria-live="polite" aria-atomic="true" class="toast-container bottom-0 end-0">

    <!-- Then put toasts within -->
    <div class="toast show" role="alert" aria-live="assertive" aria-atomic="true">
        <div class="toast-header">
            <strong class="me-auto"> Client</strong>
            <small><%=hour%>:<%=minutes%>
            </small>
            <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
        </div>
        <div class="toast-body text-black">
            <%=request.getParameter("error_message")%>
        </div>
    </div>
</div>