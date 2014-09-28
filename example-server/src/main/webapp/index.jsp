<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html ng-app="teslarApp">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>

        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="/static/css/bootstrap.min.css">

        <!-- Optional theme -->
        <link rel="stylesheet" href="/static/css/bootstrap-theme.min.css">

        <!-- Latest compiled and minified JavaScript -->
        <script src="./static/js/bootstrap.min.js" type="text/javascript"></script>

        <script src="./static/js/angular.min.js" type="text/javascript"></script>
        <script src="./static/js/angular-route.min.js" type="text/javascript"></script>
    </head>
    <body>
        <div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
            <div class="container">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target=".navbar-collapse">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="#">Project name</a>
                </div>
                <div class="collapse navbar-collapse">
                    <ul class="nav navbar-nav">
                        <li class="active"><a href="#">Home</a></li>
                        <li><a href="#about">About</a></li>
                        <li><a href="#contact">Contact</a></li>
                    </ul>
                </div><!--/.nav-collapse -->
            </div>
        </div>

        <div class="container" style="margin-top: 50px">

            <div class="starter-template">

                <%
                    out.print(getServletContext().getAttribute("name"));
                    String myVariable = (String) request.getAttribute("viewpath");
                    if (myVariable == null) {
                        myVariable = "./static/container.html";
                    }
                    out.print(myVariable + "");
                %>

                <% if (myVariable != null && !myVariable.isEmpty()) {                //  
%>
                <jsp:include page="<%= myVariable%>" />
                <%}%>



            </div>

        </div><!-- /.container -->


    </body>
</html>
