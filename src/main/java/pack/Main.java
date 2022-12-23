package pack;
public class Main {
	public static void main(String[] args) {
		
		DBManager dbManager = new DBManager();
		dbManager.connectDB();
		
		System.out.println("Hello");
		
		PublicAPI publicAPI = new PublicAPI();
		//dbManager.getShortestWifi(37, 127, 20);
		try{
			publicAPI.requestAllWiFi();
			//publicAPI.requestWiFi(11,20);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("request OK");
		
		dbManager.closeDb();
	}
	
}
