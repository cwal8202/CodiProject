import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import kr.co.greenart.dbutil.DBUtil;

public class UserDaoImpl implements UserDao {
	private User resultMapping(ResultSet rs) throws SQLException {
		String user_id = rs.getString("user_id");
		String user_Name = rs.getString("user_Name");
		String user_password = rs.getString("user_password");
		
		return new User(user_id, user_Name, user_password);
	}
	
	@Override
	public int create(String user_id, String user_Name, String user_Password) throws SQLException {
		String query = "insert into user (user_id, user_name, user_password) values (?, ?, ?)";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, user_id);
			pstmt.setString(2, user_Name);
			pstmt.setString(3, user_Password);
			
			return pstmt.executeUpdate();
			
		} finally {
			DBUtil.closeStmt(pstmt);
			DBUtil.closeConn(conn);
		}
	}

	@Override
	public User login_read(String user_id, String user_password) throws SQLException {
		String query = "select * from user where user_id = ? and user_password = ?";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, user_id);
			pstmt.setString(2,  user_password);
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

	@Override
	public User signUp_id_read(String user_id) throws SQLException {
		String query = "select * from user where user_id = ?";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, user_id);
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
