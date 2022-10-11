import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;


public interface CodiSetDao {
	int createCodiSet(List<String> list, Blob blob) throws SQLException, IOException;
	CodiSet readCodi(String user_id, int id) throws SQLException;
}
