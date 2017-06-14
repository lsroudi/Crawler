package crawler;


public class Main {


	public static void main(String[] args) {
		
//		ContentExtractor extractor = new ContentExtractor("http://www.uncompagnon.fr/detail/30490/chatons-sacre-de-birmanie.html");
//		extractor.extract();
		
//		UrlSeen urlSeen = new UrlSeen();
//		urlSeen.save("http://www.uncompagnon.fr/detail/30490/chatons-sacre-de-birmanie.html");
		
		MyRunner runner = new MyRunner();
		
		runner.start();
	}
}
