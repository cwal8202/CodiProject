import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


import kr.co.greenart.dbutil.DBUtil;

public class CodiSetDaoImpl implements CodiSetDao {
	private CodiSet resultMapping(ResultSet rs) throws SQLException {
		String product1 = rs.getString("product1");
		String product2 = rs.getString("product2");
		String product3 = rs.getString("product3");
		String product4 = rs.getString("product4");
		String product5 = rs.getString("product5");
		String product6 = rs.getString("product6");
		String product7 = rs.getString("product7");
		Blob imageUrl = rs.getBlob("codiset_Image");
		String user_id = rs.getString("user_id");
		String tag = rs.getString("tag_theme");

		return new CodiSet(product1, product2, product3, product4, product5, product6, product7, imageUrl, user_id, tag);
	}
	
	@Override
	public int createCodiSet(List<String> list, Blob blob) throws SQLException, IOException {
		String a = "";
		String b = "";
		for (int i = 0; i < list.size() - 2; i++) {
			a += "product" + (i + 1) + ", ";
			b += "?, ";
		}
		System.out.println("프로덕트 갯수" + a);
		System.out.println(b);
		String query = "INSERT INTO codiset_new (" + a + "codiset_image, user_id, tag_theme) VALUES (" + b + "?, ?, ?)";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(query);
			
			System.out.println("리스트사이즈내놔!" + list.size());
			for (int i = 0; i < list.size() - 2; i++) {
				pstmt.setString(i+1, list.get(i).toString());
			}
			
//			Blob blob = conn.createBlob();
//			int bytes = blob.setBytes(1, String.valueOf(list.get(list.size() - 3)).getBytes());
			
//			byte[] fileArray = convertObjectToBytes(list.get(list.size() - 3));
//			Blob blob = new SerialBlob(fileArray);
//			list {1, 2, 3, 4}
//			set  {1, 2, 3, 4}
//			index[0, 1, 2, 3]
			pstmt.setBlob(list.size() - 1, blob);
			pstmt.setString(list.size(), list.get(list.size() - 2).toString());
			pstmt.setString(list.size() + 1, list.get(list.size() - 1).toString());
			
			return pstmt.executeUpdate();
			
		} finally {
			DBUtil.closeStmt(pstmt);
			DBUtil.closeConn(conn);
		}
		
	}

	@Override
	public CodiSet readCodi(String user_id, int id) throws SQLException {
		String query = "SELECT * FROM codiset_new WHERE user_id = ? AND id = ?";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, user_id);
			pstmt.setInt(2, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
					return resultMapping(rs);
			} 
		} finally {
			DBUtil.closeRS(rs);
			DBUtil.closeStmt(pstmt);
			DBUtil.closeConn(conn);
		}
		
		return null;
	}

	
	
	
}
