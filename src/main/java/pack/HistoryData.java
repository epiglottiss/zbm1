package pack;

import java.sql.SQLException;
import java.util.ArrayList;

public class HistoryData extends DBManager {

	public ArrayList<History> getAll() {
		ArrayList<History> arr = new ArrayList<History>(); 
		connectDB();
		
		String sql = "select * from History;";
		
		try {
			preparedStatement = dbCon.prepareStatement(sql);
			rs = preparedStatement.executeQuery();
			while(rs.next()) {
				History history = new History();
				history.setId(rs.getInt("id"));
				history.setSearch_date(rs.getString("search_date"));
				history.setX_coord(rs.getDouble("x_coord"));
				history.setY_coord(rs.getDouble("y_coord"));
				arr.add(history);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		return arr;
	}
	
	public void insert(History history) {
		connectDB();
		String sql = "INSERT into History \r\n"
				+ "(x_coord, y_coord, search_date)\r\n"
				+ "values (?,?,?);";
		
		try {
			preparedStatement = dbCon.prepareStatement(sql);
			preparedStatement.setDouble(1, history.getX_coord());
			preparedStatement.setDouble(2, history.getY_coord());
			preparedStatement.setString(3, history.getSearch_date());
			
			preparedStatement.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeDB();
		}
	}
	
	public History get(int id) {
		connectDB();
		History history = new History();
		String sql = "select * from History where id = ?;";
		
		try {
			preparedStatement = dbCon.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			rs = preparedStatement.executeQuery();
			
			if(!rs.next()) {
				history = null;
			}
			else {
				history.setId(rs.getInt("id"));
				history.setSearch_date(rs.getString("search_date"));
				history.setX_coord(rs.getDouble("x_coord"));
				history.setY_coord(rs.getDouble("y_coord"));
			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeDB();
		}

		return history;
	}
	
	public void delete(int id) {
		connectDB();
		
		String sql = "delete from History where id =?;";
		
		try {
			preparedStatement = dbCon.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
	}
	
	public int getLastId() {
		connectDB();
		
		String sql = "select id from History order by id desc limit 1;";
		int id = 0;
		try {
			preparedStatement = dbCon.prepareStatement(sql);
			rs = preparedStatement.executeQuery();
			
			id = rs.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeDB();
		}
		return id;
	}
	
	public int getAllCount() {
		connectDB();
		int count = 0;
		String sql ="select count(*) from History;";
		try {
			preparedStatement = dbCon.prepareStatement(sql);
			rs = preparedStatement.executeQuery();
			count = rs.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeDB();
		}
		return count;
	}
}
