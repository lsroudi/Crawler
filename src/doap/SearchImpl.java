package doap;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.mysql.jdbc.PreparedStatement;

public class SearchImpl<K> implements IDoap<Search> {
	
	private static final String SQL_SELECT_PAR_ID = "SELECT * FROM search AS o WHERE o.id = ?";
	private static final String SQL_SELECT_ALL = "SELECT * FROM search";
	private static final String SQL_INSERT = "INSERT INTO search (url, text, img, date, parent)" + 
											" VALUES (?, ? ,? ,? ,?)";
	private static final String SQL_UPDATE = "UPDATE  operations SET  "
												+ "url =  ?,"
												+ "text = ?,"
												+ "img = ?,"
												+ "date = ?,"												
												+ "parent =  ?,"  
												+ " WHERE  id = ? ";
	
	private FDoap fdoap;
	List<Search> operations = new ArrayList<Search>();
	
	public SearchImpl(FDoap fdoap) {
		
		this.fdoap = fdoap;
	}

	
	public List<Search> findAll() throws SQLException, DoapException {
		
		List<Search> operations =  getOperationFromDatabase(SQL_SELECT_ALL,null);

		return operations;
	}


	public Search findOne(int rollNo) throws DoapException, SQLException {

		Map<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("id", rollNo);
		List<Search> operations =  getOperationFromDatabase(SQL_SELECT_PAR_ID,params);
		
		return operations.get(0);
	}

	private List<Search> getOperationFromDatabase(String sql, Map<String, Object> params) throws SQLException, DoapException
	{
		

		ResultSet resultSet;
		Search operation;
		resultSet = fdoap.getResultSet(sql, params);
		
			try {

				while (resultSet.next()) {
					operation = map(resultSet);

					this.operations.add(operation);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				fdoap.close();
			}
		
		
			
		return operations;
	}

	private Search map(ResultSet resultSet) throws SQLException {
		
		Search search = Search.getInstance();
		
		search.setId(resultSet.getInt("id"));
		search.setUrl(resultSet.getString("url"));
		search.setText(resultSet.getString("text") );
		search.setImg(resultSet.getString("img") );
		search.setDate(resultSet.getDate("date") );
		search.setParentId(resultSet.getInt("parent") );		
	    
	    return search;  
	}


	public void update(Search search) throws DoapException, SQLException {
		
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("url", search.getUrl());		
		params.put("text", search.getText());	
		params.put("img", search.getImg());
		params.put("date", search.getDate());
		if( search.getParentId() != null) {params.put("parent", null);}
		else params.put("parent", search.getParentId());
		
		
		
		Connection connexion = fdoap.getConnection();
		
		PreparedStatement stm = null;
		
		if(search.getId() != null){
			
			// update 
			params.put("id", search.getId());
			stm = (PreparedStatement) fdoap.prepare(connexion, SQL_UPDATE, params);	
			
		}else{
			
			// insert
			stm = (PreparedStatement) fdoap.prepare(connexion, SQL_INSERT, params);			
		}

		int nbRow = stm.executeUpdate();
		
		System.out.println("number of row updated :"+nbRow);
	}


	public void delete(Search o) {
		

	}


	public List<Search> query(String sql, Map<String, Object> params) {
		
		List<Search> searchs = null;
		try {
			searchs =  getOperationFromDatabase(sql,params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DoapException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		return searchs;
	}

}
