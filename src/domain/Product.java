package domain;

public class Product {

	private int id;
	private int cod;
	private ProductAdvertisement productAdvertisement;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCod() {
		return cod;
	}

	public void setCod(int cod) {
		this.cod = cod;
	}

	public ProductAdvertisement getProductAdvertisement() {
		return productAdvertisement;
	}

	public void setProductAdvertisement(ProductAdvertisement productAdvertisement) {
		this.productAdvertisement = productAdvertisement;
	}
}
