package userInterface;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.util.HashMap;
import java.util.Set;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

import clientServer.Client;

import javax.swing.Box;

import java.awt.Component;

public class ClientServerUI
{

	private static JFrame frame;
	public static Client client;
	public static HashMap<String, String> clientList;
	private static Set<String> keyClientList;
	private static Vector<ClientListData> SimpleClientList;
	public static JList<ClientListData> listTest;

	public static ClientServerListener listenerMenu;
	public static ListClientListener listenerList;
	public static JMenuBar menuBar;
	public static JPanel connectionPanel;
	public static JTextField pseudoField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			@SuppressWarnings("static-access")
			public void run()
			{
				try
				{
					ClientServerUI window = new ClientServerUI();
					window.getFrame().setVisible(true);
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
		client = new Client("client3", 3000, "localhost");
		clientList = client.getClientList();
		keyClientList = clientList.keySet();
		listenerMenu = new ClientServerListener();
		listenerList = new ListClientListener();
		initialize();
	}

	public static void refreshClient()
	{
		// connectionPanel.removeAll();
		connectionPanel.setVisible(false);
		clientList = client.getClientList();
		keyClientList = clientList.keySet();
		SimpleClientList = new Vector<ClientListData>();

		for (String key : keyClientList)
		{
			ClientListData clientListData = new ClientListData(key, clientList.get(key));
			SimpleClientList.add(clientListData);
		}
		System.out.println("nouvelle list" + SimpleClientList);
		// listTest = new JList<ClientListData>(SimpleClientList);
		listTest.setListData(SimpleClientList);
		getFrame().getContentPane().add(listTest, BorderLayout.CENTER);
		listTest.updateUI();
		listTest.setVisible(true);
		listTest.validate();
		listTest.repaint();
		getFrame().validate();
		getFrame().repaint();
		// System.out.println(list);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		setFrame(new JFrame("Projet msn"));

		constructMenu();
		constructConnectionPanel();

		getFrame().getContentPane().add(menuBar, BorderLayout.NORTH);
		getFrame().getContentPane().add(connectionPanel, BorderLayout.CENTER);
		listTest = new JList<ClientListData>();
		listTest.setListData(new Vector<ClientListData>());
		listTest.updateUI();
		listTest.addMouseListener(listenerList);
		// frame.getContentPane().add(list, BorderLayout.SOUTH);

		getFrame().setLocation(400, 300);
		getFrame().setMinimumSize(new Dimension(200, 300));
		getFrame().setResizable(false);
		getFrame().setVisible(true);
		getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void constructMenu()
	{
		menuBar = new JMenuBar();
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
		// menuPrincipal.add(connection);
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
	}

	private void constructConnectionPanel()
	{
		connectionPanel = new JPanel();

		connectionPanel.setLayout(new BorderLayout(0, 0));

		Box verticalBox = Box.createVerticalBox();
		connectionPanel.add(verticalBox, BorderLayout.CENTER);

		Component vsTop = Box.createVerticalStrut(20);
		vsTop.setMaximumSize(new Dimension(32767, 100));
		verticalBox.add(vsTop);

		pseudoField = new JTextField("Raphael");
		pseudoField.setMinimumSize(new Dimension(95, 20));
		pseudoField.setMaximumSize(new Dimension(110, 25));
		verticalBox.add(pseudoField);

		Component vsCenter = Box.createVerticalStrut(5);
		verticalBox.add(vsCenter);
		JButton connectionButton = new JButton("Se connecter");
		connectionButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		verticalBox.add(connectionButton);
		connectionButton.addActionListener(listenerMenu);
	}

	public static JFrame getFrame()
	{
		return frame;
	}

	public static void setFrame(JFrame frame) 
	{
		ClientServerUI.frame = frame;
	}
}
