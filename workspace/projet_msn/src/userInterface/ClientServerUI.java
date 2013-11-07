package userInterface;

import java.awt.Color;
import java.awt.EventQueue;
import java.util.Vector;

import javax.swing.JFrame;

public class ClientServerUI {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientServerUI window = new ClientServerUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ClientServerUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setForeground(Color.WHITE);
		frame.setResizable(false);
		frame.setBounds(100, 100, 666, 423);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		Vector<String> clientVector=new Vector<String>();
        clientVector.add(new String("Micka"));
        clientVector.add(new String("Thibault"));
        clientVector.add(new String("Dorian"));
        clientVector.add(new String("Raph"));
	}
}
