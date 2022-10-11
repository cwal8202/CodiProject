import java.net.MalformedURLException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.co.greenart.dbutil.DBUtil;

public class DAO_heart {
	
	//1. (행 생성-create ) 유저 아이디, 코디셋 아이디를 받아서 → 좋아요 테이블에 전부 넣는다.(버튼을 눌렀을 때 insert 되야함 )
	//2. (행 삭제) 좋아요 버튼 한번 더 누르면 행 삭제하기. 
	//3. (데이터 찾는 거 - 진호오빠 필요한 기능) 유저 아이디를 찾아서 → 그거에 맞는 좋아요 id 를 받아서 그걸 띄운다.
	
	
	
	//유저 아이디를 받아서, 좋아요 테이블에 넣기 
	private Heart resultMapping(ResultSet rs) throws SQLException{
		int id =rs.getInt("id");
		String user_id = rs.getString("user_id");
		int CodiSet_id = rs.getInt("CodiSet_id");
		
		return new Heart(id, user_id,CodiSet_id);
	}
	
	
	//현재 로그인 된 유저아이디, 코디셋을 받아서 좋아요 테이블에  insert 
	
	//where절로 현재 로그인 된 유저아이디를 	받아와야함?
	//코디셋을 받아야함? 
	public int create(String user_id, int CodiSet_id) throws SQLException{
		String query = "insert into heart (user_id,CodiSet_id) values (?,?)"/*여기에  로그인 된 유저 아이디 들어가야 함 */;
		
		Connection conn = null;
		PreparedStatement pstmt =null;
		
		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(query);
			
			//배치작업
			pstmt.setString(1, user_id); 
			pstmt.setInt(2, CodiSet_id);
			return pstmt.executeUpdate();
			
		}finally {
			DBUtil.closeStmt(pstmt);
			DBUtil.closeConn(conn);
		}
	}
	//유저 아이디를 파라미터로 받아서 
	// 그 유저 아이디가 저장한 좋아요 목록 조회하기 (시간 빼고 전부)
	//유저 아이디가  좋아요한 코디셋의 아이디를 출력함  -> 
	// 이 코디셋이면!! 
	public List<Heart> read(String user_id) throws SQLException{
		String query ="select id,user_id,CodiSet_id from heart where user_id = ?";
		
		Connection conn = null;
		PreparedStatement pstmt =null;
		ResultSet rs = null;
		List<Heart> list = new ArrayList<>();
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
	

	//좋아요 테이블의 아이디를 받아서, 그 행을 삭제한다.  
	public int delete(String user_id,int codiSet_id) throws SQLException {
//		String query = "delete from heart where id = ?";
		String query2 = "delete from heart where user_id = ? and codiSet_id = ?";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn= DBUtil.getConnection();
			pstmt = conn.prepareStatement(query2);
			pstmt.setString(1, user_id);
			pstmt.setInt(2, codiSet_id);
			
			return pstmt.executeUpdate();
		}finally {
			DBUtil.closeStmt(pstmt);
			DBUtil.closeConn(conn);
		}
		
	}
	
	public List<Integer> intliketop() throws SQLException{
		List<Integer> liketop = new ArrayList<Integer>();
		String query = "select codiSet_id from (select *, count(*) as '숫자' from heart group by codiSet_id) as a " + 
				"order by a.숫자 desc , a.id desc limit 3";
		Connection conn = null;
		PreparedStatement pstmt =null;
		ResultSet rs = null;
		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int CodiSet_id = rs.getInt("CodiSet_id");
				liketop.add(CodiSet_id);
			}
//			System.out.println("좋아요 top3 코디셋아이디:"+liketop);
			
			
		}finally {
			DBUtil.closeStmt(pstmt);
			DBUtil.closeConn(conn);
		}
		return liketop;
	}
	
	//좋아요 top3 사진 blob으로 불러오기. 
	public List<Blob> imgliketop3() throws SQLException, MalformedURLException {
	      String query = "select codiset_image from (select codiSet_id from (select *,count(*) as '숫자' from heart group by codiSet_id) as a order by a.숫자 desc , a.id desc limit 3)"
	    		  +" as B Join codiset_new as C where B.codiSet_id = C.id;";
	     
	      Connection conn = null;
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      List<Blob> cartList = new ArrayList<>();

	      try {
	         conn = DBUtil.getConnection();
	         pstmt = conn.prepareStatement(query);
	         rs = pstmt.executeQuery();
	         
	         while (rs.next()) {
	            Blob Image = rs.getBlob("codiset_image");
	            cartList.add(Image);
	         }
	         return cartList;
	      } finally {
	         DBUtil.closeRS(rs);
	         DBUtil.closeStmt(pstmt);
	         DBUtil.closeConn(conn);
	      }
	}
	
	//유저 아이디를 파라미터로 받아서 
	// 그 유저 아이디가 저장한 좋아요 목록 조회하기
	//유저 아이디가  좋아요한 코디셋의 아이디를 출력함  -> 
	// 이 코디셋이면!! 
	public List<Integer> likedCodisetId(String user_id) throws SQLException{
		String query ="select CodiSet_id from heart where user_id = ?";
		
		Connection conn = null;
		PreparedStatement pstmt =null;
		ResultSet rs = null;
		List<Integer> list = new ArrayList<>();
		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, user_id); 
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int what = rs.getInt("CodiSet_id");
				list.add(what);
			}
			
		}finally {
			DBUtil.closeRS(rs);
			DBUtil.closeStmt(pstmt);
			DBUtil.closeConn(conn);
		}
		return list;
	}
	
	public static void main(String[] args) {
		DAO_heart dao = new DAO_heart();
		try {
			System.out.println("좋아요 top3 코디셋 아이디: "+ dao.intliketop());
			System.out.println("내가 좋아요 한 코디셋:"+dao.likedCodisetId("lossryeong"));
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	
	
	
	
}
