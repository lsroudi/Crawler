package doap;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;

public class FDoap {

	private static String url = "jdbc:mysql://localhost/crawler";
	private static String utilisateur = "u_search";
	private static String motDePasse = "test";
	private static String driver = "com.mysql.jdbc.Driver";

	private Connection connexion = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	FDoap(String url, String username, String password) {
		FDoap.url = url;
		FDoap.utilisateur = username;
		FDoap.motDePasse = password;
	}

	public static FDoap getInstance() throws DoapException {

		try {
			Class.forName(FDoap.driver);
		} catch (ClassNotFoundException e) {
			throw new DoapException("Le driver est introuvable.", e);
		}

		FDoap instance = new FDoap(url, utilisateur, motDePasse);

		return instance;
	}

	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, utilisateur, motDePasse);
	}

	public ResultSet getResultSet(String sql, Map<String, Object> params)
			throws DoapException, SQLException {

		try {

			this.connexion = getConnection();
			this.preparedStatement = prepare(connexion, sql, params);
			this.resultSet = preparedStatement.executeQuery();

		} catch (SQLException e) {
			throw new DoapException(e);
		} 

		return this.resultSet;
	}
	public void close() throws SQLException{
		
		close(connexion);
		close(preparedStatement);
		close(resultSet);
		
	}

	private void close(ResultSet resultSet) throws SQLException {
		
		resultSet.close();
	}
	private void close(PreparedStatement preparedStatement) throws SQLException {
		
		preparedStatement.close();
	}
	private void close(Connection connexion) throws SQLException {
		
		connexion.close();
	}	

	public PreparedStatement prepare(Connection connexion, String sql,

	Map<String, Object> params) throws SQLException {

		PreparedStatement stmt = connexion.prepareStatement(sql);

		if (null != params) {
			Iterator<?> it = params.entrySet().iterator();

			int parameterIndex = 0;
			while (it.hasNext()) {

				parameterIndex++;
				@SuppressWarnings("rawtypes")
				Map.Entry pair = (Map.Entry) it.next();
				System.out.println(pair.getKey() +" / "+pair.getValue());
				stmt.setObject(parameterIndex, pair.getValue());

			}
		}

		return stmt;
	}
	
	

}