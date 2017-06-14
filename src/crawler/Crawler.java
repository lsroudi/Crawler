package crawler;
import java.util.ArrayList;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import doap.DoapException;

public class Crawler {
	
	private LinkExtractor linkext = new LinkExtractor();
	private UrlSeen	urlSeen = new UrlSeen();
	private LinkFilter	linkFilter = new LinkFilter();
	
	private UrlFrontier urlFronntier;
	
	public Crawler(UrlFrontier urlFrontier) {
		 this.urlFronntier = urlFrontier;
	}

	/* for each doc with default MIME type (text/html) 
	 * 		Extract All link
	 * 		|	Filter link
	 * 		|	|	Perform Url Seen Test
	 * 		|	|	|	if Url is in UrlFrontier or already downloaded
	 * 		|	|	|	|	cache
	 * 		|	|	|	else
	 * 		|	|	|	|	add Url in UrlFrontier
	 * 		|   |   |	|
	 */
	public void bfs() throws DoapException, InterruptedException {
		
		ArrayList<String> seeds = urlFronntier.getSeed();
		// populate queue
		System.out.println(seeds.get(0));
		for (String s : seeds) {
			// visited
			urlSeen.markeItVisited(s);
			// distance
			//distTo.put(s, 0);
			urlFronntier.addLast(s);

			String baseUri = linkext.getBaseUri(s);
			urlSeen.addParent(s, baseUri);
		}

		while (!urlFronntier.isEmpty()) {
			
			String v = urlFronntier.pollFirst();

			Elements links = linkext.getLinks(v);

			if (null!= links ) {
				for (Element w : links) {
					
					Thread.sleep(1000);
					String link = w.attr("abs:href");
					
					if (!link.isEmpty()) {
						if (linkFilter.shouldVisit(link)) {

							if (!urlSeen.isVisited(link) && !urlSeen.isSaved()) {

								//					edgeTo.put(v, link);
								//					distTo.put(link, distTo.get(v) + 1);

								// mark it as visited link					
								urlSeen.markeItVisited(link);
								// save the link in database					
								
								// add parent to the link	
								urlSeen.addParent(link, v);
								System.out
										.println("link in process ::::::::::::::: "
												+ link);
								System.out.println("baseUri ::::::::::::::: "
										+ v);

								urlFronntier.addLast(link);
								if (linkFilter.shoulDownloaded(link)) {
									System.out
											.println("downloaded ::::::::::::::: "
													+ link);
									// test if not exist in database 
									if(urlSeen.isDownloaded(link)){
										urlSeen.save(link);
									}
								}
							}
						}
					}
				}
			}
		}
	}
}
