package com.tayek.utilities;
import java.awt.BorderLayout;
import javax.swing.*;
public class MainGui extends JPanel {
	public MainGui() {
		this(null);
	}
	protected MainGui(MyJApplet applet) {
		this.applet = applet;
		if (!isApplet()) {
			frame = new JFrame();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		} else
			frame = null;
		setLayout(new BorderLayout());
		// setPreferredSize(new Dimension(640, 480));
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MainGui.this.run();
			}
		});
	}
	String title() {
		return "Title";
	}
	public void addContent() {
		add(new JLabel("add content! top"));
	}
	void run() {
		if (isApplet()) addContent();
		else {
			frame.setTitle(title());
			frame.getContentPane().add(this, BorderLayout.CENTER);
			addContent();
			frame.pack();
			frame.setVisible(true);
		}
	}
	boolean isApplet() {
		return applet != null;
	}
	public static void main(String[] args) {
		new MainGui(null);
	}
	final private JFrame frame;
	final private MyJApplet applet;
	private static final long serialVersionUID = 1;
}
