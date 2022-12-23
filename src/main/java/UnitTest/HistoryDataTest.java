package UnitTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pack.History;
import pack.HistoryData;

class HistoryDataTest {
	HistoryData historyData = new HistoryData();
	int testId = 0;
	
	@BeforeEach
	void setUp() throws Exception {
		History history = new History();
		history.setX_coord(1);
		history.setY_coord(1);
		historyData.insert(history);
		
		testId = historyData.getLastId();
	}

	@AfterEach
	void tearDown() throws Exception {
		if(historyData.get(testId) != null) {
			historyData.delete(testId);
		}
		else {
			return;
		}
	}

	@Test
	void testGetAll() {

		int historyCount1 = historyData.getAll().size();
		int historyCount2 = historyData.getAllCount();
		
		assertEquals(historyCount1, historyCount2);
	}

	@Test
	void testInsert() {
		int beforeCount = historyData.getAll().size();
		
		History history = new History();
		history.setX_coord(1);
		history.setY_coord(1);
		historyData.insert(history);
		
		int afterCount = historyData.getAllCount();
		
		historyData.delete(historyData.getLastId());
		
		assertEquals(beforeCount+1, afterCount);
	}

	@Test
	void testGet() {
		int getId = testId;
		History history = historyData.get(getId);
		
		assertNotEquals(null, history);
	}

	@Test
	void testDelete() {
		int beforeCount = historyData.getAllCount();
		historyData.delete(testId);
		int afterCount = historyData.getAllCount();
		
		assertEquals(beforeCount-1, afterCount);
	}
	
	@Test
	void testGetLastId() {
		int id = historyData.getLastId();
		
		assertEquals(testId, id);
	}

}
