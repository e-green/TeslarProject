<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <%
            out.print(getServletContext().getAttribute("name"));
            String myVariable = "./"+request.getAttribute("viewpath") + "";
           
            asda sdasd asd
            out.print(myVariable);
        %>

        <% if (myVariable != null || !myVariable.isEmpty()) {%>
        <jsp:include page="<%= myVariable %>" flush="true" />
        <%}%>
    </body>
</html>
