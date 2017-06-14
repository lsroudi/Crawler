package crawler;

import doap.DoapException;


public class MyRunner extends Thread {


	Config config;
	BackEndQueue backEndQueue;

	public void run() {
		
		config = new Config();

		backEndQueue = new BackEndQueue(config);
		
		// init urlFrontier
		UrlFrontier urlFrontier = new UrlFrontier(backEndQueue);
		// create instance of crawler

		
		synchronized (urlFrontier) {
			Crawler crawler = new Crawler(urlFrontier);
			try {
				crawler.bfs();
			} catch (DoapException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


		
	}



}
