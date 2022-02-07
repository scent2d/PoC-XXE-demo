<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@page import="we45.training.labs.User"%>
<%@page import="java.util.Iterator"%> 
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="0">
    <meta http-equiv="Cache-Control" content="no-store, no-cache, must-revalidate">
    <title>Users</title>
    <link href="/XXE/bootstrap.min.css" rel="stylesheet" type="text/css">
    
</head>
<body class="container">
    <br>
    <div class="card">
        <h5 class="card-header">
            Users                
        </h5>
        <div class="card-body">
            <h5 class="card-title"></h5>
            <table class="table">
                <tr>
                    <form autocomplete="off" action="secure" method="post" enctype="multipart/form-data">
                        <td colspan='2'>                                             
                            <input type="file" placeholder="Upload xml file" class="form-control" name="file">	    
                        </td>  
                        <td>
                            <button type="submit" class="btn btn-primary">Submit</button>
                        </td>                  
                    </form>
                </tr>
            </table>
            <% if(request.getAttribute("users")!=null) { %>
            <table class="table">                    
                <tr>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Email</th>
                </tr>
                <%  
                  List<User> list = (List) request.getAttribute("users");
                  for(User user : list){
                %>
                <tr>
                    <td><%=user.getFname()%></td>
                    <td><%=user.getLname()%></td>
                    <td><%=user.getEmail()%></td>
                </tr>
                <% } %>
            </table>
            <% } %>
        </div>
    </div>
    <script src="/XXE/jquery-3.3.1.slim.min.js"></script>
    <script src="/XXE/popper.min.js"></script>
    <script src="/XXE/bootstrap.min.js"></script>
</body>
</html>