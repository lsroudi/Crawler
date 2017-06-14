package crawler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import doap.DoapException;
import doap.FDoap;
import doap.Search;
import doap.SearchImpl;

public class UrlSeen {
	private ArrayList<String> marked = new ArrayList<String>();
	private Map<String, String> baseUriTo = new HashMap<String, String>();

	public UrlSeen() {
	}

	public boolean isVisited(String link) {

		return marked.contains(link);
	}

	public void markeItVisited(String link) {
		marked.add(link);

	}

	public boolean isSaved() {
		return false;
	}

	public boolean isDownloaded(String link) throws DoapException {

		FDoap fdoap = null;
		fdoap = FDoap.getInstance();
		SearchImpl<Search> simpl = new SearchImpl<Search>(fdoap);
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("url", link);

		List<Search> search = simpl.query(
				"SELECT * FROM search AS s WHERE s.url = ?", params);

		return search.isEmpty();
	}

	public void save(String link) {
		try {
			FDoap fdoap = null;
			fdoap = FDoap.getInstance();
			SearchImpl<Search> simpl = new SearchImpl<Search>(fdoap);

			Search search = Search.getInstance();

			search.setDate(new Date());
			search.setUrl(link);

			ContentExtractor extractor = new ContentExtractor(link);

			HashMap<String,String> content = extractor.extract();
			String img = content.get("img");
			String text = content.get("description");
			search.setImg(img);
			search.setText(text);

			simpl.update(search);

		} catch (DoapException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String getBaseUri(String link) {

		return baseUriTo.get(link);
	}

	public void addParent(String link, String parent) {
		baseUriTo.put(link, parent);

	}

}
