package crawler;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class ContentExtractor {

	public enum Base {
		MISTER, PARUVENDU, COMPAGNON,TOPANNONCE, ANIDONNE
	}

	private String url;
	private HashMap<String,String> property = new HashMap<String, String>();
	
	public ContentExtractor(String link) {
		url = link;
	}



	public HashMap<String,String> extract() {
		
		Connection con = Jsoup.connect(url);
		con.userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.118 Safari/537.36");
		con.timeout(5000);

		try {			
			Base base = null;
			
			if (("www.mister-chien.com").equals(getHost(url))) {base = Base.MISTER;}
			if (("www.paruvendu.fr").equals(getHost(url))) {base = Base.PARUVENDU;}
			if (("www.uncompagnon.fr").equals(getHost(url))) {base = Base.COMPAGNON;}
			if (("www.topannonces.fr").equals(getHost(url))) {base = Base.TOPANNONCE;}
			if (("www.anidonne.com").equals(getHost(url))) {base = Base.ANIDONNE;}

			switch (base) {
			case MISTER:
				mister(con);
				break;
			case PARUVENDU:
				paruVendu(con);
				break;
			case COMPAGNON:
				compagnon(con);
			case TOPANNONCE:
				topAnnonce(con);
			case ANIDONNE:
				anidonne(con);
				break;				
			default:
				break;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (NullPointerException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		return property;
	}



	private void anidonne(Connection con) throws IOException {

		Document doc = null;
		doc = con.get();
		String description = doc.select("meta[property=og:description]").attr("content");
		Element image = doc.select("div.imageRow img").first();
		System.out.println(description);
		
		if (image != null) {
			String url = image.absUrl("src");
			property.put("img", getImages(url).substring(1));
		}
		if(description != null){
			property.put("description", description);
		}
		
	}



	private void topAnnonce(Connection con) throws IOException {

		Document doc = null;
		doc = con.get();
		String description = doc.select("p.detailAnnonce").text();
		Element image = doc.select("img#bigpic").first();
		System.out.println(description);
		
		if (image != null) {
			String url = image.absUrl("src");
			property.put("img", getImages(url).substring(1));
		}
		if(description != null){
			property.put("description", description);
		}
		
	}



	private void compagnon(Connection con) throws IOException {

		Document doc = null;
		doc = con.get();
		String description = doc.select("p[itemprop]").text();
		Element image = doc.select("img.img-circle.img-responsive").first();
//		System.out.println(description);
		
		if (image != null) {
			String url = image.absUrl("src");
			
			property.put("img", getImages(url).substring(1));
		}
		if(description != null){
			property.put("description", description);
		}
		
	}

	private void paruVendu(Connection con) throws IOException {
		
		Document doc = null;
		doc = con.get();
		String description = doc.select("meta[property=og:description]").attr("content");
		Element image = doc.select(".im11_pic_main").last();

		System.out.println(description);
		
		if (image != null) {
			String url = image.attr("src");
			property.put("img", getImages(url).substring(1));
		}
		if(description != null){
			property.put("description", description);
		}
				
	}

	private void mister(Connection con) throws IOException {
		
		Document doc = null;
		doc = con.get();
		String description = doc.select("div > span.value").last().text();
		Element image = doc.select(".image_zoom.bigger").last();

		System.out.println(description);
		
		if (image != null) {
			String url = image.absUrl("href");
			property.put("img", getImages(url).substring(1));
		}
		if(description != null){
			property.put("description", description);
		}
				
	}

	private static String getImages(String src) throws IOException {

		String folderPath = "image";

		// Exctract the name of the image from the src attribute
		int indexname = src.lastIndexOf("/");

		if (indexname == src.length()) {
			src = src.substring(1, indexname);
		}

		indexname = src.lastIndexOf("/");
		String name = src.substring(indexname, src.length());
		if(name.contains("?")) name = name.substring(0, name.indexOf("?"));;

//		System.out.println(name);

		// Open a URL Stream
		URL url = new URL(src);
		InputStream in = url.openStream();

		OutputStream out = new BufferedOutputStream(new FileOutputStream(
				folderPath + name));

		for (int b; (b = in.read()) != -1;) {
			out.write(b);
		}
		out.close();
		in.close();

		return name;

	}

	/**
	 * return the base url from a full string supposed be a valid url
	 * 
	 * @param s
	 *            as full url
	 * @return base of url
	 */
	private String getHost(String s) {

		URL url;
		String host = null;
		try {
			url = new URL(s);
			host = url.getHost();
		} catch (MalformedURLException e) {

			e.printStackTrace();
		}
		
		return host;
	}
}
