<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Delivery check</title>
</head>
<body>
<a href="index.jsp"><img src="JSP/hyundaiMobisIcon.jpg"></a>
<h2 align="center">납품 확인</h2>
	<form action="delivery_final.do" method="post">
	<input type="hidden" value="${orderNum}" name="orderNum" />
	<input type="hidden" value="${order_vol}" name="order_vol" />
	<input type="hidden" value="${p_num}" name="p_num" />
		<table align="center" style="width: 30%;" border="1">
        	<thead>
            	<tr>
                	<th bgcolor="D5D5D5" colspan="2">정말 납품 하시겠습니까?</th>
            	</tr>
            
            	<tr>
            			
            		<th><input type="submit" value="예" /></th>
            		<th><input type="button" value="돌아가기" onclick="location.href='s_checkOrder.do'" /></th>
            	</tr>
        	</thead>
 		</table>
	 </form>
</body>
</html>