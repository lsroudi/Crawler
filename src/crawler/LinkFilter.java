package crawler;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.regex.Pattern;

public class LinkFilter {

	private HashMap<String, LinkedList<String>> likeLink = new HashMap<String, LinkedList<String>>();

	private static final Pattern FILTERS = Pattern
			.compile(".*(\\.(css|js|bmp|gif|jpe?g|png|tiff?|mid|mp2|mp3|mp4|wav|avi|mov|mpeg|ram|m4v|pdf"
					+ "|rm|smil|wmv|swf|wma|zip|rar|gz))$");
	private static final Pattern OUT = Pattern
			.compile(".*((m\\.topannonces|cgu|out|tracking|track|google|facebook|twitter|assur|veto|contact|"
					+ "comparatif|Publicite|publicite|question|rss|mention|mentions|toiletteur|canin)).*");
	private Pattern likedPattern ;
	
	public LinkFilter() {
		
		HashMap<String, LinkedList<String>> link = Config.buildAll();
		prepareLikedLink(link);
	}

	public boolean shouldVisit(String link) {

		String url = link.toLowerCase();

		return !FILTERS.matcher(url).matches() && !OUT.matcher(url).matches();
	}
	
	public boolean shoulDownloaded(String link) {

		String url = link.toLowerCase();

		return likedPattern.matcher(url).matches();
	}

	private void prepareLikedLink(HashMap<String, LinkedList<String>> link) {
		
		// iterate over the hashMap
		StringBuilder compileTo = new StringBuilder();
		compileTo.append("(");
		
		
		Iterator<Entry<String, LinkedList<String>>> it = link.entrySet().iterator();
		
	    while (it.hasNext()) {

			@SuppressWarnings("rawtypes")
			HashMap.Entry pair = (HashMap.Entry) it.next();

			@SuppressWarnings("unchecked")
			LinkedList<String> value = (LinkedList<String>) pair.getValue();
	        
			for (Iterator<String> iterator = value.iterator(); iterator.hasNext();) {
				String string = (String) iterator.next();
				if(it.hasNext()){
					compileTo.append(string+"|");
				}else{
					compileTo.append(string);
				}


			}
	    }
		
		compileTo.append(")");
		System.out.println(compileTo);
		likedPattern = Pattern.compile(compileTo.toString());
	}

	/**
	 * @return the likeLink
	 */
	public HashMap<String, LinkedList<String>> getLikeLink() {
		return likeLink;
	}

	/**
	 * @param likeLink the likeLinkk to set
	 */
	public void setLikeLink(HashMap<String, LinkedList<String>> likeLink) {
		this.likeLink = likeLink;
	}

}
