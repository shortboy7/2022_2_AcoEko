<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:if test = "${!empty cookie.id}">
	<c:redirect url = "MainPage"/>
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
                $("#signup").click(function(){
                location.href = signup_href; });
            })
        </script>
    </head>
    <body id="container" class="main background-img">
        <!-- navigation bar 영역 -->
        <nav id="global-Nav"></nav>
        <!-- main 영역 -->
        <main class="layer_shadow">
            <!-- section 영역 -->
            <section class="section_center">
                <!-- 로그인 폼 -->
                <div class="box_halo">
                    <div class="box_shadow">
                        <!-- Logo -->
                        <div class="login-logo">
                            <img src="./static/img/aco_eclipse.png" alt="aco_eclipses" width="80px">
                        </div>
                        <!-- Title -->
                        <div class="login-title">Sign in to AkoEko</div>
                        <!-- Login form -->
                        <div class="login-form">
                        <form method="post" action="./Login">
                            <div class="login-font">Username or email address</div>
                            <input class="login-field" type="text" name="id">
                            <div class="login-font">Password</div>
                            <input class="login-field" type="password" name="pwd"><br><br>
                            <button id="signin" class="sign_btn" type="submit">로그인</button>
                            <hr color="black">
                        </form>
                        <button id="signup" class="sign_btn">회원가입</button>
                    </div>
                </div>
            </section>
        </main>
    </body>
</html>