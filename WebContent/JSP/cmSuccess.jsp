<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Customer manufacturing Success</title>
<meta http-equiv="refresh" content="5; url=cus_mf.do">
</head>
<body>
<a href="index.jsp"><img src="JSP/hyundaiMobisIcon.jpg"></a>
<table align="center" style="width: 70%;" >
        <thead>
            <tr>
                <th><h1><b>${manuNum}</b>개 생산이 완료되었습니다.</h1></th>
            </tr>
            <tr>
            	<th>5초후 자동으로 제품 생산 화면으로 이동합니다.</th>
            </tr>
            <tr>
            	<th><a href="cus_mf.do">즉시이동</a></th>
            </tr>
        </thead>
 </table>
</body>
</html>