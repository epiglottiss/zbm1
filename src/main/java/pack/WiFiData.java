package pack;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class WiFiData extends DBManager{

	//deprecated
	public int insert(WiFi wifi) {
		connectDB();
		
		String sql = "replace into WiFi\r\n"
				+ "(id, goo, name, dorojuso, detailjuso, 'floor', install_type, gigwan, service_guboon, mang_type, install_year, "
				+ "in_out_door, wifi_environment, lat, lnt, work_date)\r\n"
				+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		int count = 0;
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
            
            count = preparedStatement.executeUpdate();
        } 
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
        	closeDB();
        }        
        return count;
	}
	
	public int insertList(ArrayList<WiFi> wifiList) {
		connectDB();
		
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append( "replace into WiFi\r\n"
				+ "(id, goo, name, dorojuso, detailjuso, 'floor', install_type, gigwan, service_guboon, mang_type, install_year, "
				+ "in_out_door, wifi_environment, lat, lnt, work_date)\r\n"
				+ "values ");
		int count = 0;
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
        	
        	count = preparedStatement.executeUpdate();
        } 
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
        	closeDB();
        }
        return count;
	}
	
	public ArrayList<WiFi> getShortestWifi(double LAT, double LNT, int wifiCount){
		connectDB();
		ArrayList<WiFi> wifiList = new ArrayList<WiFi>();
		
		//Calcuate distance using Harversine formula 
		String sql = "select *\r\n"
				+ "	, (6371 * acos(cos(radians(?))\r\n"	//LAT
				+ "		*cos(radians(wf.lat))\r\n"
				+ "		*cos(radians(wf.lnt)-radians(?))\r\n"	//LNT
				+ "		+sin(radians(?))\r\n"	//LAT
				+ "		*sin(radians(wf.lat)))) as distance\r\n"
				+ "from WiFi wf\r\n"
				+ "where wf.lat is not null and wf.lnt is not null "
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
				//rs -> dto instance
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
		} catch (SQLException e) {
            throw new RuntimeException(e);
        }
		finally {
			closeDB();
		}
		return wifiList;
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
								if(str.contains("'")) {
									str = str.replace("'", "''");
								}
							} catch (IllegalArgumentException e) {
								e.printStackTrace();
							} catch (IllegalAccessException e) {
								e.printStackTrace();
							}
							if(str.isBlank()) {
								return "\'\'";
							};
							
							return "\'"+ str + "\'";
						})
						.collect(Collectors.toList()).stream()
						.collect(Collectors.joining(",","(",")"));
		return ret;
	}
	
	public int getCount() {
		int count = 0;
		connectDB();
		String sql = "select count(*) from WiFi;";
		try {
			preparedStatement = dbCon.prepareStatement(sql);
			
			rs = preparedStatement.executeQuery();
			count = rs.getInt(1);

		} catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
        	closeDB();    	
        }
		return count;	
	}
	
	public void delete(String id) {
		connectDB();
		
		String sql = "delete from History where id =?;";
		
		try {
			preparedStatement = dbCon.prepareStatement(sql);
			preparedStatement.setString(1, id);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
	}
}
