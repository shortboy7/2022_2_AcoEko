<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "model.course.*" %>
<%@ page import = "dto.RoadMap" %>
<%@ page import = "general.BaseResponse" %>
<%@ page import = "java.lang.String" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">

<c:if test = "${empty cookie.id}">
	<c:redirect url = "main.jsp">
		<c:param name = "reason" value ="no_id"/>
	</c:redirect>
</c:if>
<c:if test = "${!res.isSuccess }">
	<c:redirect url="main.jsp">
		<c:param name = "reason" value = "${res.message}"/>
	</c:redirect>
</c:if>
<head>
    <title></title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
    <link href="css/frame.css" rel="stylesheet">
    <link href="css/element.css" rel="stylesheet">
    <link href="css/course.css" rel="stylesheet">
    <link rel="shortcut icon" type="image/x-icon" href="./static/img/aco.ico" />
    <script>
        $(document).ready(function () {
            $("#global-Nav").load("navbar.html");
         	
        })

        function syncState(state, id) {
            var courseID = "#" + id;
            if(state == "complete") {
                $(courseID).attr('class', 'course-unit complete');
            } else if(state == "ongoing") {
                $(courseID).attr('class', 'course-unit ongoing');
            } else if(state == "available") {
                $(courseID).attr('class', 'course-unit available');
            } else if(state == "impossible") {
                $(courseID).attr('class', 'course-unit impossible');
            }

        }
    </script>
</head>

<body id="container">
    <!-- navigation bar 영역 -->
    <nav id="global-Nav"></nav>
    <!-- main 영역 -->
    <main class="main_fix">
        <section>
        	<c:set var = "result" value = "${res.result}"/>
        	<c:forEach var = "course" items = "${result.gradedCourses}">
        		<div id = "${course.courseNumber}" class = "course-unit complete">
        			<div class = "unit-name"> ${course.name}</div>
        		</div>
        	</c:forEach>
        	
        	<c:forEach var = "course" items = "${result.enrollingCourses}">
        		<div id = "${course.courseNumber}" class = "course-unit ongoing">
        			<div class = "unit-name"> ${course.name}</div>
        		</div>
        	</c:forEach>
        	
       		<c:forEach var = "course" items = "${result.openCourses}">
        		<div id = "${course.courseNumber}" class = "course-unit available">
        			<div class = "unit-name"> ${course.name}</div>
        		</div>
        	</c:forEach>
        	
        	<c:forEach var = "course" items = "${result.closeCourses}">
        		<div id = "${course.courseNumber}" class = "course-unit impossible">
        			<div class = "unit-name"> ${course.name}</div>
        		</div>
        	</c:forEach>
        	

        </section>
    </main>
</body>

</html>