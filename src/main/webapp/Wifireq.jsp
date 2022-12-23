<?xml version="1.0" encoding="utf-8"?>
<%@ page language="java" contentType="text/XML; charset=utf-8"
    pageEncoding="utf-8"
    import = "pack.*"
    import = "java.time.*"
    import = "java.time.temporal.ChronoUnit"
%>
<info>
<wifiCount>
	<%! int wifiCount = 0;%>
	<%! long timeTaken = 0;%>
	<%
		PublicAPI publicAPI = new PublicAPI();	
		
		LocalDateTime startTime = LocalDateTime.now();
		publicAPI.requestAllWiFi();
		LocalDateTime endTime = LocalDateTime.now();
		
		WiFiData wifiData = new WiFiData();
		wifiCount = wifiData.getCount();
		
		timeTaken = ChronoUnit.MILLIS.between(startTime, endTime);
	%>
	<%= wifiCount %>
	
</wifiCount>
<timeTaken><%=timeTaken%></timeTaken>
</info>