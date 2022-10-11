import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import kr.co.greenart.dbutil.DBUtil;


// All_product 테이블  컬럼
// product_Name, product_Size, product_Color, product_Category,
// product_Sub_Category, product_Image, product_Season 

public class AdminDaoImpl implements AdminDao {
	// ResultSet mapping method
	private Item resultMapping(ResultSet rs) throws SQLException {
		int number = rs.getInt("number");
		String name = rs.getString("product_Name");
		String size = rs.getString("product_Size");
		String color = rs.getString("product_Color");
		String category = rs.getString("product_Category");
		String subCategory = rs.getString("product_Sub_Category");
		Blob imageUrl = rs.getBlob("product_Image");
		String season = rs.getString("product_Season");

		return new Item(number, name, size, color, category, subCategory, imageUrl, season);
	}

	// --------------------------------

	@Override
	public int create(String name, String size, String color, String category, String subCategory, String imageUrl,
			File imageBlob, String season) throws SQLException {
		String query = "INSERT INTO all_product (product_Name, product_Size, product_Color, product_Category, product_Sub_Category, product_Image, product_Season) VALUES (?, ?, ?, ?, ?, ?, ?)";
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(query);
			// product_Name, product_Size, product_Color, product_Category,
			// product_Sub_Category, product_Image, product_Season
			// name, size, color, category, sub_category, image, season 값
			FileInputStream makeBlob = new FileInputStream(imageBlob);
			pstmt.setString(1, name);
			pstmt.setString(2, size);
			pstmt.setString(3, color);
			pstmt.setString(4, category);
			pstmt.setString(5, subCategory);
			pstmt.setBinaryStream(6, makeBlob, (int) imageBlob.length());
			pstmt.setString(7, season);

			return pstmt.executeUpdate();
		} catch (java.lang.NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "1~5의 숫자만 입력해주세요.");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "사진은 필수 조건입니다.");
			System.out.println("사진이 없음 저장안됨 다시 시도 하시오.");
		} finally {
			DBUtil.closeStmt(pstmt);
			DBUtil.closeConn(conn);
		}
		return 1;
	}

	@Override
	public int update(int number, String name, String size, String color, String category, String subCategory,
			String season) throws SQLException {
		String query = "UPDATE all_product SET product_Name = ?, product_Size = ?, product_Color = ?, product_Category = ?, product_Sub_Category = ?, product_Season = ? WHERE number = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		// product_Name, product_Size, product_Color, product_Category,
		// product_Sub_Category, product_Image, product_Season
		// name, size, color, category, sub_category, image, season 값
		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, name);
			pstmt.setString(2, size);
			pstmt.setString(3, color);
			pstmt.setString(4, category);
			pstmt.setString(5, subCategory);
			pstmt.setString(6, season);
			pstmt.setInt(7, number);

			return pstmt.executeUpdate();
		} finally {
			DBUtil.closeStmt(pstmt);
			DBUtil.closeConn(conn);
		}
	}

	@Override
	public int delete(int number) throws SQLException {
		String query = "DELETE FROM all_product WHERE number = ?";

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, number);

			return pstmt.executeUpdate();
		} finally {
			DBUtil.closeStmt(pstmt);
			DBUtil.closeConn(conn);
		}
	}

	@Override
	public int delete(String name) throws SQLException {
		String query = "DELETE FROM all_product WHERE product_Name = ?";

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, name);

			return pstmt.executeUpdate();
		} finally {
			DBUtil.closeStmt(pstmt);
			DBUtil.closeConn(conn);
		}
	}

	@Override
	public List<Item> read() throws SQLException {
		String query = "SELECT * FROM all_product ORDER BY number";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Item> list = new ArrayList<>();

		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				list.add(resultMapping(rs));
			}
		} finally {
			DBUtil.closeRS(rs);
			DBUtil.closeStmt(pstmt);
			DBUtil.closeConn(conn);
		}
		return list;
	}

	@Override
	public Item read(String name) throws SQLException {
		String query = "SELECT * FROM all_product WHERE product_Name = ?";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, name);
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

	// 수정 패널에서 체크박스 번호랑 매치하는 객체를 불러오는 메소드
	@Override
	public Item read(int number) throws SQLException {
		String query = "SELECT * FROM all_product WHERE number = ?";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, number);
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
	public int findIncrement() throws SQLException {
		String query = "SELECT COUNT(*) AS number FROM all_product";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				return rs.getInt("number");
			}

		} finally {
			DBUtil.closeRS(rs);
			DBUtil.closeStmt(pstmt);
			DBUtil.closeConn(conn);
		}
		return 0;
	}

	@Override
	public int alterIncrement(int number) throws SQLException {
		String query = "ALTER TABLE `teamproject`.`all_product` AUTO_INCREMENT = ?";

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, number);

			return pstmt.executeUpdate();
		} finally {
			DBUtil.closeStmt(pstmt);
			DBUtil.closeConn(conn);
		}
	}

	@Override
	public int updateImage(int number, File imageBlob) throws SQLException {
		String query = "UPDATE all_product SET product_Image = ? WHERE number = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		// product_Name, product_Size, product_Color, product_Category,
		// product_Sub_Category, product_Image, product_Season
		// name, size, color, category, sub_category, image, season 값
		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(query);
			
			FileInputStream makeBlob = new FileInputStream(imageBlob);
			pstmt.setBinaryStream(1, makeBlob, (int) imageBlob.length());
			pstmt.setInt(2, number);

			return pstmt.executeUpdate();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeStmt(pstmt);
			DBUtil.closeConn(conn);
		}
		return 1;
	}

	
	
	// 10개씩 불러오고 더 보기 버튼으로 새로 10개씩 불러오는 dao //
	@Override
	public List<Item> readLimited(int push) throws SQLException {
		int offsetNum = 10 * push;
		String query = "SELECT * FROM all_product ORDER BY number LIMIT 10 OFFSET " + offsetNum;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Item> list = new ArrayList<>();

		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				list.add(resultMapping(rs));
			}
		} finally {
			DBUtil.closeRS(rs);
			DBUtil.closeStmt(pstmt);
			DBUtil.closeConn(conn);
		}
		return list;
	}

}
