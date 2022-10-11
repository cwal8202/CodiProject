import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.co.greenart.dbutil.DBUtil;

public class Tag_ThemeDao {
	public List<String> readTag() throws SQLException {
		String query = "SELECT codi FROM tag_theme order by number";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<String> tags = new ArrayList<>();

		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				String tag = rs.getString("codi");
				tags.add(tag);
			}

			return tags;

		} finally {
			DBUtil.closeStmt(pstmt);
			DBUtil.closeConn(conn);
		}
	}

	public int delete(String user_id) throws SQLException {

		String query2 = "delete from tag_like where user_id = ?";

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(query2);
			pstmt.setString(1, user_id);

			return pstmt.executeUpdate();
		} finally {
			DBUtil.closeStmt(pstmt);
			DBUtil.closeConn(conn);
		}

	}

	public int[] create(String user_id, List<String> tag) throws SQLException {
		String query = "insert into tag_like (user_id, tag_like_tag_theme) values (?, ?)";

		Connection conn = null;
		PreparedStatement pstmt = null;
		System.out.println(tag);
		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(query);
			for (String t : tag) {
				pstmt.setString(1, user_id);
				pstmt.setString(2, t.toString());
				pstmt.addBatch();
			}
			return pstmt.executeBatch();
		} finally {
			DBUtil.closeStmt(pstmt);
			DBUtil.closeConn(conn);
		}

	}

	public List<String> readSubTag(String id) throws SQLException {
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
}
