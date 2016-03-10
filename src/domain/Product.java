package domain;

import java.util.ArrayList;

public class Product {

	private int id;
	private int cod;
	private ArrayList<ProductAdvertisement> lstProductAdvertisement;

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

	public ArrayList<ProductAdvertisement> getLstProductAdvertisement() {
		return lstProductAdvertisement;
	}

	public void setLstProductAdvertisement(ArrayList<ProductAdvertisement> lstProductAdvertisement) {
		this.lstProductAdvertisement = lstProductAdvertisement;
	}

}
