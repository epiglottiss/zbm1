<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"
    import = "pack.*"
    import = "java.sql.*"
    import = "java.util.*"
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Open WiFi Home</title>
<style>
	table {
  		width: 100%;
  		border-collapse: collapse;
	}

	th, td {
  		padding: 8px;
  		text-align: left;
  		border-bottom: 1px solid #ddd;
	}
	tr{
		tr:nth-child(even) {background-color: #f2f2f2;}
	}
	th {
  		background-color: #04AA6D;
  		color: white;
	}
</style>
</head>
<body>
	<h3>OpenWiFi</h3>
	<a href="Home.jsp">Ȩ</a> | <a href="History.jsp">�˻� History</a> | <a href="Wifiget.jsp">Open API �������� ��������</a>
	<br><br>
	<h4>��ġ �����丮 ���</h4>
	<%
		HistoryData historyData = new HistoryData();
		ArrayList<History> historyList = historyData.getAll();
	%>
	<table>
		<tr>
			<th>ID</th>
			<th>X��ǥ</th>
			<th>Y��ǥ</th>
			<th>��ȸ����</th>
			<th>���</th>
		</tr>
		<%
			for(History history : historyList){
		%>
		<tr>
			<td><% int id = history.getId(); out.print(id); %></td>
			<td><%=history.getX_coord() %></td>
			<td><%=history.getY_coord() %></td>
			<td><%=history.getSearch_date() %></td>
			<td><button id="<%=id %>" onclick="location.href='DeleteHistory.jsp?id=<%=id%>'">����</button></td>
		<%}%>
	</table>
	<script>
	</script>
</body>
</html>