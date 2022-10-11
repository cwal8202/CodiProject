import java.net.MalformedURLException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.co.greenart.dbutil.DBUtil;

public class jinhoDao {
	public List<String> readTag(String id) throws SQLException {
		String query = "select tag_like_tag_theme from tag_like where user_id = ?";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<String> subTagList = new ArrayList<>();
		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String subTag = rs.getString("tag_like_tag_theme");
				subTagList.add(subTag);
			}
			return subTagList;
		} finally {
			DBUtil.closeRS(rs);
			DBUtil.closeStmt(pstmt);
			DBUtil.closeConn(conn);
		}
	}

	public List<Blob> read(String id) throws SQLException {
		String query = "select a.codiset_image from codiset_new as a where user_id = ?";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Blob> codiList = new ArrayList<>();
		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Blob Image = rs.getBlob("codiset_image");
				codiList.add(Image);
			}
			return codiList;
		} finally {
			DBUtil.closeRS(rs);
			DBUtil.closeStmt(pstmt);
			DBUtil.closeConn(conn);
		}
	}

	public List<Blob> cart(String id) throws SQLException, MalformedURLException {

		String query = "SELECT product_Image FROM all_Product as a Join cart as b on a.product_name = b.product where user_id = ? order by record_Data desc, number";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Blob> cartList = new ArrayList<>();

		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Blob Image = rs.getBlob("product_Image");
				cartList.add(Image);
			}
			return cartList;
		} finally {
			DBUtil.closeRS(rs);
			DBUtil.closeStmt(pstmt);
			DBUtil.closeConn(conn);
		}
	}
	public List<String> cartstring(String id) throws SQLException, MalformedURLException {
		
		String query = "SELECT a.product_Name FROM all_Product as a Join cart as b on a.product_name = b.product where user_id = ? order by record_Data desc, number";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<String> cartList = new ArrayList<>();
		
		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String name = rs.getString("product_Name");
				cartList.add(name);
			}
			return cartList;
		} finally {
			DBUtil.closeRS(rs);
			DBUtil.closeStmt(pstmt);
			DBUtil.closeConn(conn);
		}
	}

	public List<Blob> like(String id) throws SQLException {
		String query = "select a.codiset_image from codiset_new as a left join heart as b on a.id = b.codiSet_id where b.user_id = ? order by  record_time desc, codiSet_id";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Blob> heartList = new ArrayList<>();
		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Blob Image = rs.getBlob("codiset_image");
				heartList.add(Image);
			}
			return heartList;
		} finally {
			DBUtil.closeRS(rs);
			DBUtil.closeStmt(pstmt);
			DBUtil.closeConn(conn);
		}
	}
	public List<Integer> likeint(String id) throws SQLException {
		String query = "select a.id from codiset_new as a left join heart as b on a.id = b.codiSet_id where b.user_id = ? order by record_time desc, codiSet_id";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Integer> heartList = new ArrayList<>();
		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				int num = rs.getInt("id");
				heartList.add(num);
			}
			return heartList;
		} finally {
			DBUtil.closeRS(rs);
			DBUtil.closeStmt(pstmt);
			DBUtil.closeConn(conn);
		}
	}
}
