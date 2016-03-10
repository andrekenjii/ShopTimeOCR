package app;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;

public class CaptureData {
	public TransparentFrame windowRefence;

	public void TakePicture() {
		Robot robot;
		try {
			robot = new Robot();
			BufferedImage screenShot = robot.createScreenCapture(
					new Rectangle(windowRefence.getLocationOnScreen().x, windowRefence.getLocationOnScreen().y,
							windowRefence.getSize().width, windowRefence.getSize().height));
			// getting width and height of image
			double image_width = screenShot.getWidth();
			double image_height = screenShot.getHeight();

			BufferedImage bimg = null;
			BufferedImage img = screenShot;

			try {

				// drawing a new image
				bimg = new BufferedImage((int) image_width, (int) image_height, BufferedImage.TYPE_BYTE_GRAY);
				Graphics2D gg = bimg.createGraphics();
				gg.drawImage(img, 0, 0, img.getWidth(null), img.getHeight(null), null);

				ImageIO.write(bimg, "png", new File("c:\\OCR\\myScreenShot.png"));

			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	public boolean ProcessOCR() {
		// Call the tesseract.exe OCR
		boolean validation = true;
		try {
			@SuppressWarnings("unused")
			Process process = new ProcessBuilder("C:\\OCR\\Tesseract-OCR\\tesseract.exe", "c:\\OCR\\myScreenShot.png",
					"c:\\OCR\\out").start();
			validation = this.validationData(this.readFile("c:\\OCR\\out.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return validation;
	}

	private boolean validationData(String dataOcr) {
		dataOcr = dataOcr.trim();
		if (dataOcr.length() == 9) {
			try {
				int code = Integer.parseInt(dataOcr);

				return false;
			} catch (Exception e) {
				return true;
			}
		} else {
			return true;
		}
	}

	private String readFile(String f) {
		String everything = "";

		BufferedReader br;

		try {

			br = new BufferedReader(new FileReader(f));

			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			everything = sb.toString();

			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return everything;

	}

}
