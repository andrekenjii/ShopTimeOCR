package dao;

import java.sql.*;
import domain.*;

public class ProductDAO {
	private Connection conn = null;

	public ProductDAO() {
		this.conn = new DB().OpenConnection();
	}

	public void InsertProduct(Product product) {
		if (this.conn != null) {
			try {
				int productId = this.SelectProdcutId(product.getCod());
				if (productId != 0) {
					product.getProductAdvertisement().setProductId(productId);
					this.InsertProductAdvertisement(product.getProductAdvertisement());
				} else {
					PreparedStatement p = conn.prepareStatement("insert into Product values (?)");
					p.setInt(1, product.getCod());
					p.executeUpdate();
					p.close();

					productId = this.SelectProdcutId(product.getCod());

					product.getProductAdvertisement().setProductId(productId);
					this.InsertProductAdvertisement(product.getProductAdvertisement());
				}

			} catch (SQLException e) {
				e.printStackTrace();

			}
		}
	}

	private void InsertProductAdvertisement(ProductAdvertisement productAdvertisement) {
		if (this.conn != null) {
			try {
				PreparedStatement p = conn.prepareStatement("insert into ProductAdvertisement values (?, ?, ?)");
				p.setInt(1, productAdvertisement.getProductId());
				p.setTimestamp(2, productAdvertisement.getStartDate());
				p.setTimestamp(3, productAdvertisement.getEndDate());
				p.executeUpdate();
				p.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				new DB().ClosedConnection(conn);
			}
		}
	}

	private int SelectProdcutId(int code) {
		int productId = 0;
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from Product where code = " + code);

			while (rs.next()) {
				productId = rs.getInt("id");
			}
			return productId;
		} catch (SQLException e) {
			e.printStackTrace();
			return productId;
		}
	}
}
