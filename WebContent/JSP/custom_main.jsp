<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Customer main</title>
</head>
<body>
<a href="index.jsp"><img src="JSP/hyundaiMobisIcon.jpg"></a>
<h2 align="center">고객사 업무 리스트</h2>
<table align="center" style="border:1px solid red; width: 30%; height: 100px; margin:auto;">
        <thead>
            <tr>
                <th></th>
            </tr>
            
            <tr>
            	<td align="center"><a href="index.jsp">메인으로 이동</a></td>
            </tr>
            
            <tr>
            	<td align="center"><a href="c_inventory.do">재고확인(주문)</a></td>
            </tr>
            
            <tr>
            	<td align="center"><a href="c_orderCheck.do">주문목록 확인<b>${modelAndView}</b></a></td>
            </tr>
            
            <tr>
            	<td align="center"><a href="cus_mf.do">제품 생산</a></td><!-- ■지수추가■ -->
            </tr>
        </thead>
 </table>
</body>
</html>