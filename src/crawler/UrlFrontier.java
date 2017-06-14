package crawler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;


public class UrlFrontier {

	LinkedList<String> frontEndQueue = new LinkedList<String>();
	Map<Integer,Double> table = new HashMap<Integer,Double>();
	BackEndQueue backEndQue;
	
	/**
	 * populate frontEndQueue with seeds in backEndQueue config
	 * @param backEndQueue
	 */
	public UrlFrontier(BackEndQueue backEndQueue){
		this.backEndQue = backEndQueue;

	}
	
	public void add(String s){
		
		frontEndQueue.add(s);
	}
	


	public void remove(String s){
		
		frontEndQueue.remove(s);
	}

	public void addLast(String s) {
		
		
		//search the corresponding queue and insert url in this queue

		backEndQue.chooseCorrespondingBackEndQueue(s);
		

		frontEndQueue.addLast(s);

	}

	public boolean isEmpty() {
		
		return frontEndQueue.isEmpty();
	}

	public String pollFirst() {
		
		int max = backEndQue.getSizeBackEndQueue();
		int rang = randInt(0, max);
		
		LinkedList<String> queue = backEndQue.getCorrespondingBackEndQueue(rang);
		
		String url = queue.peekFirst();
		// see if the queue is empty
		if(url == null){
//			//update table mapping
//			backEndQue.updateTable(queue);
//			// if the queue is empty remove it from the list
//			backEndQue.removeQueue(queue);
			
			// iterate over the list of queue 
			for (int i = 0; i < backEndQue.getSizeBackEndQueue(); i++) {
				LinkedList<String> locQueue = backEndQue.getCorrespondingBackEndQueue(i);
				// if the queue is not empty
				if(!locQueue.isEmpty()){
					url = locQueue.pollFirst();
					
					break;
				}
			}
		}
		// remove the element from the front end queue
		frontEndQueue.remove(url);
		
		return url;
	}

	public ArrayList<String> getSeed(){
		
		ArrayList<String> seeds = this.backEndQue.getSeeds();
		
		return seeds;
	
	}
	
	/**
	 * Returns a pseudo-random number between min and max, inclusive.
	 * The difference between min and max can be at most
	 * <code>Integer.MAX_VALUE - 1</code>.
	 *
	 * @param min Minimum value
	 * @param max Maximum value.  Must be greater than min.
	 * @return Integer between min and max, inclusive.
	 * @see java.util.Random#nextInt(int)
	 */
	public int randInt(int min, int max) {

	    // NOTE: Usually this should be a field rather than a method
	    // variable so that it is not re-seeded every call.
	    Random rand = new Random();

	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    int randomNum = rand.nextInt((max - min)) + min;

	    return randomNum;
	}
}
