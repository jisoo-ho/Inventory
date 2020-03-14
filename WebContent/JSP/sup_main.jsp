<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Supplier Main</title>
</head>
<body>
<a href="index.jsp"><img src="JSP/hyundaiMobisIcon.jpg"></a>
<h2 align="center">협력사 업무 리스트</h2>
<table align="center" style="border:1px solid red; width: 30%; height: 100px; margin:auto;">
        <thead>
            <tr>
                <th></th>
            </tr>
            <tr>
            	<td align="center"><a href="index.jsp">메인으로 이동</a></td>
            </tr>
            <tr>
            	<td align="center"><a href="s_inventory.do">재고확인</a></td>
            </tr>
            <tr>
            	<td align="center"><a href="s_checkOrder.do">주문 요청 목록 확인 <b>${modelAndView}</b></a></td>
            </tr>
        </thead>
 </table>
</body>
</html>