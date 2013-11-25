package userInterface;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JList;
import javax.swing.AbstractListModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.ListSelectionModel;

import clientServer.Client;
import clientServer.ClientDialog;
import clientServer.ClientServerData;

import java.awt.Dimension;
import java.awt.Component;
import java.util.HashMap;
import java.util.Set;
import java.util.Vector;

public class ClientUI extends JFrame
{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public static Client client;

	public static Vector<ClientDialog> dialogList;
	private static Vector<ClientListData> simpleClientList;
	private JList<ClientListData> list;
	private JTextArea textAreaDialog;
	private JTextArea textAreaSaisie;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					ClientUI frame = new ClientUI(new Client("", 3000, ""));
					frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the frame.
	 */
	public ClientUI(Client clientServer)
	{
		client = clientServer;
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		Box vbGlobale = Box.createVerticalBox();
		contentPane.add(vbGlobale, BorderLayout.CENTER);

		JPanel panelHaut = new JPanel();
		panelHaut.setPreferredSize(new Dimension(10, 2500));
		vbGlobale.add(panelHaut);
		panelHaut.setLayout(new BorderLayout(0, 0));

		Box hbHaut = Box.createHorizontalBox();
		panelHaut.add(hbHaut, BorderLayout.CENTER);

		JPanel panelList = new JPanel();
		panelList.setMinimumSize(new Dimension(300, 200));
		panelList.setMaximumSize(new Dimension(300, 1000));
		hbHaut.add(panelList);
		panelList.setLayout(new BorderLayout(0, 0));

		simpleClientList = new Vector<ClientListData>();
		simpleClientList.add(new ClientListData("ee", "test"));
		list = new JList<ClientListData>(simpleClientList);
		list.setPreferredSize(new Dimension(100, 100));
		list.setMinimumSize(new Dimension(100, 100));
		panelList.add(list, BorderLayout.CENTER);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		/*
		 * list.setModel(new AbstractListModel<String>() { private static final
		 * long serialVersionUID = 1L; String[] values = new String[] { "Micka",
		 * "Thibault  ", "Dorian", "Raph" };
		 * 
		 * public int getSize() { return values.length; }
		 * 
		 * public String getElementAt(int index) { return values[index]; } });
		 */

		Component hsRightList = Box.createHorizontalStrut(20);
		hsRightList.setPreferredSize(new Dimension(5, 0));
		hbHaut.add(hsRightList);

		textAreaDialog = new JTextArea();
		textAreaDialog.setColumns(40);
		hbHaut.add(textAreaDialog);

		Component vsPanHautPanBas = Box.createVerticalStrut(5);
		vbGlobale.add(vsPanHautPanBas);

		JPanel panelBas = new JPanel();
		vbGlobale.add(panelBas);
		panelBas.setLayout(new BorderLayout(0, 0));

		Box hbBas = Box.createHorizontalBox();
		panelBas.add(hbBas, BorderLayout.CENTER);

		textAreaSaisie = new JTextArea();
		hbBas.add(textAreaSaisie);

		Component hsRightSaisie = Box.createHorizontalStrut(20);
		hsRightSaisie.setPreferredSize(new Dimension(5, 0));
		hbBas.add(hsRightSaisie);

		Box vbBouton = Box.createVerticalBox();
		hbBas.add(vbBouton);

		JButton btnEnvoyer = new JButton("Envoyer");
		btnEnvoyer.setPreferredSize(new Dimension(90, 23));
		btnEnvoyer.setMaximumSize(new Dimension(90, 23));
		btnEnvoyer.setMinimumSize(new Dimension(90, 23));
		btnEnvoyer.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				if (list.getSelectedValue() != null)
				{
					ClientListData dialog = list.getSelectedValue();
					client.sendMessageToClient(textAreaSaisie.getText(), dialog.getKey());
				}
			}
		});

		JPanel panelVideBtn = new JPanel();
		vbBouton.add(panelVideBtn);
		vbBouton.add(btnEnvoyer);

		JButton btnFermer = new JButton("Fermer");
		btnFermer.setPreferredSize(new Dimension(90, 23));
		btnFermer.setMaximumSize(new Dimension(90, 23));
		btnFermer.setMinimumSize(new Dimension(90, 23));
		btnFermer.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
			}
		});

		Component vsBtn = Box.createVerticalStrut(5);
		vsBtn.setMinimumSize(new Dimension(0, 5));
		vsBtn.setPreferredSize(new Dimension(0, 5));
		vbBouton.add(vsBtn);
		vbBouton.add(btnFermer);

		new Thread(new Runnable()
		{

			@Override
			public void run()
			{
				Vector<ClientDialog> temp = new Vector<ClientDialog>(client.getDialogs());
				while (true)
				{
					if (!temp.equals(client.getDialogs()))
					{
						System.out.println("changement");
						temp = new Vector<ClientDialog>(client.getDialogs());
						refreshList();
					}
					try
					{
						Thread.sleep(200);
					} catch (InterruptedException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		}).start();

		new Thread(new Runnable()
		{

			@Override
			public void run()
			{
				while (true)
				{
					if (!list.isSelectionEmpty())
					{
						ClientListData dialog = list.getSelectedValue();
						String idDialog = dialog.getKey();
						Vector<ClientDialog> listDialog = client.getDialogs();
						String textDialog = "";
						for (ClientDialog clientDialog : listDialog)
						{
							if (clientDialog.getIdDialog().equals(idDialog))
							{
								textDialog = clientDialog.getDialogue();
							}
						}
						textAreaDialog.setText(textDialog);
					}

					try
					{
						Thread.sleep(20);
					} catch (InterruptedException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		}).start();
	}

	public void refreshList()
	{
		dialogList = client.getDialogs();
		simpleClientList = new Vector<ClientListData>();

		for (ClientDialog dialog : dialogList)
		{
			String idDialog = dialog.getIdDialog();
			Vector<ClientServerData> clients = dialog.getClients();
			String clientstring = "";
			for (ClientServerData clientServerData : clients)
			{
				clientstring += clientServerData + " ";
			}
			ClientListData clientListData = new ClientListData(idDialog, clientstring);
			simpleClientList.add(clientListData);
		}
		System.out.println("nouvelle list" + simpleClientList);
		list.setListData(simpleClientList);
	}

}
