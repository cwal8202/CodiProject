import java.sql.SQLException;
import java.util.List;

public interface ManagementDao {
	List<Item> readByKind(String product_Category) throws SQLException;
	List<Item> readFromCart(String user_id) throws SQLException;
	List<Item> readFromCodi(String user_id, int id) throws SQLException;
	Item readProdcutImage(String product_Name) throws SQLException;
}
