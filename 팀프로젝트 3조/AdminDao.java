import java.io.File;
import java.sql.SQLException;
import java.util.List;

public interface AdminDao {
	int create(String name, String size, String color, String category, String subCategory, String imageUrl,
			File imageBlob, String season) throws SQLException;

	int update(int number, String name, String size, String color, String category, String subCategory, String season) throws SQLException;
	int updateImage(int number, File imageBlob) throws SQLException;

	int delete(int number) throws SQLException;

	int delete(String name) throws SQLException;

	List<Item> read() throws SQLException;

	Item read(String name) throws SQLException;

	Item read(int number) throws SQLException;
	int findIncrement() throws SQLException;
	int alterIncrement(int number) throws SQLException;
	List<Item> readLimited(int push) throws SQLException;
	
}
