package userInterface;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import clientServer.Client;

/**
 * 
 * @author Dorian, Mickaël, Raphaël, Thibault
 * 
 */
public class ClientServerUI
{
	private static JFrame mainFrame;
	private static JFrame dialogFrame;
	protected static Client client;

	protected static HashMap<String, String> clientList;
	private static Set<String> keyClientList;
	private static Vector<ClientListData> SimpleClientList;
	protected static JList<ClientListData> jClientList;

	private ClientServerListener listenerMenu;
	private ListClientListener listenerList;
	private JMenuBar menuBar;
	protected static JPanel connectionPanel;
	protected static JTextField pseudoField;

	private Properties properties;

	/**
	 * Lancement de l'application.
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
					getMainFrame().setVisible(true);
					window.toString();
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Création de l'application.
	 */
	public ClientServerUI()
	{
		properties = new Properties();
		try
		{
			FileInputStream file = new FileInputStream("configuration.property");
			properties.loadFromXML(file);
			file.close();
		} catch (IOException e2)
		{
			properties.put("ipServer", "localhost");
			properties.put("TCPServer", "30970");
			properties.put("UDPServer", "30971");
			properties.put("alias", "client");
			properties.put("UDPClient", "3000");
			try
			{
				FileOutputStream file = new FileOutputStream("configuration.property");
				properties.storeToXML(file, "");
				file.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		String alias = properties.getProperty("alias");
		int udpPort = Integer.parseInt(properties.getProperty("UDPClient"));
		String ipServer = properties.getProperty("ipServer");
		int udpServerPort = Integer.parseInt(properties.getProperty("UDPServer"));
		int tcpServerPort = Integer.parseInt(properties.getProperty("TCPServer"));

		client = new Client(alias, udpPort, ipServer, udpServerPort, tcpServerPort);
		clientList = client.getClientList();
		keyClientList = clientList.keySet();
		listenerMenu = new ClientServerListener();
		listenerList = new ListClientListener();
		dialogFrame = new DialogUI(client);
		dialogFrame.setVisible(false);
		initialize();

		new Thread(new Runnable()
		{

			@Override
			public void run()
			{
				HashMap<String, String> listTemp = new HashMap<>(client.getClientList());
				while (true)
				{
					if (!listTemp.equals(client.getClientList()))
					{
						listTemp = new HashMap<>(client.getClientList());
						refreshClient();
					}
					try
					{
						Thread.sleep(100);
					} catch (InterruptedException e)
					{
					}
				}
			}
		}).start();
	}

	public static void refreshClient()
	{
		if (!client.getId().equals(""))
		{
			mainFrame.setTitle("msn-" + client.getName());
			dialogFrame.setTitle("Dialog-" + client.getName());
			if (!dialogFrame.isVisible())
			{
				dialogFrame.setVisible(true);
			}
			connectionPanel.setVisible(false);
			clientList = client.getClientList();
			keyClientList = clientList.keySet();
			SimpleClientList = new Vector<ClientListData>();

			for (String key : keyClientList)
			{
				ClientListData clientListData = new ClientListData(key, clientList.get(key));
				SimpleClientList.add(clientListData);
			}
			jClientList.setListData(SimpleClientList);
			getMainFrame().getContentPane().add(jClientList, BorderLayout.CENTER);
			jClientList.setVisible(true);
		} else
		{
			dialogFrame.setVisible(false);
		}
	}

	/**
	 * Initialise le éléments de la fenêtre principale
	 */
	private void initialize()
	{
		setMainFrame(new JFrame("Projet msn"));
		constructMenu();
		constructConnectionPanel();

		getMainFrame().getContentPane().add(menuBar, BorderLayout.NORTH);
		getMainFrame().getContentPane().add(connectionPanel, BorderLayout.CENTER);
		jClientList = new JList<ClientListData>();
		jClientList.setListData(new Vector<ClientListData>());
		jClientList.updateUI();
		jClientList.addMouseListener(listenerList);

		getMainFrame().setLocation(400, 300);
		getMainFrame().setMinimumSize(new Dimension(200, 300));
		getMainFrame().setResizable(false);
		getMainFrame().setVisible(true);
		getMainFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getMainFrame().addWindowListener(new java.awt.event.WindowAdapter()
		{
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent)
			{
				if (JOptionPane.showConfirmDialog(getMainFrame(), "Etes vous sur de vouloir quitter?", "Fermeture", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
				{
					properties.setProperty("alias", client.getName());
					properties.setProperty("ipServer", client.getIpServer());
					properties.setProperty("TCPServer", client.getTcpServerPort() + "");
					properties.setProperty("UDPServer", client.getUdpServerPort() + "");
					properties.setProperty("UDPClient", client.getListeningUDPPort() + "");

					try
					{
						properties.storeToXML(new FileOutputStream("configuration.property"), "");
					} catch (IOException e)
					{
						e.printStackTrace();
					}
					System.exit(0);
				}
			}
		});
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
		menuPrincipal.add(unConnection);

		JMenuItem adressServer = new JMenuItem("Adresse serveur");
		adressServer.addActionListener(listenerMenu);
		JMenuItem serverUDPPort = new JMenuItem("Port UDP serveur");
		serverUDPPort.addActionListener(listenerMenu);
		JMenuItem serverTCPPort = new JMenuItem("Port TCP serveur");
		serverTCPPort.addActionListener(listenerMenu);
		JMenuItem UDPPort = new JMenuItem("Port UDP");
		UDPPort.addActionListener(listenerMenu);
		//JMenuItem TCPPort = new JMenuItem("Port TCP");
		//TCPPort.addActionListener(listenerMenu);
		menuConfiguration.add(adressServer);
		menuConfiguration.add(serverUDPPort);
		menuConfiguration.add(serverTCPPort);
		menuConfiguration.add(UDPPort);
		//menuConfiguration.add(TCPPort);

		menuBar.add(menuPrincipal);
		menuBar.add(menuConfiguration);
	}

	private void constructConnectionPanel()
	{
		// Panel principal
		connectionPanel = new JPanel();
		connectionPanel.setLayout(new BorderLayout(0, 0));
		// Box principal
		Box principalBox = Box.createVerticalBox();
		principalBox.setBorder(new EmptyBorder(90, 0, 0, 0));

		// Construction du pseudo
		pseudoField = new JTextField(properties.getProperty("alias"));
		pseudoField.setMinimumSize(new Dimension(110, 20));
		pseudoField.setMaximumSize(new Dimension(135, 25));

		// Construction du bouton de connexion
		JButton connectionButton = new JButton("Se connecter");
		connectionButton.setMinimumSize(new Dimension(110, 20));
		connectionButton.setMaximumSize(new Dimension(135, 25));
		connectionButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		connectionButton.addActionListener(listenerMenu);

		// Ajout des elements
		principalBox.add(pseudoField);
		principalBox.add(connectionButton);
		connectionPanel.add(principalBox, BorderLayout.CENTER);
	}

	public static JFrame getMainFrame()
	{
		return mainFrame;
	}

	public static void setMainFrame(JFrame frame)
	{
		ClientServerUI.mainFrame = frame;
	}
}
