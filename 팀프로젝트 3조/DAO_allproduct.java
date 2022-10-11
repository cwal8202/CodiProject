import java.net.MalformedURLException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.co.greenart.dbutil.DBUtil;

public class DAO_allproduct {
	
	//<db생성>
	//담기 버튼 누르면!!
	//삽입)유저 아이디,  -> cart테이블에 삽입
	
	
	//옷 정보  받아와서 
	
	//사진 3개  blob으로 불러오기. 
	public List<Blob> imgnew3() throws SQLException, MalformedURLException {
	      String query = "select product_Image from all_product order by record_Data desc, number desc limit 3";
	     
	      Connection conn = null;
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      List<Blob> imgnew3 = new ArrayList<>();

	      try {
	         conn = DBUtil.getConnection();
	         pstmt = conn.prepareStatement(query);
	         rs = pstmt.executeQuery();
	         
	         while (rs.next()) {
	            Blob Image = rs.getBlob("product_Image");
	            imgnew3.add(Image);
	         }
	         return imgnew3;
	      } finally {
	         DBUtil.closeRS(rs);
	         DBUtil.closeStmt(pstmt);
	         DBUtil.closeConn(conn);
	      }
	}
	
	
	//신상 3개 리스트로 이름 불러오기 
	public List<String> listnew3() throws SQLException{
	      String query = "select product_Name from all_product order by record_Data desc, number desc limit 3";
	     
	      Connection conn = null;
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      List<String> listnew = new ArrayList<>();

	      try {
	         conn = DBUtil.getConnection();
	         pstmt = conn.prepareStatement(query);
	         rs = pstmt.executeQuery();
	         while (rs.next()) {
	        	 String what=rs.getString("product_Name");
	        	 listnew.add(what);
	         }
	         return listnew;
	      } finally {
	         DBUtil.closeRS(rs);
	         DBUtil.closeStmt(pstmt);
	         DBUtil.closeConn(conn);
	      }
	      
	}
	
	
	public static void main(String[] args) throws SQLException {
		DAO_allproduct dao = new DAO_allproduct();
		System.out.println(dao.listnew3());
	}
	
	
}
