package com.tayek.utilities;
import java.awt.BorderLayout;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
public class MainGui extends JPanel {
	public MainGui() {
		this(null);
	}
	public MainGui(MyJApplet applet) {
		this.applet=applet;
		if(!isApplet()) {
			frame=new JFrame();
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		} else frame=null;
		// setLayout(new BorderLayout()); // let's see what happens
		// setPreferredSize(new Dimension(640, 480));
		SwingUtilities.invokeLater(new Runnable() {
			@Override public void run() {
				MainGui.this.run();
			}
		});
	}
	public String title() {
		return "Main Gui";
	}
	public void addContent() {
		add(new JLabel("add content! top"));
	}
	void run() {
		if(isApplet()) addContent();
		else {
			frame.setTitle(title());
			frame.getContentPane().add(this,BorderLayout.CENTER); // center mau
																	// not be
																	// right if
																	// no other
																	// layout?
			addContent();
			frame.pack();
			System.out.println(getSize());
			frame.setVisible(true);
		}
	}
	public boolean isApplet() {
		return applet!=null;
	}
	public JFrame frame() {
		return frame;
	}
	public JApplet applet() {
		return applet;
	}
	public static void main(String[] args) {
		new MainGui(null);
	}
	public final JFrame frame;
	public final MyJApplet applet;
	private static final long serialVersionUID=1;
}
