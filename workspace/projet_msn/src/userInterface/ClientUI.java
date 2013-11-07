package userInterface;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.JList;
import java.awt.Color;

public class ClientUI {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientUI window = new ClientUI();
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
	public ClientUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setForeground(Color.WHITE);
		frame.setResizable(false);
		frame.setBounds(100, 100, 800, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JPanel panelTchat = new JPanel();
		panelTchat.setBounds(181, 0, 603, 662);
		frame.getContentPane().add(panelTchat);
		panelTchat.setLayout(null);

		JButton btnEnvoyer = new JButton("Envoyer");
		btnEnvoyer.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0)
			{

				//TODO action bouton envoyer

			}
		});
		btnEnvoyer.setBounds(504, 520, 89, 60);
		panelTchat.add(btnEnvoyer);

		JButton btnQuitter = new JButton("Quitter");
		btnQuitter.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				//TODO action bouton quitter
			}
		});
		btnQuitter.setBounds(504, 591, 89, 60);
		panelTchat.add(btnQuitter);

		JScrollPane scrollPaneSaisie = new JScrollPane();
		scrollPaneSaisie.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPaneSaisie.setBounds(10, 520, 482, 131);
		panelTchat.add(scrollPaneSaisie);

		JTextArea textAreaSaisie = new JTextArea();
		scrollPaneSaisie.setViewportView(textAreaSaisie);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(10, 11, 583, 496);
		panelTchat.add(scrollPane);

		JTextPane textPane = new JTextPane();
		scrollPane.setViewportView(textPane);
		
		JScrollPane scrollPaneClient = new JScrollPane();
		scrollPaneClient.setBounds(10, 11, 172, 640);
		frame.getContentPane().add(scrollPaneClient);
		
		
		Vector<String> clientVector=new Vector<String>();
        clientVector.add(new String("Micka"));
        clientVector.add(new String("Thibault"));
        clientVector.add(new String("Dorian"));
        clientVector.add(new String("Raph"));
		JList listClients = new JList(clientVector);
		scrollPaneClient.setViewportView(listClients);
	}
}
