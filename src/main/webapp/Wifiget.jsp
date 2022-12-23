<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"
    import = "pack.*"
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Request Open API</title>
</head>
<body>
	<div id="comment">
		<h3>와이파이를 가져오는 중입니다.</h3>
		
	</div>
	<a href="Home.jsp">홈으로 가기</a>
	<script type="text/javascript">
	var xhr;
	startRequest();
	function startRequest() {
		xhr = new XMLHttpRequest();
		xhr.onreadystatechange = callback;
		xhr.open("POST","Wifireq.jsp", true);
		xhr.send();
		
	}
	
	function callback(){
		if (xhr.readyState==4 && xhr.status==200){
			var xmlObj = xhr.responseXML;
			var wifiCount = xmlObj.getElementsByTagName("wifiCount")[0].childNodes[0].nodeValue;
			var str = "<h3>" + wifiCount + " 개의 Wifi 정보가 저장되었습니다. </h3>";
			document.getElementById("comment").innerHTML = str;
		}
	}
	
	
	

	</script>
	
	
</body>
</html>