package pack;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DBManager {
	ResultSet rs = null;
	PreparedStatement preparedStatement = null;
	Connection dbCon = null;
	boolean isConnected = false;
	
	public DBManager() {
		
	}
	
	public void connectDB() {
	
		try {
			System.out.println("start connectDB");
			// SQLite JDBC 체크
			Class.forName("org.sqlite.JDBC");
			
			System.out.println("jdbc check ok.");
			// SQLite 데이터베이스 파일에 연결
			String dbFile = "C:\\Users\\user\\Desktop\\std\\zerobase-backend\\dev\\sqlite\\db\\OpenWiFi.db";
			dbCon = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
			System.out.println("dbCon OK");
			isConnected = true;
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean isDBConnected() {
		return isConnected;
	}
	
	public Connection getDbCon() {
		return dbCon;
	}
	
	public void closeDb(){
        try {
            if(rs != null && !rs.isClosed()){
                rs.close();
                System.out.println("rs closed.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
//                if(statement != null && !statement.isClosed()){
//                    statement.close();
//                }
            if(preparedStatement != null && !preparedStatement.isClosed()){
                preparedStatement.close();
                System.out.println("preparedStatement closed.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            if(dbCon != null && !dbCon.isClosed()){
                dbCon.close();
                System.out.println("DB Connection closed.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

//	private boolean isWiFiExist(WiFi wifi) {
//		String sql = "select count(*) from WiFi where id is ?;";
//		try {
//			preparedStatement = dbCon.prepareStatement(sql);
//			preparedStatement.setString(1, wifi.getId());
//			
//			rs = preparedStatement.executeQuery();
//			int count = 0;
//			if(rs.next()) {
//				count = rs.getInt(1);
//			}
//			if(count>0) {
//				return true;
//			}
//			return false;
//		} catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//	}
	
	public int getWiFiCount() {
		String sql = "select count(*) from WiFi;";
		try {
			preparedStatement = dbCon.prepareStatement(sql);
			
			rs = preparedStatement.executeQuery();
			return rs.getInt(1);

		} catch (SQLException e) {
            throw new RuntimeException(e);
        }
		
	}
	
	
	public void insertWiFi(WiFi wifi) {
//		if(isWiFiExist(wifi))
//			return;
		
		String sql = "replace into WiFi\r\n"
				+ "(id, goo, name, dorojuso, detailjuso, 'floor', install_type, gigwan, service_guboon, mang_type, install_year, "
				+ "in_out_door, wifi_environment, lat, lnt, work_date)\r\n"
				+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        try {
            preparedStatement = dbCon.prepareStatement(sql);
            preparedStatement.setString(1, wifi.getId());
            preparedStatement.setString(2, wifi.getGoo());
            preparedStatement.setString(3, wifi.getName());
            preparedStatement.setString(4, wifi.getDorojuso());
            preparedStatement.setString(5,wifi.getDetailjuso());
            preparedStatement.setString(6,wifi.getFloor());
            preparedStatement.setString(7,wifi.getInstall_type());
            preparedStatement.setString(8,wifi.getGigwan());
            preparedStatement.setString(9,wifi.getService_guboon());
            preparedStatement.setString(10,wifi.getMang_type());
            preparedStatement.setString(11, wifi.getInstall_year());
            preparedStatement.setString(12,wifi.getIn_out_door());
            preparedStatement.setString(13,wifi.getWifi_environment());
            preparedStatement.setDouble(14,wifi.getLat());
            preparedStatement.setDouble(15,wifi.getLnt());
            preparedStatement.setString(16,wifi.getWork_date());          
            
            int affectedRows = preparedStatement.executeUpdate();

            System.out.println(affectedRows + " records inserted by DBManager.insertWiFiList()");
        } 
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
	}
	
	public void insertWiFiList(ArrayList<WiFi> wifiList) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append( "replace into WiFi\r\n"
				+ "(id, goo, name, dorojuso, detailjuso, 'floor', install_type, gigwan, service_guboon, mang_type, install_year, "
				+ "in_out_door, wifi_environment, lat, lnt, work_date)\r\n"
				+ "values ");
//				+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		
        try {        	
        	for(int i=0;i<wifiList.size();i++) {
        		String record = getWifiRecord(wifiList.get(i));
        		sqlBuilder.append(record);
        		if(i != wifiList.size()-1) {
        			sqlBuilder.append(",\r\n");
        		}
        	}
        	String sql = sqlBuilder.toString();
        	preparedStatement = dbCon.prepareStatement(sql);
        	
        	int affectedRows = preparedStatement.executeUpdate();
    
            System.out.println(affectedRows + " records inserted by DBManager.insertWiFiList()");
            
        } 
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
	}
	
	//Pair<double, WiFi> ?
	public ArrayList<WiFi> getShortestWifi(double LAT, double LNT, int wifiCount){
		ArrayList<WiFi> wifiList = new ArrayList<WiFi>();

		if(dbCon==null) {
			System.out.println("dbCon is null. method : getShortestWifi");
			return wifiList;
		}
		
		//Calcuate distance using Harversine formula 
		String sql = "select *\r\n"
				+ "	, (6371 * acos(cos(radians(?))\r\n"	//LAT
				+ "		*cos(radians(wf.lat))\r\n"
				+ "		*cos(radians(wf.lnt)-radians(?))\r\n"	//LNT
				+ "		+sin(radians(?))\r\n"	//LAT
				+ "		*sin(radians(wf.lat)))) as distance\r\n"
				+ "from WiFi wf\r\n"
				+ "order by distance asc\r\n"
				+ "limit ?;";
		try {
			preparedStatement = dbCon.prepareStatement(sql);
			preparedStatement.setDouble(1, LAT);
			preparedStatement.setDouble(2, LNT);
			preparedStatement.setDouble(3, LAT);
			preparedStatement.setInt(4, wifiCount);
			
			rs = preparedStatement.executeQuery();
			
			//(id, goo, name, dorojuso, detailjuso, 'floor', install_type, gigwan, service_guboon, mang_type, install_year,
			// in_out_door, wifi_environment, lat, lnt, work_date)
			while(rs.next()) {
				//rs -> dto instance로 바꿔주는 건 없을까?
				WiFi wifi = new WiFi();
				wifi.setId(rs.getString("id"));
				wifi.setGoo(rs.getString("goo"));
				wifi.setName(rs.getString("name"));
				wifi.setDorojuso(rs.getString("dorojuso"));
				wifi.setDetailjuso(rs.getString("detailjuso"));
				wifi.setFloor(rs.getString("floor"));
				wifi.setInstall_type(rs.getString("install_type"));
				wifi.setGigwan(rs.getString("gigwan"));
				wifi.setService_guboon(rs.getString("service_guboon"));
				wifi.setMang_type(rs.getString("mang_type"));
				wifi.setInstall_year(rs.getString("install_year"));
				wifi.setIn_out_door(rs.getString("in_out_door"));
				wifi.setWifi_environment(rs.getString("wifi_environment"));
				wifi.setLat(rs.getDouble("lat"));
				wifi.setLnt(rs.getDouble("lnt"));
				wifi.setWork_date(rs.getString("work_date"));
				wifi.setDistance(Math.round(rs.getDouble("distance")*1000)/1000.0);
				wifiList.add(wifi);
			}
						
			return wifiList;

		} catch (SQLException e) {
            throw new RuntimeException(e);
        }
	}
	
	public String getWifiRecord(WiFi wifi) {
		//(id, goo, name, dorojuso, detailjuso, 'floor', install_type, gigwan, service_guboon, mang_type, install_year,
		// in_out_door, wifi_environment, lat, lnt, work_date)
		
		Field[] field = wifi.getClass().getDeclaredFields();
		ArrayList<Field> list = new ArrayList<Field>(Arrays.asList(field));
		list.removeIf(f->(f.getName().equals("distance")));
		String ret = list.stream()
						.map(f->{
							String str="";
							try {
								str = String.valueOf(f.get(wifi));
							} catch (IllegalArgumentException e) {
								e.printStackTrace();
							} catch (IllegalAccessException e) {
								e.printStackTrace();
							}
							if(str.isBlank()) {
								return "\'\'";
							};
							
							return "\""+ str + "\"";
						})
						.collect(Collectors.toList()).stream()
						.collect(Collectors.joining(",","(",")"));
		return ret;
	}
}
