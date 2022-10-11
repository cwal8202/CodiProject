import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.co.greenart.dbutil.DBUtil;

// All_product 테이블  컬럼
// product_Name, product_Size, product_Color, product_Category,
// product_Sub_Category, product_Image, product_Season 

// PK가 int인 table = heart, cart, codiSet

public class ManagementDaoImpl implements ManagementDao {
	// ResultSet mapping method
	private Item resultMapping_S(ResultSet rs) throws SQLException {
		int id = rs.getInt("number");
		String name = rs.getString("product_Name");
		String category = rs.getString("product_Category");
		String subCategory = rs.getString("product_Sub_Category");
		Blob imageUrl = rs.getBlob("product_Image");

		return new Item(id, name, category, subCategory, imageUrl);
	}

	// --------------------------------

	@Override
	public List<Item> readByKind(String product_Category) throws SQLException {
		String query = "SELECT number, product_Name, product_Image, product_Category, product_Sub_Category  FROM all_product WHERE product_Category = ?";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Item> topList = new ArrayList<>();
		List<Item> bottomList = new ArrayList<>();
		List<Item> bagList = new ArrayList<>();
		List<Item> shoesList = new ArrayList<>();
		List<Item> accList = new ArrayList<>();


		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, product_Category);
			rs = pstmt.executeQuery();
			
			if (product_Category.equals("top")) {
				while (rs.next()) {
					topList.add(resultMapping_S(rs));
				}
				return topList;
			} else if (product_Category.equals("bottom")) {
				while (rs.next()) {
					bottomList.add(resultMapping_S(rs));
				}
				return bottomList;
			} else if (product_Category.equals("bag")) {
				while (rs.next()) {
					bagList.add(resultMapping_S(rs));
				} 
				return bagList;
			} else if (product_Category.equals("shoes")) {
				while (rs.next()) {
					shoesList.add(resultMapping_S(rs));
				}
				return shoesList;
			} else if (product_Category.equals("acc")) {
				while (rs.next()) {
					accList.add(resultMapping_S(rs));
				}
				return accList;
			}
		} finally {
			DBUtil.closeRS(rs);
			DBUtil.closeStmt(pstmt);
			DBUtil.closeConn(conn);
		}
		return null;
	}

	@Override
	public List<Item> readFromCart(String user_id) throws SQLException {
		String query = "SELECT user_id, number, product_Name, product_Image, product_Category, product_Sub_Category FROM all_product AS A LEFT JOIN cart AS B ON A.product_Name = B.product WHERE user_id = ?";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Item> cartList = new ArrayList<>();
		
		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, user_id);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				cartList.add(resultMapping_S(rs));
			}
		} finally {
			DBUtil.closeRS(rs);
			DBUtil.closeStmt(pstmt);
			DBUtil.closeConn(conn);
		}
		
		return cartList;
	}


	@Override
	public List<Item> readFromCodi(String user_id, int id) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Item> codiProduct = new ArrayList<>();
		
		try {
			conn = DBUtil.getConnection();
			for(int i = 1; i <= 7;  i++) {
				String query = "SELECT number, product_Name, product_Image, product_Category, product_Sub_Category FROM all_product AS A LEFT JOIN codiset_new AS B ON A.product_Name = B.product" + i + " WHERE user_id = ? AND id = ?";
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, user_id);
				pstmt.setInt(2, id);
				rs = pstmt.executeQuery();
					while (rs.next()) {
							codiProduct.add(resultMapping_S(rs));
					}
				}
			return codiProduct;
		} finally {
			DBUtil.closeRS(rs);
			DBUtil.closeStmt(pstmt);
			DBUtil.closeConn(conn);
		}
		
		
	}
	
	public Item readProdcutImage(String product_Name) throws SQLException {
		String query = "SELECT number, product_Name, product_Image, product_Category, product_Sub_Category FROM all_product WHERE product_name = ?";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, product_Name);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				return resultMapping_S(rs);
			}
		} finally {
			DBUtil.closeRS(rs);
			DBUtil.closeStmt(pstmt);
			DBUtil.closeConn(conn);
		}
		return null;
	}
		
}
