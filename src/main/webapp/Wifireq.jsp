<?xml version="1.0" encoding="utf-8"?>
<%@ page language="java" contentType="text/XML; charset=utf-8"
    pageEncoding="utf-8"
    import = "pack.*"%>
<wifiCount>
	<%
	int wifiCount=0;
		PublicAPI publicAPI = new PublicAPI();			
		publicAPI.requestAllWiFi();
		DBManager dbManager = new DBManager();
		
		dbManager.connectDB();
		wifiCount = dbManager.getWiFiCount();
		dbManager.closeDb();
	%>
	<%= wifiCount %>
</wifiCount>