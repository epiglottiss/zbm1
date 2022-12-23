<?xml version="1.0" encoding="utf-8"?>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.time.LocalDateTime"%>
<%@ page language="java" contentType="text/XML; charset=UTF-8"
    pageEncoding="UTF-8"
    import = "pack.*"
    import = "java.util.*"
    import = "java.sql.*"
    import = "org.sqlite.*"
%>

<WiFiList>

<%
	DBManager db = new DBManager();
	ArrayList<WiFi> wifiList;
	 
	try{
		double lat = Double.parseDouble(request.getParameter("lat"));
		double lnt = Double.parseDouble(request.getParameter("lnt"));
		HistoryData hData = new HistoryData();
		History history = new History();
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd hh/mm/ss");
		String searchDate = now.format(formatter);
		
		history.setSearch_date(searchDate);
		history.setX_coord(lat);
		history.setY_coord(lnt);
		
		hData.insert(history);
		///////////////////////////////

		db.connectDB();
		wifiList = db.getShortestWifi(lat, lnt, 20);
		for(int i=0; i<wifiList.size();i++){
			WiFi wifi = wifiList.get(i);
	
	
%>

	<wifi>
		<id><% out.print(wifi.getId()); %></id>
		<goo><%=wifi.getGoo() %></goo>
		<name><%=wifi.getName()%></name>
		<dorojuso><%=wifi.getDorojuso()%></dorojuso>
		<detailjuso><%=wifi.getDetailjuso() %></detailjuso>
		<floor><% if(wifi.getFloor().isBlank()){out.print(" ");} else {out.print(wifi.getFloor());}%></floor>
		<install_type><%=wifi.getInstall_type() %></install_type>
		<gigwan><%=wifi.getGigwan() %></gigwan>
		<service_guboon><%=wifi.getService_guboon() %></service_guboon>
		<mang_type><%=wifi.getMang_type() %></mang_type>
		<install_year><%=wifi.getInstall_year() %></install_year>
		<in_out_door><%=wifi.getIn_out_door() %></in_out_door>
		<wifi_environment><% if(wifi.getWifi_environment().isBlank()){out.print(" ");} else{wifi.getWifi_environment();} %></wifi_environment>
		<lat><%=wifi.getLat() %></lat>
		<lnt><%=wifi.getLnt() %></lnt>
		<work_date><%=wifi.getWork_date() %></work_date>
		<distance><%=wifi.getDistance() %></distance>
	</wifi>
<%}
	}catch(Exception e) {
		e.printStackTrace();
	}finally{
		db.closeDb();
	}
	%>
</WiFiList>

