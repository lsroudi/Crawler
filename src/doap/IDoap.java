package doap;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface IDoap<T> 

{	
	   public List<T> findAll() throws DoapException,SQLException;
	   public T findOne(int id) throws DoapException,SQLException;
	   public void update(T o) throws DoapException, SQLException;
	   public void delete(T o);
	   public List<T> query(String value, Map<String, Object> params);
}
