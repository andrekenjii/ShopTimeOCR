package app;

import java.awt.Color;
import java.awt.Robot;

public class ThreadSensorColor extends Thread {
	public TransparentFrame windowRefence;
	private AppController appController;
	private boolean control;

	public ThreadSensorColor(AppController app) {
		this.appController = app;
		this.control = true;
	}

	public void run() {
		try {
			Robot robot = new Robot();
			if (this.windowRefence.isShowing()) {
				while (true) {
					Color color = robot.getPixelColor(windowRefence.getLocationOnScreen().x,
							windowRefence.getLocationOnScreen().y);
					System.out.println(color.getRed() + " " + color.getGreen() + " " + color.getBlue());

					int test = 0;
					for (int i = 0; i < 10; i++) {
						if (color.getRed() <= 195 && color.getRed() >= 170 && color.getGreen() <= 205
								&& color.getGreen() >= 185 && color.getBlue() <= 255 && color.getBlue() >= 220) {
							test++;
						}
						Thread.sleep(300);

					}

					if (test == 10) {
						if (this.control) {
							this.control = this.appController.TakePicture();
							System.out.println("entrou");
						}
					} else {
						this.control = true;
						System.out.println("saiu");
					}

					robot.delay(300);
				}
			}
		} catch (

		Exception e)

		{
			e.printStackTrace();
		}

	}

}
