<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Customer Manufacturing</title>
</head>
<body>
<a href="index.jsp"><img src="JSP/hyundaiMobisIcon.jpg"></a>
<h2 align="center">고객사 생산 페이지</h2>
    
    <form action="make.do" method="post">
	<table align="center" style="width: 50%; height: 100px; margin:auto;" border="1">
        <thead> 
            <tr>
                <th bgcolor="D5D5D5">자사 제품명</th>
                <th bgcolor="D5D5D5">완제품 보유량</th>
                <th bgcolor="D5D5D5">필요 부품</th>
                <th bgcolor="D5D5D5">필요 수량</th>
                <th bgcolor="D5D5D5">필요 부품</th>
                <th bgcolor="D5D5D5">필요 수량</th>
                <th bgcolor="D5D5D5">필요 부품</th>
                <th bgcolor="D5D5D5">필요 수량</th>
            </tr>
        </thead>
        
        <tbody>
                <c:forEach items="${boardList}" var="list" varStatus="status">
                    <tr>
                        <td style="text-align: center;">
                            <b><c:out value="${list.goods_name}" /></b>
                        </td>
                        
                        <td style="text-align: center;">
                            <b><c:out value="${list.goods_inven}" /></b>
                        </td>
                        
                        <td style="text-align: center;">
                            <c:out value="A001" />
                        </td>
                        
                        <td style="text-align: center;">
                            <c:out value="3EA" />
                        </td>
                        
                        <td style="text-align: center;">
                            <c:out value="B001" />
                        </td>
                        
                        <td style="text-align: center;">
                            <c:out value="4EA" />
                        </td>
                        
                        <td style="text-align: center;">
                            <c:out value="C001" />
                        </td>
                        
                        <td style="text-align: center;">
                            <c:out value="2EA" />
                        </td>
                    </tr>
                </c:forEach>
                
                <tr>
	        		<td colspan="8" align="center">생산량 입력 : <input type="text" name="volume" required pattern="\d{0,2}" size="35" title="100개 이상은 생산할 수 없습니다." placeholder="100개 이상은 생산할 수 없습니다." />&nbsp;&nbsp;
	        		<input type="submit" value="생산하기" /></td>
        		</tr>
        </tbody>            
	
        </table>
       	</form>
        <br /> <br />
        <h2 align="center">보유 단품 재고</h2>
        <table align="center" style="width: 30%; height: 100px; margin:auto;" border="1">
        <thead>
            <tr>
                <th bgcolor="D5D5D5">단품번호</th>
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
     		<tr>
            	<th colspan="2"><input type="button" value="돌아가기" onclick="location.href='custom_main.do'"/></th>
            </tr>
        </tbody>
        </table>
         
</body>
</html>