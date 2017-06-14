package crawler;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * @author abdel
 *
 */
public class BackEndQueue {

	List<LinkedList<String>> backEndQueue = new ArrayList<LinkedList<String>>();
	Map<String,Integer> table = new HashMap<String, Integer>();
	
	Config config;
	
	public BackEndQueue(Config config){
		
		this.config = config;
		prepareBackEndQueue();
	}
	
	/**
	 * create a queue and add each seed in a this queue
	 */
	private void prepareBackEndQueue() {
		
		ArrayList<String> seeds = config.getSeeds();
		
		for (int i = 0; i < config.getNumberOfBackEndQueue(); i++) {
			LinkedList<String> newLinkedList = new LinkedList<String>();
			String link = seeds.get(i);
			newLinkedList.add(link);
			backEndQueue.add(newLinkedList);
			mapLinkToQueue(link,i);
		}
	}
	

	private void mapLinkToQueue(String link,Integer i) {
		
		String baseUri =  getHost(link);
		
		if(table.get(baseUri) == null){
			table.put(baseUri,i);
		}
	}
	
	public Integer getOffsetLink(String link) {
		
		String baseUri =  getHost(link);

		return table.get(baseUri);
	}
	
	public boolean chooseCorrespondingBackEndQueue(String link) {

		boolean bool = false;
		
		LinkedList<String> queue =  getCorrespondingBackEndQueue(link);
		// if queue is null and not contains the link we add it in the queue
		if (null != queue && !queue.contains(link)) {
			
				queue.addLast(link);
				bool = true;
		}
		
		return bool;
	}
	
	/**
	 * return the base url from a full string supposed be a valid url
	 * 
	 * @param s as full url
	 * @return  base of url
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
	public LinkedList<String> getCorrespondingBackEndQueue(String link){
		
		Integer offset =  getOffsetLink(link);
		LinkedList<String> queue = null;
		
		if(offset != null){
			
			queue =  backEndQueue.get(offset);
		}
		return queue;
	}
	public LinkedList<String> getCorrespondingBackEndQueue(int rang){
		
		return backEndQueue.get(rang);
	}
	
	public Integer getSizeBackEndQueue(){
		
		return backEndQueue.size();
	}

	public List<LinkedList<String>> getBackEndQueue() {

		return backEndQueue;
	}
	
	public ArrayList<String> getSeeds(){
		
		return config.getSeeds();
	}

	public boolean removeQueue(LinkedList<String> queue) {
		
		return backEndQueue.remove(queue);
		
	}

	
}
