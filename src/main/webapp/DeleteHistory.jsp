<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"
    import ="pack.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
	<% 
		int id = Integer.parseInt(request.getParameter("id"));
		HistoryData historyData = new HistoryData();
		History deletedHistory = historyData.get(id);
		historyData.delete(id);
	%>
	<h4>다음 히스토리를 삭제했습니다.</h4>
	<p>id = <%=deletedHistory.getId() %></p>
	<p>x좌표 = <%=deletedHistory.getX_coord() %></p>
	<p>y좌표 = <%=deletedHistory.getY_coord() %></p>
	<p>검색일 = <%=deletedHistory.getSearch_date() %></p>
	<br><br>
	<a href="History.jsp">위치 히스토리 목록으로 돌아가기</a>
</body>
</html>