<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Supplier Inventory</title>
</head>
<body>
<a href="index.jsp"><img src="JSP/hyundaiMobisIcon.jpg"></a>
<h2 align="center">협력사 재고 리스트</h2>
<table align="center" style="width: 50%; height: 100px; margin:auto;" border="1">
        <thead>
            <tr>
                <th bgcolor="D5D5D5">부품번호</th>
                <th bgcolor="D5D5D5">부품명</th>
                <th bgcolor="D5D5D5">재질</th>
                <th bgcolor="D5D5D5">생산가</th>
                <th bgcolor="D5D5D5">판매가</th>
                <th bgcolor="D5D5D5">현재 보유량</th>
            </tr>
        </thead>
        
         <tbody>

                <c:forEach items="${invenList}" var="list" varStatus="status">
                    <tr>
                        <td style="text-align: center;">
                            <c:out value="${list.p_num}" />
                        </td>
                        
                        <td style="text-align: center;">
                            <c:out value="${list.p_name}" />
                        </td>
                        
                        <td style="text-align: center;">
                            <c:out value="${list.p_mate}" />
                        </td>
                        
                        <td style="text-align: center;">
                            <c:out value="${list.p_mPrice}" />
                        </td>
                        
                         <td style="text-align: center;">
                            <c:out value="${list.p_sPrice}" />
                        </td>
                        
                         <td style="text-align: center;">
                            <b><c:out value="${list.p_inven}" /></b>
                        </td>
                    </tr>
                </c:forEach>

            
             <tr>
            	<th colspan="6"><input type="button" value="돌아가기" onclick="location.href='sup_main.do'"/></th>
            </tr>
        </tbody>
 </table>
 
 
 		<h2 align="center">최근 납품 리스트</h2>
		<table align="center" style="width: 50%; height: 100px; margin:auto;" border="1">
        <thead>
            <tr>
                <th bgcolor="D5D5D5">부품번호</th>
                <th bgcolor="D5D5D5">납품량</th>
                <th bgcolor="D5D5D5">납품고일</th>
                <th bgcolor="D5D5D5">납품처</th>
                <th bgcolor="D5D5D5">LOT번호</th>
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
                            <c:out value="${list.del_vol}" />
                        </td>
                        
                        <td style="text-align: center;">
                            <c:out value="${fn:substring(list.del_date,0,10)}" />
                        </td>
                        
                        <td style="text-align: center;">
                            <c:out value="${list.cus_idNum}" />
                        </td>
                        
                         <td style="text-align: center;">
                            <c:out value="${list.lotNum}" />
                        </td>
                        
                         <td style="text-align: center;">
                            <c:out value="${list.orderNum}" />
                        </td>
                        
                    </tr>
                </c:forEach>
            </c:if>
            
            <c:if test="${totalCount==0}">
                <tr>
                    <td style="text-align: center;" colspan="6">목록이 존재하지 않습니다.</td>
                </tr>
            </c:if>
           
            <tr>
				<td align="center" colspan="6"><c:out value="${pageNavigator}" escapeXml="false" /></td>
			</tr>
			
        </tbody>
         </table>
</body>
</html>