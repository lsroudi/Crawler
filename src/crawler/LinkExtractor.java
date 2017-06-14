package crawler;
import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class LinkExtractor {

	public LinkExtractor() {
		// TODO Auto-generated constructor stub
	}

	public Elements getLinks(String v) {
		Document doc = null;
		Elements links = null;
		try {
			
		Connection con = Jsoup.connect(v);
		con.timeout(300000);
		Connection.Response resp = con.execute();
		
		    if (resp.statusCode() == 200) {
		        doc = con.get();
				links = doc.select("a");
		    }
		        
		        
		} catch (IllegalArgumentException e) {

//			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return links;
	}

	public String getBaseUri(String s) {

		String baseUri = null;
		try {
			baseUri = Jsoup.connect(s).get().baseUri();
		} catch (IllegalArgumentException e) {
			System.err.println(e);
			// do nothing to force baseUri to be null
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println(e);
		}

		return baseUri;
	}
}
