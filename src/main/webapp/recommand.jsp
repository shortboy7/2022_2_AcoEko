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
        <div class="inquire-title">�ð�ǥ ��ȸ�ϱ�</div>
        <div class="inquire-subtitle">���� ���Ǹ� �������� ����̽Ű���?</div>
        <form class="inquire-form" method="get" action="RecommendPage">
           <div class="inquire-font">�б�</div>
            <select id="semester" class="inquire-field" name="semester">
                <option value="1">1�г� 1�б�</option>
                <option value="2">1�г� 2�б�</option>
                <option value="3">2�г� 1�б�</option>
                <option value="4">2�г� 2�б�</option>
                <option value="5">3�г� 1�б�</option>
                <option value="6">3�г� 2�б�</option>
                <option value="7">4�г� 1�б�</option>
                <option value="8">4�г� 2�б�</option>
            </select>
            <div class="inquire-font">�ִ�����</div>
            <input class="inquire-field" type="text" name="maxCredit">
            <button id="signin" class="inquire_btn" type="submit">��ȸ�ϱ�</button>
        </form>
    </body>
</html>