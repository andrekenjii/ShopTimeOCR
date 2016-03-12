package app;

import dao.ProductDAO;
import domain.*;

public class AppController {
	TransparentFrame windowReference;

	public AppController() {
		this.windowReference = new TransparentFrame();
	}

	public int TakePicture() {
		CaptureData cp = new CaptureData();
		cp.windowRefence = this.windowReference;
		cp.TakePicture();
		return cp.ProcessOCR();
	}

	public void CreateThreadSensorColor() {

		ThreadSensorColor t = new ThreadSensorColor(this);
		t.windowRefence = this.windowReference;
		t.start();

	}
	
	public void InsertProduct(Product product) {
		new ProductDAO().InsertProduct(product);			
	}
}
