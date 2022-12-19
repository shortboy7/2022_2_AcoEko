<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page import ="java.util.ArrayList" %>
<%@ page import = "general.BaseResponse" %>
<%@ page import = "model.course.*" %>

<!DOCTYPE html>
<%!
	ArrayList<ArrayList<Course>> combs = null;
%>

<%
	BaseResponse<ArrayList<ArrayList<Course>>> res = (BaseResponse<ArrayList<ArrayList<Course>>>)request.getAttribute("result");
	combs = res.getResult();
%>
<html lang="ko">

    <head>
        <title></title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <script src="http://code.jquery.com/jquery-latest.min.js"></script>
        <link href="css/frame.css" rel="stylesheet">
        <link href="css/element.css" rel="stylesheet">
        <script>
            var signup_href = "signup.html";
            $(document).ready(function(){
                $("#global-Nav").load("navbar.html");
            })
        </script>
    </head>
    <body id="container">
   		<nav id="global-Nav"></nav>    
        <div class="inquire-title"></div>
        <div class="inquire-subtitle"></div>
        <main class="main_horizon">
            <%
            	for (int i = 0 ; i < combs.size() ; i++) {
           		%>
           		 	<section class="section_horizon">
	                <div class="course-result-title s1"> <%= i + 1%></div>
	                <div id="s<%= i+1%>" class="course-result">
	                <%
	                	for (int j = 0 ; j < combs.get(i).size(); j++){
	                		out.println("<div>"+combs.get(i).get(j).getName()+"</div><br>");
	                	}
	                %>
                	</div></section>
           		<%
            	}
            %>
        </main>
    </body>
</html>