package app;

import static java.awt.GraphicsDevice.WindowTranslucency.TRANSLUCENT;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;

public class TransparentFrame extends JFrame implements MouseMotionListener, ActionListener {

	private static final long serialVersionUID = 1L;

	public TransparentFrame() {
		/* Check for the transparency feature */
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();

		if (!gd.isWindowTranslucencySupported(TRANSLUCENT)) {
			System.err.println("Translucency is not supported");
			System.exit(0);
		}			
		
		addMouseMotionListener(this);

		setUndecorated(true);

		setLayout(new GridBagLayout());

		setSize(210, 40);

		setLocation(291, 136);
		
		// Set the window to 55% opaque (45% translucent).		
		this.setOpacity(0.55f);

		// Display the window.
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(java.awt.event.MouseEvent e) {
		// Positioning with the mouse
		this.setLocation(e.getLocationOnScreen().x - this.getSize().width / 2,
				e.getLocationOnScreen().y - this.getSize().height / 2);
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
