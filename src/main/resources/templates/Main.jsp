<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="utf-8" />
    <meta
            name="viewport"
            content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0"
    />

    <title></title>

    <style type="text/css">
        .pencilLogo{

            width:30px;
            height:30px;

        }
        .login-stat{

            margin-top:10px;
            margin-bottom:10px;
            margin-left:30px;
            list-style: none;
        }
        .forMargin {
            margin-top: 15px;
        }
    </style>
</head>


<body>
        <sec:authorize access=" isAuthenticated()">
        <!-- Dashboard -->
        <li class="menu-item active">
            <a href="${pageContext.request.contextPath }/" class="menu-link">
                <i class="menu-icon tf-icons bx bx-home-circle"></i>
                <div data-i18n="Analytics">메인</div>
            </a>
        </li>

            <li class="login-stat">
                <a href="${pageContext.request.contextPath }/user/MyPageForm" class="menu-link">
                    <div class="menu-icon tf-icons bx bx-user"></div>
                    <sec:authentication property="principal.EmpDto.Emp_name"/>님 안녕하세요 !
                </a>
            </li>
            <li class="login-stat">
            <sec:authorize access="isAuthenticated()">
              <a href="<c:url value="/logout"/>" class="menu-link">
                <div class='menu-icon tf-icons bx bx-log-out' ></div>
                <div data-i18n="Account">로그아웃</div>
            </a>
            </sec:authorize>

        </li>
        </sec:authorize>

</body>
</html>
