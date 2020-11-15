package t;
import java.awt.BorderLayout;
import javax.swing.*;
@SuppressWarnings("serial") public class MainGui extends JPanel {
	@SuppressWarnings("serial") public static class MyJApplet extends JApplet {
		@Override public void start() {}
		@Override public void init() {
			getContentPane().setLayout(new BorderLayout());
			getContentPane().add(new MainGui(this),BorderLayout.CENTER);
		}
	}
	public MainGui() {
		this(null);
	}
	public MainGui(MyJApplet applet) {
		this.applet=applet;
		if(!isApplet()) {
			frame=new JFrame();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		} else frame=null;
		setLayout(new BorderLayout());
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MainGui.this.run();
			}
		});
	}
	String title() {
		return "Time vs Threads";
	}
	public void addContent() {
		add(new JLabel("add content! top"));
	}
	void run() {
		if(isApplet())
			addContent();
		else {
			frame.setTitle(title());
			frame.getContentPane().add(this,BorderLayout.CENTER);
			addContent();
			frame.pack();
			frame.setVisible(true);
		}
	}
	public boolean isApplet() {
		return applet!=null;
	}
	public static void main(String[] args) {
		new MainGui(null);
	}
	protected final JFrame frame;
	private final MyJApplet applet;
}
