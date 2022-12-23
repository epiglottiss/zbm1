package UnitTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pack.*;

class WiFiDataTest {
	WiFiData wifiData = new WiFiData();
	WiFi wifi = new WiFi();
	String testId = "testId";
	String testString1 = "";
	String testString2 = "\'";
	String testString3 = " ";
	String testString4 = "\"";
	
	@BeforeEach
	void setUp() throws Exception {
		wifi.setId(testId);
		wifi.setGoo(testString1);
		wifi.setName(testString2);
		wifi.setDorojuso(testString3);
		wifi.setDetailjuso(testString4);

		ArrayList<WiFi> arr = new ArrayList<WiFi>();
		arr.add(wifi);
		//Can test getShortestWifi, because insertList makes and executes SQL using getShortestWifi.
		wifiData.insertList(arr);
		
		//wifiData.insert(wifi);	//use insert if insertList throws an exception;
	}

	@AfterEach
	void tearDown() throws Exception {
		wifiData.delete(testId);
	}

	@Test
	void testGetCount() {
		int count = wifiData.getCount();
		
		assertNotEquals(0, count);
	}

	@Test
	void testInsert() {
		int beforeCount = wifiData.getCount();

		String testId1 = "insert Test1";
		WiFi wifi1 = new WiFi();
		wifi1.setId(testId1);

		int result = wifiData.insert(wifi1);
		
		
		wifiData.delete(testId1);
		
		assertEquals(1, result);
	}
	
	@Test
	void testInsertList() {		
		ArrayList<WiFi> arr = new ArrayList<WiFi>();
		String testId1 = "insert Test1";
		String testId2 = "insert Test2";
		WiFi wifi1 = new WiFi();
		wifi1.setId(testId1);
		WiFi wifi2 = new WiFi();
		wifi2.setId(testId2);
		
		arr.add(wifi1);
		arr.add(wifi2);
		
		int result = wifiData.insertList(arr);
		
		wifiData.delete(testId1);
		wifiData.delete(testId2);
		
		assertEquals(2, result);
	}

	@Test
	void testGetShortestWifi() {
		double lat = 32.1324;
		double lnt = 123.1234;
		int wifiCount = 5;
		double resultDistance = wifiData.getShortestWifi(lat, lnt, wifiCount).get(0).getDistance();
		
		assertEquals(669.696, resultDistance);
	}

	@Test
	void testGetWifiRecord() {
		//test can be done at setup phase.
		assertEquals("('testId','','''','','\"','','','','','','','','','0.0','0.0','')", wifiData.getWifiRecord(wifi));
	}

}
