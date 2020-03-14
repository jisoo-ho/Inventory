<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Customer Order List</title>
</head>
<body>
<a href="index.jsp"><img src="JSP/hyundaiMobisIcon.jpg"></a>
<h2 align="center">주문목록 및 처리 현황</h2>
<table align="center" style="width: 50%; height: 100px; margin:auto;" border="1">
        <thead>
            <tr>
                <th bgcolor="D5D5D5">부품번호</th>
                <th bgcolor="D5D5D5">주문개수</th>
                <th bgcolor="D5D5D5">주문일</th>
                <th bgcolor="D5D5D5">메모</th>
                <th bgcolor="D5D5D5">완료여부</th>
                <th bgcolor="D5D5D5">주문번호</th>
            </tr>
        </thead>
        
         <tbody>
            <c:if test="${totalCount!=0}">
                <c:forEach items="${boardList}" var="list" varStatus="status">
                    <tr>
                        <td style="text-align: center;">
                            <c:out value="${list.p_num}" />
                        </td>
                        
                        <td style="text-align: center;">
                            <c:out value="${list.order_vol}" />
                        </td>
                        
                        <td style="text-align: center;">
                            <c:out value="${fn:substring(list.order_date,0,10)}" />
                        </td>
                        
                        <td style="text-align: center;">
                            <c:out value="${list.memo}" />
                        </td>
                        
                         <td style="text-align: center;">
                            <c:if test="${list.complete eq 'A'}"> 납품 대기 </c:if>
                            <c:if test="${list.complete eq 'B'}"> 납품 완료 </c:if>
                        </td>
                        
                         <td style="text-align: center;">
                            <c:out value="${list.orderNum}" />
                        </td>
                    </tr>
                </c:forEach>
            </c:if>
            
            <c:if test="${totalCount==0}">
                <tr>
                    <td style="text-align: center;" colspan="7">목록이 존재하지 않습니다.</td>
                </tr>
            </c:if>
            <tr>
				<td align="center" colspan="7"><c:out value="${pageNavigator}" escapeXml="false" /></td>
			</tr>
            <tr>
            	<th colspan="7"><input type="button" value="돌아가기" onclick="location.href='custom_main.do'"/></th>
            </tr>
        </tbody>
        </table>
         
</body>
</html>