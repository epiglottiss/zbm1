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
	<a href="Home.jsp">홈</a> | <a href="History.jsp">검색 History</a> | <a href="Wifiget.jsp">Open API 와이파이 가져오기</a>
	<br>
		LAT : 
		<input type="text" id ="LATinput" name="LATinput" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');">
		LNT : 
		<input type ="text" id = "LNTinput" name="LNTinput" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');">
		<button id="btnSearch">근처 와이파이 찾기</button>
	<br><br>
	<div id="table">
		<table>
			<tr>
				<th>거리(KM)</th>
				<th>관리번호</th>
				<th>자치구</th>
				<th>와이파이명</th>
				<th>도로명주소</th>
				<th>상세주소</th>
				<th>설치위치(층)</th>
				<th>설치유형</th>
				<th>설치기관</th>
				<th>서비스구분</th>
				<th>망종류</th>
				<th>설치년도</th>
				<th>실내외구분</th>
				<th>WIFI접속환경</th>
				<th>X좌표</th>
				<th>Y좌표</th>
				<th>작업일자</th>
			</tr>
			<tr>
				<td colspan="17">위치정보 입력 후 조회해 주세요.</td>
			</tr>
		</table>
	</div>	

<script type="text/javascript">
	var xhr;
	document.getElementById('btnSearch').onclick = function(){
		const LAT = document.getElementById('LATinput').value;
        const LNT = document.getElementById('LNTinput').value;
        if(LAT.length === 0 || LAT === null || LNT.length === 0 || LNT === null) return alert("LAT와 LNT를 입력하세요.")

        xhr = new XMLHttpRequest();
        var url = "Search.jsp?lat=" + LAT +"&lnt=" + LNT;  
        xhr.open("get", url, false);
        xhr.onreadystatechange = function(){
        	if(this.readyState == 4 && this.status ==200){
        		parseXml();
        	}
        }
        xhr.send();

	function parseXml(){
		var xmlObj = xhr.responseXML;
		
		var wifiTag = xmlObj.getElementsByTagName("wifi");
		var idTag = xmlObj.getElementsByTagName("id");
		var gooTag = xmlObj.getElementsByTagName("goo");
		var nameTag = xmlObj.getElementsByTagName("name");
		var dorojusoTag = xmlObj.getElementsByTagName("dorojuso");
		var detailjusoTag = xmlObj.getElementsByTagName("detailjuso");
		var floorTag = xmlObj.getElementsByTagName("floor");
		var install_typeTag = xmlObj.getElementsByTagName("install_type");
		var gigwanTag = xmlObj.getElementsByTagName("gigwan");
		var service_guboonTag = xmlObj.getElementsByTagName("service_guboon");
		var mang_typeTag = xmlObj.getElementsByTagName("mang_type");
		var install_yearTag = xmlObj.getElementsByTagName("install_year");
		var in_out_doorTag = xmlObj.getElementsByTagName("in_out_door");
		var wifi_environmentTag = xmlObj.getElementsByTagName("wifi_environment");
		var latTag = xmlObj.getElementsByTagName("lat");
		var lntTag = xmlObj.getElementsByTagName("lnt");
		var work_dateTag = xmlObj.getElementsByTagName("work_date");
		var distanceTag = xmlObj.getElementsByTagName("distance");

		var retString = "<table>";
		retString += "<tr><th>거리(KM)</th><th>관리번호</th><th>자치구</th><th>와이파이명</th><th>도로명주소</th><th>상세주소</th>"
			+"<th>설치위치(층)</th><th>설치유형</th><th>설치기관</th><th>서비스구분</th><th>망종류</th><th>설치년도</th>"
			+"<th>실내외구분</th><th>WIFI접속환경</th><th>X좌표</th><th>Y좌표</th><th>작업일자</th></tr>";
		
			//(id, goo, name, dorojuso, detailjuso, 'floor', install_type, gigwan, service_guboon, mang_type, install_year,
			// in_out_door, wifi_environment, lat, lnt, work_date)
		for(var i=0;i<wifiTag.length;i++){
			retString+="<tr>"; 
			retString+="<td>" + distanceTag[i].childNodes[0].nodeValue + "</td>";
			retString+="<td>" + idTag[i].childNodes[0].nodeValue + "</td>";
			retString+="<td>" + gooTag[i].childNodes[0].nodeValue + "</td>";
			retString+="<td>" + nameTag[i].childNodes[0].nodeValue + "</td>";
			retString+="<td>" + dorojusoTag[i].childNodes[0].nodeValue + "</td>";
			retString+="<td>" + detailjusoTag[i].childNodes[0].nodeValue + "</td>";
			retString+="<td>" + floorTag[i].childNodes[0].nodeValue + "</td>";
			retString+="<td>" + install_typeTag[i].childNodes[0].nodeValue + "</td>";
			retString+="<td>" + gigwanTag[i].childNodes[0].nodeValue + "</td>";
			retString+="<td>" + service_guboonTag[i].childNodes[0].nodeValue + "</td>";
			retString+="<td>" + mang_typeTag[i].childNodes[0].nodeValue + "</td>";
			retString+="<td>" + install_yearTag[i].childNodes[0].nodeValue + "</td>";
			retString+="<td>" + in_out_doorTag[i].childNodes[0].nodeValue + "</td>";
			retString+="<td>" + wifi_environmentTag[i].childNodes[0].nodeValue + "</td>";
			retString+="<td>" + latTag[i].childNodes[0].nodeValue + "</td>";
			retString+="<td>" + lntTag[i].childNodes[0].nodeValue + "</td>";
			retString+="<td>" + work_dateTag[i].childNodes[0].nodeValue + "</td>";
			retString+="</tr>";
		}
		retString+="</table>";
		document.getElementById("table").innerHTML = retString;
		
	}
	}
	</script>
	
	  
</body>
</html>