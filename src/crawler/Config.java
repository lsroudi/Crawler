package crawler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;


public class Config {
	private static HashMap<String, LinkedList<String>> likeLink = new HashMap<String, LinkedList<String>>();
	private static ArrayList<String> seeds = new ArrayList<String>();
	public static final int NUMBER_THREAD = 5;
	public static final int NUMBER_BACKED_QUEUE = 5;
	
	public Config(){

		addSeed("http://www.paruvendu.fr/animaux/");
		addSeed("http://www.mister-chien.com/annonce/");
		addSeed("http://www.chien.com/");		
		addSeed("http://www.uncompagnon.fr/");
		addSeed("http://www.topannonces.fr/annonces-chien-u9.html");
	}
	public void addSeed(String s){
		seeds.add(s);
	}
	
	public ArrayList<String> getSeeds(){
		return seeds;
	}
	
	public Integer getNumberOfBackEndQueue(){
		
		return seeds.size();
	}
	
	

	public static HashMap<String, LinkedList<String>> buildAll(){
		
		buildParuVendu();
		buildAnidonne();
		buildMister();
		buildTopannonces();
		buildUncompagnon();
		
		return likeLink;
	}
	private static void buildParuVendu() {
		
		LinkedList<String> list = new LinkedList<String>();
		list.add("^http://www.paruvendu.fr/p/chien-chiot/.*");
		likeLink.put("http://www.paruvendu.fr/animaux/", list);
	}
	
	private static void buildMister() {
		
		LinkedList<String> list = new LinkedList<String>();
		list.add("^http://www.mister-chien.com/annonce/.*\\.html$");
		likeLink.put("http://www.mister-chien.com/annonce/", list);
	}	

	private static void buildAnidonne() {
		
		LinkedList<String> list = new LinkedList<String>();
		list.add("^http://www.anidonne.com/annonce-animaux/.*\\.html$");
		likeLink.put("http://www.anidonne.com/", list);
	}
	private static void buildUncompagnon() {
		
		LinkedList<String> list = new LinkedList<String>();
		list.add("^http://www.uncompagnon.fr/detail/.*\\.html$");
		likeLink.put("http://www.uncompagnon.fr/", list);
	}	
	private static void buildTopannonces() {
		
		LinkedList<String> list = new LinkedList<String>();
		list.add("^http://www.topannonces.fr/annonce-chien-v.*\\.html$");
		likeLink.put("http://www.topannonces.fr/annonces-chien-u9.html", list);
	}	
}
