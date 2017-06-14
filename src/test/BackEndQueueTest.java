package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import crawler.BackEndQueue;
import crawler.Config;

public class BackEndQueueTest {

	private static BackEndQueue backEndQueue ;
	private static Config config ;
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		config = new Config();
		config.addSeed("http://www.paruvendu.fr/animaux/");
		config.addSeed("http://www.mister-chien.com/annonce/");
		backEndQueue = new BackEndQueue(config);
	}
	
	@Test
	public void verifySeeds() {
		
		ArrayList<String> seeds = config.getSeeds();
		assertEquals(seeds.get(0),"http://www.paruvendu.fr/animaux/");
		assertEquals(seeds.get(1),"http://www.mister-chien.com/annonce/");
	}
	
	@Test
	public void ShoudContainsAllSeeds() {
		
		ArrayList<String> seeds = config.getSeeds();
		List<LinkedList<String>> listQueue = backEndQueue.getBackEndQueue();
		
		for (int i = 0; i < listQueue.size(); i++) {		
			LinkedList<String> queue = listQueue.get(i);
			assertEquals(queue.get(0), seeds.get(i));
		}
		
	}
	@Test
	public void verifysize() {
		
		Integer size = backEndQueue.getSizeBackEndQueue();
		Integer s = new Integer(2);
		assertEquals(size, s);
		
	}
	@Test
	public void verifyMapLink() {
		
		Integer mapValue1 = backEndQueue.getOffsetLink("http://www.paruvendu.fr/auto-moto/");
		Integer mapValue2 = backEndQueue.getOffsetLink("http://www.paruvendu.fr/animaux/");
		
		assertNotSame(mapValue1, null);
		assertEquals(mapValue1, mapValue2);
		
	}
	
	@Test
	public void verifyRelationBetweenQueueAndLink() {
		
		LinkedList<String> queue = backEndQueue.getCorrespondingBackEndQueue("http://www.paruvendu.fr/auto-moto/");
		assertFalse(queue.contains("http://www.paruvendu.fr/auto-moto/"));
		
		backEndQueue.chooseCorrespondingBackEndQueue("http://www.paruvendu.fr/auto-moto/");
		assertTrue(queue.contains("http://www.paruvendu.fr/auto-moto/"));
		
		ArrayList<String> links = new ArrayList<String>();
		
		links.add("http://www.paruvendu.fr/animaux/");
		links.add("http://www.paruvendu.fr/auto-moto/");
		links.add("http://www.paruvendu.fr/services/");
		
		assertFalse(queue.containsAll(links));
		
		backEndQueue.chooseCorrespondingBackEndQueue("http://www.paruvendu.fr/services/");
		assertTrue(queue.containsAll(links));
		
		LinkedList<String> queue2 = backEndQueue.getCorrespondingBackEndQueue("http://www.mister-chien.com/annonce/");		
		assertTrue(queue2.size()== 1);
		assertTrue(queue2.contains("http://www.mister-chien.com/annonce/"));
	}	
	
	
	@After
	public void tearDown() throws Exception {
	}

}
