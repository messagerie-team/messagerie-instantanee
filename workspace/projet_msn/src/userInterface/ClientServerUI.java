package userInterface;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.util.HashMap;
import java.util.Set;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
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
		client = new Client("client1", 3003, "localhost");
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
		//Panel principal
		connectionPanel = new JPanel();
		connectionPanel.setLayout(new BorderLayout(0, 0));
		//Box principal
		Box principalBox = Box.createVerticalBox();
		principalBox.setBorder(new EmptyBorder(90, 0, 0, 0));

		//Construction du pseudo
		pseudoField = new JTextField("Raphael");
		pseudoField.setMinimumSize(new Dimension(110, 20));
		pseudoField.setMaximumSize(new Dimension(135, 25));

		//Construction du bouton de connexion
		JButton connectionButton = new JButton("Se connecter");
		connectionButton.setMinimumSize(new Dimension(110, 20));
		connectionButton.setMaximumSize(new Dimension(135, 25));
		connectionButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		connectionButton.addActionListener(listenerMenu);
		
		//Ajout des elements
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
