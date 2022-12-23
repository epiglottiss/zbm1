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
	<br>
		LAT : 
		<input type="text" id ="LATinput" name="LATinput" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');">
		LNT : 
		<input type ="text" id = "LNTinput" name="LNTinput" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');">
		<button id="btnSearch">��ó �������� ã��</button>
	<br><br>
	<div id="table">
		<table>
			<tr>
				<th>�Ÿ�(KM)</th>
				<th>������ȣ</th>
				<th>��ġ��</th>
				<th>�������̸�</th>
				<th>���θ��ּ�</th>
				<th>���ּ�</th>
				<th>��ġ��ġ(��)</th>
				<th>��ġ����</th>
				<th>��ġ���</th>
				<th>���񽺱���</th>
				<th>������</th>
				<th>��ġ�⵵</th>
				<th>�ǳ��ܱ���</th>
				<th>WIFI����ȯ��</th>
				<th>X��ǥ</th>
				<th>Y��ǥ</th>
				<th>�۾�����</th>
			</tr>
			<tr>
				<td colspan="17">��ġ���� �Է� �� ��ȸ�� �ּ���.</td>
			</tr>
		</table>
	</div>	

<script type="text/javascript">
	var xhr;
	document.getElementById('btnSearch').onclick = function(){
		const LAT = document.getElementById('LATinput').value;
        const LNT = document.getElementById('LNTinput').value;
        if(LAT.length === 0 || LAT === null || LNT.length === 0 || LNT === null) return alert("LAT�� LNT�� �Է��ϼ���.")

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
		retString += "<tr><th>�Ÿ�(KM)</th><th>������ȣ</th><th>��ġ��</th><th>�������̸�</th><th>���θ��ּ�</th><th>���ּ�</th>"
			+"<th>��ġ��ġ(��)</th><th>��ġ����</th><th>��ġ���</th><th>���񽺱���</th><th>������</th><th>��ġ�⵵</th>"
			+"<th>�ǳ��ܱ���</th><th>WIFI����ȯ��</th><th>X��ǥ</th><th>Y��ǥ</th><th>�۾�����</th></tr>";
		
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