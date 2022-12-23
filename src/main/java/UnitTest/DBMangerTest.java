package UnitTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pack.DBManager;

class DBMangerTest {
	DBManager db = null;
	@BeforeEach
	void setUp() throws Exception {
		db = new DBManager();
		if(db != null) {
			db.connectDB();
		}
	}

	@AfterEach
	void tearDown() throws Exception {
		if(db != null) {
			db.closeDb();
		}
	}

	@Test
	void testGetWiFiCount() {
		int count = db.getWiFiCount();
		
		assertNotEquals(0, count);
	}

}
