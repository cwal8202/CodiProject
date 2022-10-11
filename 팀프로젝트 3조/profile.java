import java.sql.SQLException;

public class profile {
	public static void main(String[] args) {
		myProfile m;
		try {
			m = new myProfile("test1");
			m.setVisible(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}