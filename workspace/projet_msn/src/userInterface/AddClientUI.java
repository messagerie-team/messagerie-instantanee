package userInterface;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Set;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JList;

import clientServer.Client;
import clientServer.ClientDialog;
import clientServer.ClientServerData;
/**
 * 
 * @author Dorian, Mickaël, Raphaël, Thibault
 * 
 */
public class AddClientUI
{
	private static JFrame mainFrame;
	public static Client client;
	public static ClientDialog dialog;

	public static HashMap<String, String> clientList;
	private static Set<String> keyClientList;
	private static Vector<ClientListData> SimpleClientList;
	public static JList<ClientListData> listTest;

	/**
	 * Création de la fenêtre principale.
	 */
	public AddClientUI(Client clientRef, ClientDialog dialogRef)
	{
		client = clientRef;
		dialog = dialogRef;
		clientList = client.getClientList();
		keyClientList = clientList.keySet();
		initialize();

		new Thread(new Runnable()
		{

			@Override
			public void run()
			{
				// TODO Auto-generated method stub
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
						Thread.sleep(500);
					} catch (InterruptedException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	public static void refreshClient()
	{
		clientList = client.getClientList();
		keyClientList = clientList.keySet();
		SimpleClientList = new Vector<ClientListData>();

		for (ClientServerData client : dialog.getClients())
		{
			keyClientList.remove(client.getId());
		}

		for (String key : keyClientList)
		{
			ClientListData clientListData = new ClientListData(key, clientList.get(key));
			SimpleClientList.add(clientListData);
		}
		System.out.println("nouvelle list" + SimpleClientList);
		listTest.setListData(SimpleClientList);
		getMainFrame().getContentPane().add(listTest, BorderLayout.CENTER);
		listTest.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		setMainFrame(new JFrame("Ajout"));

		listTest = new JList<ClientListData>();
		listTest.setListData(new Vector<ClientListData>());
		listTest.updateUI();
		listTest.addMouseListener(new MouseListener()
		{

			@Override
			public void mouseReleased(MouseEvent e)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e)
			{
				if (e.getClickCount() == 2)
				{
					System.out.println("dbclick");
					ClientListData clientList = AddClientUI.listTest.getSelectedValue();
					if (clientList != null)
					{
						ClientServerUI.client.addClientToDialog(clientList.getKey(), dialog);
					}
					mainFrame.dispose();
				}
			}
		});

		getMainFrame().setLocation(400, 300);
		getMainFrame().setMinimumSize(new Dimension(200, 300));
		getMainFrame().setResizable(false);
		getMainFrame().setVisible(true);
		getMainFrame().setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		refreshClient();
	}

	public static JFrame getMainFrame()
	{
		return mainFrame;
	}

	public static void setMainFrame(JFrame frame)
	{
		AddClientUI.mainFrame = frame;
	}
}
