<%@ page language="java" contentType="text/html; charset=EUC-KR"
   pageEncoding="EUC-KR"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html>
<c:if test = "${empty cookie.id }">
	<c:redirect url = "main.jsp">
		<c:param name = "reason" value ="no_id"/>
	</c:redirect>
</c:if>
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
        <link rel="shortcut icon" type="image/x-icon" href="./static/img/aco.ico" />
    </head>
    <body id="container">
		<nav id="global-Nav"></nav>    
        <div class="inquire-title">시간표 조회하기</div>
        <div class="inquire-subtitle">무슨 강의를 들어야할지 고민이신가요?</div>
        <form class="inquire-form" method="get" action="RecommendPage">
           <div class="inquire-font">학기</div>
            <select id="semester" class="inquire-field" name="semester">
                <option value="1">1학년 1학기</option>
                <option value="2">1학년 2학기</option>
                <option value="3">2학년 1학기</option>
                <option value="4">2학년 2학기</option>
                <option value="5">3학년 1학기</option>
                <option value="6">3학년 2학기</option>
                <option value="7">4학년 1학기</option>
                <option value="8">4학년 2학기</option>
            </select>
            <div class="inquire-font">최대학점</div>
            <input class="inquire-field" type="text" name="maxCredit">
            <button id="signin" class="inquire_btn" type="submit">조회하기</button>
        </form>
    </body>
</html>