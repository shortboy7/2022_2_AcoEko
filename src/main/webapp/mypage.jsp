<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import = "general.BaseResponse" %>
<%@ page import = "model.student.Student" %>
<%@ page import = "java.lang.String" %>
<%@ page import = "model.student.StudentDao" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/fmt" prefix = "fmt" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<!DOCTYPE html>
<html lang="UTF-8">

<head>
    <title></title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
    <link href="css/frame.css" rel="stylesheet">
    <link href="css/element.css" rel="stylesheet">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    <link rel="shortcut icon" type="image/x-icon" href="./static/img/aco.ico" />
</head>

<body id="container">
<c:if test="${empty cookie.id}">
	<c:redirect url = "main.jsp"></c:redirect>
</c:if>
<c:set var = "student" value = "${res.result}"/>
<c:set var = "totalCredit" value = "${student.mandatoryElectCredit + student.selectiveElectCredit + student.mandatoryMajorCredit + student.selectiveMajorCredit}"/>
<c:set var = "studentGrade" value = "${student.semester / 2 }"/>
<c:set var = "studentSemester" value = "${(student.semester % 2 ==0 ? 2 : 1)}" />
 	<script type="text/javascript">
        var signup_href = "signup.html";
        var e1 = ${student.mandatoryElectCredit};// 교필
        var e2 = ${student.mandatoryElectCredit + student.selectiveElectCredit}; // 교선
        var m1 = ${student.mandatoryMajorCredit}; //전필
        var m2 = ${student.mandatoryMajorCredit + student.selectiveMajorCredit}; // 전선
        var majorRate = ((m2 * 100)/140);
        var electiveRate = ((e2 * 100)/140);
        $(document).ready(function () {
            $("#global-Nav").load("navbar.html");
            $("#electiveChart").css("background",
                "conic-gradient(var(--e1) 0% " + e1 +
                "%, var(--e2) " + e1 + "% " + e2 +
                "%, var(--e3) " + e2 + "% 100%)");

            $("#majorChart")
                .css("background",
                    "conic-gradient(var(--m1) 0% " + m1 +
                    "%, var(--m2) " + m1 + "% " + m2 +
                    "%, var(--m3) " + m2 + "% 100%)");

            $("#majorStatus").css("width", majorRate+"%");
            $("#electiveStatus").css("width", electiveRate+"%");
        })
    </script>
    <!-- navigation bar 영역 -->
    <nav id="global-Nav"></nav>
    <!-- main 영역 -->
    <main class="main_center">
        <!-- section 영역 -->
        <section class="profile-section">
            <div>
                <img src="./static/img/studyako.png" width="50px">
                <span id="userName">${student.name}님의 프로필</span>
            </div>
            <div id="userInfo">
                <span id="userMajor">${student.major}</span>
                <span id="userSid">${student.stnum}</span>
            </div>
        </section>
        <section class="progress-rate-section">
            <div id="progress-state">
                <img id="rate-icon" src="./static/img/progress-rate.png" width="15px">
                <span>학업이수 진행율 - </span>
                <span id="totalCredit_banner">${totalCredit} 학점</span>
                <span>/</span>
                <span id="fullCredit_banner">140학점</span>
                <span id="creditRate"><fmt:formatNumber value = "${totalCredit * 100 / 140}" pattern="#.00"/>%</span>
            </div>
            <div class="progress">
                <div id="majorStatus" class="progress-bar progress-bar-striped active" role="progressbar" aria-valuenow="40"
                    aria-valuemin="0" aria-valuemax="100" style="width:40%">
                    전공
                </div>
                <div id="electiveStatus" class="progress-bar progress-bar-info progress-bar-striped active" role="progressbar"
                    aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width:20%">
                    교양
                </div>
            </div>
        </section>
        <section class="user-info-section">
            <!-- 학년정보 -->
            <div id="creditInfo">
                <div class="title-box">
                    <img src="/static/img/information.png" width="15px" height="15px">
                    <div class="title">학년 정보</div>
                </div>
                <table class="user-info-table">
                    <tr align="center">
                        <td id="presentGrade" rowspan="2" width=55%><fmt:formatNumber value = "${studentGrade}" pattern= "#"/>학년 ${studentSemester}학기</td>
                        <!-- 전공취득 -->
                        <td width="15%">전공취득</td>
                        <td id="majorCredit">${student.mandatoryMajorCredit + student.selectiveMajorCredit}학점</td>
                        <td>/</td>
                        <td id="majorfullCredit">84학점</td>
                    </tr>
                    <tr align="center">
                        <!-- 전체취득 -->
                        <td>전체취득 </td>
                        <td id="totalCredit">${totalCredit}학점</td>
                        <td>/</td>
                        <td id="fullCredit">140학점</td>
                    </tr>
                </table>
            </div>
            <!-- 학점평균 -->
            <div id="gradeInfo">
                <div class="title-box">
                    <img src="./static/img/grade.png" width="15px" height="15px">
                    <div class="title">학점 평균</div>
                </div>
                <table class="user-info-table">
                    <tr align="center">
                        <td id="totalGradeBox" rowspan="2" width=55%>
                            <span id="totalGrade_Big"></span>
                            <span><fmt:formatNumber value="${student.totalScore}" pattern="#.00"/>/ 4.5</span>
                        </td>
                        <!-- 전공취득 -->
                        <td width="15%">전공학점</td>
                        <td id="majorGrade"></td>
                        <td><fmt:formatNumber value="${student.totalMajorScore}" pattern="#.00"/>
                        <!-- <fmt:formatNumber value = "${student.totalMajorScore}" pattern="#.00"/> -->
                        </td>
                        <td id="majorfullGrade">4.5</td>
                    </tr>
                    <tr align="center">
                        <!-- 전체취득 -->
                        <td>전체학점</td>
                        <td id="totalGrade"></td>
                        <td><fmt:formatNumber value="${student.totalScore}" pattern="#.00"/></td>
                        <td id="fullGrade">4.5</td>
                    </tr>
                </table>
            </div>
        </section>
        <section class="complete-rate-section">
            <div id="electiveRate">
                <div class="title-box">
                    <img src="./static/img/liberal_arts.png" width="15px" height="15px">
                    <div class="title">교양 이수율</div>
                </div>
                <table class="chart-border">
                    <tr>
                        <td rowspan="3">
                            <!-- 파이차트 -->
                            <div class="pie-chart" id="electiveChart"></div>
                        </td>
                        <td class="color-inf">
                            <span id="reqElective" class="color-box">&nbsp;&nbsp;&nbsp;&nbsp;</span>
                            <span>교양 필수</span>
                        </td>
                    </tr>
                    <tr>
                        <td class="color-inf">
                            <span id="selElective" class="color-box">&nbsp;&nbsp;&nbsp;&nbsp;</span>
                            <span>교양 선택</span>
                        </td>
                    </tr>
                    <tr>
                        <td class="color-inf">
                            <span id="leftElective" class="color-box">&nbsp;&nbsp;&nbsp;&nbsp;</span>
                            <span>남은 교양</span>
                        </td>
                    </tr>

                </table>
            </div>

            <div id="majorRate">
                <div class="title-box">
                    <img src="./static/img/major.png" width="15px" height="15px">
                    <div class="title">전공 이수율</div>
                </div>
                <table class="chart-border">
                    <tr>
                        <td rowspan="3">
                            <!-- 파이차트 -->
                            <div class="pie-chart" id="majorChart"></div>
                        </td>
                        <td class="color-inf">
                            <span id="reqMajor" class="color-box">&nbsp;&nbsp;&nbsp;&nbsp;</span>
                            <span>전공 필수</span>
                        </td>
                    </tr>
                    <tr>
                        <td class="color-inf">
                            <span id="selMajor" class="color-box">&nbsp;&nbsp;&nbsp;&nbsp;</span>
                            <span>전공 선택</span>
                        </td>
                    </tr>
                    <tr>
                        <td class="color-inf">
                            <span id="leftMajor" class="color-box">&nbsp;&nbsp;&nbsp;&nbsp;</span>
                            <span>남은 전공</span>
                        </td>
                    </tr>

                </table>
            </div>
        </section>
    </main>
</body>
</html>