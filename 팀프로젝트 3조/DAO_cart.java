import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.co.greenart.dbutil.DBUtil;

public class DAO_cart {
	//<db생성>
	//담기 버튼 누르면!!
	//삽입)유저 아이디, 옷 정보  받아와서 -> cart테이블에 삽입
	
	
	//<db읽기>
	//읽기)user_id 와 일치하는  db를 읽어 옴  
	
	//삭제 버튼 누르면
	//삭제) user_id와, product이름을 받아와서 두개 다 일치하는 거   -> 그 정보 삭제!
	
	//user_id,product(상의_* )를 받아옴. 
	private Cart resultMapping(ResultSet rs) throws SQLException{
		String user_id = rs.getString("user_id");
		String product = rs.getString("product");
		
		return new Cart(user_id,product);
	}
	//<db생성>
	//담기 버튼 누르면!!
	//삽입)유저 아이디, 옷 정보  받아와서 -> cart테이블에 삽입
	public int create(String user_id, String product) throws SQLException{
		String query = "insert into cart (user_id,product) values (?,?)"/*여기에  로그인 된 유저 아이디 들어가야 함 */;
		
		Connection conn = null;
		PreparedStatement pstmt =null;
		
		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(query);
			
			//배치작업
			pstmt.setString(1, user_id); 
			pstmt.setString(2, product);
			return pstmt.executeUpdate();
			
		}finally {
			DBUtil.closeStmt(pstmt);
			DBUtil.closeConn(conn);
		}
		
	}
	
	
	//읽기)user_id 와 일치하는  db를 읽어 옴  
	public List<Cart> read(String user_id) throws SQLException{
		String query ="select user_id, product from cart where user_id = ?";
		
		Connection conn = null;
		PreparedStatement pstmt =null;
		ResultSet rs = null;
		List<Cart> list = new ArrayList<>();
		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, user_id); 
			rs = pstmt.executeQuery();
			while(rs.next()) {
				list.add(resultMapping(rs));
			}
			
		}finally {
			DBUtil.closeRS(rs);
			DBUtil.closeStmt(pstmt);
			DBUtil.closeConn(conn);
		}
		return list;
	}
	//삭제 버튼 누르면
	//삭제) user_id와, product이름을 받아와서 두개 다 일치하는 거   -> 그 정보 삭제!
	public int delete(String user_id,String product) throws SQLException {
		String query = "delete from cart where user_id = ? and product = ?";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn= DBUtil.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, user_id);
			pstmt.setString(2, product);
			
			return pstmt.executeUpdate();
		}finally {
			DBUtil.closeStmt(pstmt);
			DBUtil.closeConn(conn);
		}
		
	}
	//유저 아이디를 파라미터로 받아서 
	//유저 아이디가  담은 상품을 출력함. 
	public List<String> readcart(String user_id) throws SQLException{
		String query ="select product from cart where user_id = ?";
		
		Connection conn = null;
		PreparedStatement pstmt =null;
		ResultSet rs = null;
		List<String> list = new ArrayList<>();
		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, user_id); 
			rs = pstmt.executeQuery();
			while(rs.next()) {
				list.add(rs.getString("product"));
			}
			
		}finally {
			DBUtil.closeRS(rs);
			DBUtil.closeStmt(pstmt);
			DBUtil.closeConn(conn);
		}
		return list;
	}
	
	public static void main(String[] args) {
		DAO_cart dao = new DAO_cart();
		try {
			System.out.println(dao.readcart("lossryeong"));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
}
