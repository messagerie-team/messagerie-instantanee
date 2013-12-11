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

import model.Client;
import model.ClientDialog;
import model.ClientServerData;

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

	public static HashMap<String, String[]> clientList;
	private static Set<String> keyClientList;
	private static Vector<JListData> simpleClientList;
	public static JList<JListData> displayList;

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
				HashMap<String, String[]> listTemp = new HashMap<String, String[]>(client.getClientList());
				while (true)
				{
					if (!listTemp.equals(client.getClientList()))
					{
						listTemp = new HashMap<String, String[]>(client.getClientList());
						refreshClient();
					}
					try
					{
						Thread.sleep(500);
					} catch (InterruptedException e)
					{
						// e.printStackTrace();
					}
				}
			}
		}).start();
	}

	public static void refreshClient()
	{
		clientList = client.getClientList();
		keyClientList = clientList.keySet();
		simpleClientList = new Vector<JListData>();

		for (ClientServerData client : dialog.getClients())
		{
			keyClientList.remove(client.getId());
		}

		for (String key : keyClientList)
		{
			JListData clientListData = new JListData(key, clientList.get(key)[0] + " " + clientList.get(key)[1]);
			simpleClientList.add(clientListData);
		}
		System.out.println("nouvelle list" + simpleClientList);
		displayList.setListData(simpleClientList);
		getMainFrame().getContentPane().add(displayList, BorderLayout.CENTER);
		displayList.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		setMainFrame(new JFrame("Ajout"));

		displayList = new JList<JListData>();
		displayList.setListData(new JListData[0]);
		displayList.updateUI();
		displayList.addMouseListener(new MouseListener()
		{

			@Override
			public void mouseReleased(MouseEvent e)
			{

			}

			@Override
			public void mousePressed(MouseEvent e)
			{

			}

			@Override
			public void mouseExited(MouseEvent e)
			{

			}

			@Override
			public void mouseEntered(MouseEvent e)
			{

			}

			@Override
			public void mouseClicked(MouseEvent e)
			{
				if (e.getClickCount() == 2)
				{
					System.out.println("dbclick");
					JListData clientList = AddClientUI.displayList.getSelectedValue();
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
