package app;

import java.awt.Color;
import java.awt.Robot;
import java.sql.Date;

import domain.Product;
import domain.ProductAdvertisement;

public class ThreadSensorColor extends Thread {
	public TransparentFrame windowRefence;
	private AppController appController;
	private Product product;
	private ProductAdvertisement productAdvertisement;

	public ThreadSensorColor(AppController app) {
		this.appController = app;
	}

	public void run() {
		boolean control = true;

		try {
			Robot robot = new Robot();
			if (this.windowRefence.isShowing()) {
				while (true) {
					Color color = robot.getPixelColor(windowRefence.getLocationOnScreen().x,
							windowRefence.getLocationOnScreen().y);
					System.out.println(color.getRed() + " " + color.getGreen() + " " + color.getBlue());

					// teste de establização da tarja do anúncio
					int test = 0;
					for (int i = 0; i < 10; i++) {
						if (color.getRed() <= 195 && color.getRed() >= 170 && color.getGreen() <= 205
								&& color.getGreen() >= 185 && color.getBlue() <= 255 && color.getBlue() >= 220) {
							test++;
						}
						Thread.sleep(300);// quando rodar 10x no for, este sleep
											// sera equivalente a 3 segundos

					}

					if (test == 10) {
						if (control) {
							System.out.println("entrou");
							int code = this.appController.TakePicture();
							if (code != 0) {
								control = false;
								product = new Product();
								product.setCod(code);
								productAdvertisement = new ProductAdvertisement();
								java.util.Calendar cal = java.util.Calendar.getInstance();
								java.util.Date utilDate = cal.getTime();
								productAdvertisement.setStartDate(new Date(utilDate.getTime()));
								product.setProductAdvertisement(productAdvertisement);
							} else {
								control = true;
							}
						}
					} else {
						control = true;
						if (product != null) {
							java.util.Calendar cal = java.util.Calendar.getInstance();
							java.util.Date utilDate = cal.getTime();
							productAdvertisement.setEndDate(new Date(utilDate.getTime()));
							product.setProductAdvertisement(productAdvertisement);

							if (product.getProductAdvertisement().getStartDate() != null
									&& product.getProductAdvertisement().getEndDate() != null) {
								this.appController.InsertProduct(product);
								product = null;
								System.out.println("persistiu");
							}
						}

						System.out.println("saiu");

					}

					robot.delay(300);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
