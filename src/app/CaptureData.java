package app;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract1;
import net.sourceforge.tess4j.TesseractException;

public class CaptureData {
	public TransparentFrame windowRefence;
	private static String pathFile = "c:\\OCR\\";
	private static String nameFile = "anuncio.png";

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

				// ImageIO.write(bimg, "png", new File(imageFile));
				ImageIO.write(bimg, "png", new File(pathFile + nameFile));

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
			ITesseract instance = new Tesseract1();
			// code = this.validationData(instance.doOCR(new File(imageFile)));
			return code = this.validationData(instance.doOCR(new File(pathFile + nameFile)));
		} catch (TesseractException ex) {
			ex.printStackTrace();
			return code;
		}
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

}
