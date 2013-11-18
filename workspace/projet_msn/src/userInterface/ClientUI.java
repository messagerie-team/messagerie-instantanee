package userInterface;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import clientServer.Client;

import dataLink.ProtocolUDP;

public class ClientUI
{

	private JFrame frame;
	// private final static int size = 60000;
	// private final static byte buffer[] = new byte[size];
	// private static ProtocolUDP protocol = new ProtocolUDP();
	private Client client;
	private JTextArea textAreaSaisie;
	private JTextArea textAreaRecu;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					ClientUI window = new ClientUI();
					window.frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ClientUI()
	{
		this.client = new Client("toto2", 3001);
		initialize();
		this.client.registerToServer();
		this.client.askListToServer();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
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
				String textToSend = textAreaSaisie.getText();
				System.out.println(textToSend);
				// protocol.sendMessage(textToSend);
				client.sendMessageToClient(textToSend, client.getDialogs().firstElement().getIdDialog());
				textAreaRecu.append("me<" + textToSend + "\n");
				textAreaSaisie.setText("");
			}
		});
		btnEnvoyer.setBounds(504, 520, 89, 60);
		panelTchat.add(btnEnvoyer);

		JButton btnQuitter = new JButton("Quitter");
		btnQuitter.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				// TODO action bouton quitter
				client.unregisterToServer();
				client.getThreadListenerUDP().stopThread();
			}
		});
		btnQuitter.setBounds(504, 591, 89, 60);
		panelTchat.add(btnQuitter);

		JScrollPane scrollPaneSaisie = new JScrollPane();
		scrollPaneSaisie.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPaneSaisie.setBounds(10, 520, 482, 131);
		panelTchat.add(scrollPaneSaisie);

		textAreaSaisie = new JTextArea();
		scrollPaneSaisie.setViewportView(textAreaSaisie);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(10, 11, 583, 496);
		panelTchat.add(scrollPane);

		textAreaRecu = new JTextArea();
		scrollPane.setViewportView(textAreaRecu);

		JScrollPane scrollPaneClient = new JScrollPane();
		scrollPaneClient.setBounds(10, 11, 172, 640);
		frame.getContentPane().add(scrollPaneClient);

		Vector<String> clientVector = new Vector<String>();
		clientVector.add(new String("Micka"));
		clientVector.add(new String("Thibault"));
		clientVector.add(new String("Dorian"));
		clientVector.add(new String("Raph"));
		JList listClients = new JList(clientVector);
		scrollPaneClient.setViewportView(listClients);
	}

	public void addMessage(String msg)
	{
		textAreaRecu.append(msg + "\n");
	}
}
