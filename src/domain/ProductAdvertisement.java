package domain;

import java.sql.Timestamp;

public class ProductAdvertisement {
	private int id;
	private int productId;
	private Timestamp startDate;
	private Timestamp endDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate() {
		java.util.Calendar cal = java.util.Calendar.getInstance();
		this.startDate = new Timestamp(cal.getTimeInMillis());
		;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate() {
		java.util.Calendar cal = java.util.Calendar.getInstance();
		this.endDate = new Timestamp(cal.getTimeInMillis());
		;
	}

}
