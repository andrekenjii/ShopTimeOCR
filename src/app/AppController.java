package app;

public class AppController {
	TransparentFrame windowReference;

	public AppController() {
		this.windowReference = new TransparentFrame();
	}

	public boolean TakePicture() {
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
}
