package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import crawler.BackEndQueue;
import crawler.Config;
import crawler.UrlFrontier;

public class UrlFrontierTest {

	private static BackEndQueue backEndQueue ;
	private static Config config ;
	private static UrlFrontier urlFrontier;
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		config = new Config();
		config.addSeed("http://www.paruvendu.fr/animaux/");
		config.addSeed("http://www.mister-chien.com/annonce/");
		backEndQueue = new BackEndQueue(config);
		urlFrontier = new UrlFrontier(backEndQueue);
		
	}
	
	@Test
	public void verifyRangeOfPickedQueue() {
		
		int randomInt = urlFrontier.randInt(0, backEndQueue.getSizeBackEndQueue());
		assertTrue((randomInt >= 0) && (randomInt < backEndQueue.getSizeBackEndQueue()));
	}
		
	@After
	public void tearDown() throws Exception {
	}

}
