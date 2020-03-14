<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Delivery Fail</title>
<meta http-equiv="refresh" content="5; url=s_inventory.do">
</head>
<body>
<a href="index.jsp"><img src="JSP/hyundaiMobisIcon.jpg"></a>
<table align="center" style="width: 70%;" >
        <thead>
            <tr>
                <th><h1>재고가 부족합니다. 재고를 확인해 주세요.</h1></th>
            </tr>
            <tr>
            	<th>5초후 자동으로 재고 확인 화면으로 이동합니다.</th>
            </tr>
            <tr>
            	<th><a href="s_inventory.do">즉시이동</a></th>
            </tr>
        </thead>
 </table>
</body>
</html>