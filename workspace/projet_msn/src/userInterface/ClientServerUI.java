package userInterface;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Set;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import clientServer.Client;

public class ClientServerUI
{

	private JFrame frame;
	public static Client client;
	public static HashMap<String, String> clientList;
	private static Set<String> keyClientList;
	private static Vector<String> SimpleClientList;
	private static JPanel list;
	private static JList<String> listTest;

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
					ClientServerUI window = new ClientServerUI();
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
	public ClientServerUI()
	{
		client = new Client("client3", 3003,"localhost");
		clientList = client.getClientList();
		keyClientList = clientList.keySet();
		initialize();
	}

	public static void refreshClient()
	{
		keyClientList = clientList.keySet();
		SimpleClientList = new Vector<String>();
		for (String key : keyClientList)
		{
			SimpleClientList.add(clientList.get(key));
		}
		System.out.println("nouvelle list" + SimpleClientList);
		listTest = new JList<String>(SimpleClientList);
		list.add(listTest, BorderLayout.CENTER);
		list.repaint();
		listTest.repaint();
		// System.out.println(list.get);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frame = new JFrame("Projet msn");
		/*
		 * frame.getContentPane().setForeground(Color.WHITE);
		 * frame.setResizable(false);
		 * frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 * frame.getContentPane().setLayout(new BorderLayout());
		 */
		list = new JPanel();
		list.repaint();
		list.setLayout(new BorderLayout());
		refreshClient();

		ActionListener listenerMenu = new ClientServerListener();
		JMenuBar menuBar = new JMenuBar();
		JMenu menuPrincipal = new JMenu("Fichier");
		JMenu menuConfiguration = new JMenu("Configuration");
		JMenuItem profil = new JMenuItem("Profil");
		profil.addActionListener(listenerMenu);
		JMenuItem refresh = new JMenuItem("Rafraichir");
		refresh.addActionListener(listenerMenu);
		JMenuItem connection = new JMenuItem("Se connecter");
		connection.addActionListener(listenerMenu);
		JMenuItem unConnection = new JMenuItem("Se déconnecter");
		unConnection.addActionListener(listenerMenu);
		menuPrincipal.add(profil);
		menuPrincipal.add(refresh);
		menuPrincipal.add(connection);
		menuPrincipal.add(unConnection);

		JMenuItem adressServer = new JMenuItem("Adresse serveur");
		adressServer.addActionListener(listenerMenu);
		JMenuItem serverPort = new JMenuItem("Port serveur");
		serverPort.addActionListener(listenerMenu);
		JMenuItem UDPPort = new JMenuItem("Port UDP");
		UDPPort.addActionListener(listenerMenu);
		JMenuItem TCPPort = new JMenuItem("Port TCP");
		TCPPort.addActionListener(listenerMenu);
		menuConfiguration.add(adressServer);
		menuConfiguration.add(serverPort);
		menuConfiguration.add(UDPPort);
		menuConfiguration.add(TCPPort);

		menuBar.add(menuPrincipal);
		menuBar.add(menuConfiguration);

		frame.add(menuBar, BorderLayout.NORTH);
		frame.add(list, BorderLayout.CENTER);

		frame.setLocation(400, 300);
		frame.setMinimumSize(new Dimension(200, 300));
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
