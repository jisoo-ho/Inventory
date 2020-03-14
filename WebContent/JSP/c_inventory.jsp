<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Customer Inventory List</title>
</head>
<body>
<a href="index.jsp"><img src="JSP/hyundaiMobisIcon.jpg"></a>
<h2 align="center">고객사 재고 리스트</h2>
<table align="center" style="width: 35%; height: 100px; margin:auto;" border="1">
        <thead>
            <tr>
                <th bgcolor="D5D5D5">부품번호</th>
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
                            <c:out value="${list.cus_inven}" />
                        </td>
                    </tr>
                </c:forEach>
      
        </tbody>
         </table>
         <br /> <br />
         
         <form action="order.do" method="post">
        <table align="center" style="width: 50%; height: 100px; margin:auto;" border="1">
        <thead>
        	<tr>
        		<h2 align="center">주문하기</h2>
        	</tr>
        	
        	<tr>
        		<th bgcolor="D5D5D5" align="center">품번</th>
        		<th bgcolor="D5D5D5" align="center">주문처</th>
        		<th bgcolor="D5D5D5" align="center">수량</th>
        		<th bgcolor="D5D5D5" align="center">메모</th>
    		</tr>
    		
    		<tr>
        		<td align="center">
        		<select name="p_num">
        			<option>A001</option>
        			<option>B001</option>
        			<option>C001</option>
        		</select>
        		
        		</td>
        		<td align="center">
        		<select name="sup_idNum">
        			<option>SUP001</option>
        		</select>
        		</td>
        		
        		<td align="center"><input type="text" name="volume" required pattern="\d{0,3}" size="35" title="1000개 이상은 주문할 수 없습니다." /></td>
        		<td align="center"><input type="text" name="memo" maxlength="30" placeholder="30자 이내로 입력" size="35" /></td>
    		</tr>
        </thead>
        <tbody>
        	<tr><th colspan="4"><input type="submit" value="주문하기" />&nbsp;&nbsp;
        	<input type="hidden" value="CUS001" name="cus_idNum" />
        	<input type="button" value="돌아가기" onclick="location.href='custom_main.do'" />
        	
        	</th></tr>
        </tbody>
		</table>
		</form>
		<br /><br />
		
		<h2 align="center">최근 입고 리스트</h2>
		<table align="center" style="width: 50%; height: 100px; margin:auto;" border="1">
        <thead>
            <tr>
                <th bgcolor="D5D5D5">부품번호</th>
                <th bgcolor="D5D5D5">입고량</th>
                <th bgcolor="D5D5D5">입고일</th>
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
                            <c:out value="${list.sup_idNum}" />
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