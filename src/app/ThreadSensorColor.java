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

					double[] lab1 = RGBtoLAB(color.getRed(), color.getGreen(), color.getBlue());
					double[] lab2 = RGBtoLAB(180, 195, 236);
					double delta_e = calculaDE(lab1, lab2);
					System.out.println(delta_e);

					// teste de establização da tarja do anúncio
					int test = 0;
					for (int i = 0; i < 20; i++) {
						color = robot.getPixelColor(windowRefence.getLocationOnScreen().x,
								windowRefence.getLocationOnScreen().y);
						lab1 = RGBtoLAB(color.getRed(), color.getGreen(), color.getBlue());
						delta_e = calculaDE(lab1, lab2);
						if (delta_e < 20) {
							test++;
						}

						Thread.sleep(100);// quando rodar 10x no for, este sleep
											// sera equivalente a 3 segundos
					}

					if (test >= 10) {
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

	private double[] RGBtoLAB(int R, int G, int B) {
		double[] result = new double[3];
		double[][] M = { { 0.4124, 0.3576, 0.1805 }, { 0.2126, 0.7152, 0.0722 }, { 0.0193, 0.1192, 0.9505 } };
		double[] whitePoint = { 95.0429, 100.0, 108.8900 };

		// convert 0..255 into 0..1
		double r = R / 255.0;
		double g = G / 255.0;
		double b = B / 255.0;
		// assume sRGB
		if (r <= 0.04045) {
			r = r / 12.92;
		} else {
			r = Math.pow(((r + 0.055) / 1.055), 2.4);
		}
		if (g <= 0.04045) {
			g = g / 12.92;
		} else {
			g = Math.pow(((g + 0.055) / 1.055), 2.4);
		}
		if (b <= 0.04045) {
			b = b / 12.92;
		} else {
			b = Math.pow(((b + 0.055) / 1.055), 2.4);
		}
		r *= 100.0;
		g *= 100.0;
		b *= 100.0;
		// [X Y Z] = [r g b][M]
		double x = (r * M[0][0]) + (g * M[0][1]) + (b * M[0][2]) / whitePoint[0];
		double y = (r * M[1][0]) + (g * M[1][1]) + (b * M[1][2]) / whitePoint[1];
		double z = (r * M[2][0]) + (g * M[2][1]) + (b * M[2][2]) / whitePoint[2];

		if (x > 0.008856) {
			x = Math.pow(x, 1.0 / 3.0);
		} else {
			x = (7.787 * x) + (16.0 / 116.0);
		}
		if (y > 0.008856) {
			y = Math.pow(y, 1.0 / 3.0);
		} else {
			y = (7.787 * y) + (16.0 / 116.0);
		}
		if (z > 0.008856) {
			z = Math.pow(z, 1.0 / 3.0);
		} else {
			z = (7.787 * z) + (16.0 / 116.0);
		}
		result[0] = (116.0 * y) - 16.0;
		result[1] = 500.0 * (x - y);
		result[2] = 200.0 * (y - z);
		return result;
	}

	private double calculaDE(double[] lab1, double[] lab2) {

		double delta = Math.sqrt(
				Math.pow((lab1[0] - lab2[0]), 2) + Math.pow((lab1[1] - lab2[1]), 2) + Math.pow((lab1[2] - lab2[2]), 2));
		return delta;

	}

}
