package pack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBManager {
	ResultSet rs = null;
	PreparedStatement preparedStatement = null;
	Connection dbCon = null;
	
	public void connectDB() {
	
		try {
			// SQLite JDBC 체크
			Class.forName("org.sqlite.JDBC");
			
			// SQLite 데이터베이스 파일에 연결
			String dbFile = "C:\\Users\\user\\Desktop\\std\\zerobase-backend\\dev\\sqlite\\db\\OpenWiFi.db";
			dbCon = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void closeDB(){
        try {
            if(rs != null && !rs.isClosed()){
                rs.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
//          if(statement != null && !statement.isClosed()){
//              statement.close();
//          }
            if(preparedStatement != null && !preparedStatement.isClosed()){
                preparedStatement.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            if(dbCon != null && !dbCon.isClosed()){
                dbCon.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
