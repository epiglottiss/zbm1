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
	<h4>���� �����丮�� �����߽��ϴ�.</h4>
	<p>id = <%=deletedHistory.getId() %></p>
	<p>x��ǥ = <%=deletedHistory.getX_coord() %></p>
	<p>y��ǥ = <%=deletedHistory.getY_coord() %></p>
	<p>�˻��� = <%=deletedHistory.getSearch_date() %></p>
	<br><br>
	<a href="History.jsp">��ġ �����丮 ������� ���ư���</a>
</body>
</html>