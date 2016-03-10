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

	public int ProcessOCR() {
		// Call the tesseract.exe OCR
		int code = 0;
		try {
			@SuppressWarnings("unused")
			Process process = new ProcessBuilder("C:\\OCR\\Tesseract-OCR\\tesseract.exe", "c:\\OCR\\myScreenShot.png",
					"c:\\OCR\\out").start();
			code = this.validationData(this.readFile("c:\\OCR\\out.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return code;
	}

	private int validationData(String dataOcr) {
		dataOcr = dataOcr.trim();
		int code = 0;
		if (dataOcr.length() == 9) {
			try {
				code = Integer.parseInt(dataOcr);

				return code;
			} catch (Exception e) {
				return code;
			}
		} else {
			return code;
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
