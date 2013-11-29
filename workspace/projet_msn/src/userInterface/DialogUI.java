package userInterface;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JList;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ListSelectionModel;

import clientServer.Client;
import clientServer.ClientDialog;
import clientServer.ClientServerData;

import java.awt.Dimension;
import java.awt.Component;
import java.util.Vector;

public class DialogUI extends JFrame
{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Client client;

	private Vector<ClientDialog> dialogList;
	private Vector<ClientListData> SimpleDialogtList;
	private JList<ClientListData> jDialogList;
	private JTextArea textAreaDialog;
	private JTextArea textAreaSaisie;

	/**
	 * Launch the application.
	 */
	/*
	 * public static void main(String[] args) { EventQueue.invokeLater(new
	 * Runnable() { public void run() { try { ClientUI frame = new ClientUI(new
	 * Client("", 3000, "")); frame.setVisible(true); } catch (Exception e) {
	 * e.printStackTrace(); } } }); }
	 */

	/**
	 * Create the frame.
	 */
	public DialogUI(Client clientServer)
	{
		client = clientServer;
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		setMinimumSize(new Dimension(300, 350));
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

		SimpleDialogtList = new Vector<ClientListData>();
		jDialogList = new JList<ClientListData>(SimpleDialogtList);
		jDialogList.setPreferredSize(new Dimension(100, 100));
		jDialogList.setMinimumSize(new Dimension(100, 100));
		panelList.add(jDialogList, BorderLayout.CENTER);
		jDialogList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		Component hsRightList = Box.createHorizontalStrut(20);
		hsRightList.setPreferredSize(new Dimension(5, 0));
		hbHaut.add(hsRightList);

		textAreaDialog = new JTextArea();
		textAreaDialog.setLineWrap(true);
		textAreaDialog.setWrapStyleWord(true);
		textAreaDialog.setEditable(false);
		JScrollPane areaDialog = new JScrollPane(textAreaDialog, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		hbHaut.add(areaDialog);

		Component vsPanHautPanBas = Box.createVerticalStrut(5);
		vbGlobale.add(vsPanHautPanBas);

		JPanel panelBas = new JPanel();
		vbGlobale.add(panelBas);
		panelBas.setLayout(new BorderLayout(0, 0));

		Box hbBas = Box.createHorizontalBox();
		panelBas.add(hbBas, BorderLayout.CENTER);

		textAreaSaisie = new JTextArea();
		textAreaSaisie.setLineWrap(true);
		textAreaSaisie.setWrapStyleWord(true);
		JScrollPane areaSaisie = new JScrollPane(textAreaSaisie, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		hbBas.add(areaSaisie);
		textAreaSaisie.addKeyListener(new KeyListener()
		{

			@Override
			public void keyTyped(KeyEvent e)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e)
			{
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					sendMessage();
				}
			}

			@Override
			public void keyPressed(KeyEvent e)
			{
				// TODO Auto-generated method stub
			}
		});

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
				sendMessage();
			}
		});

		JButton btnAjout = new JButton("Ajouter");
		btnAjout.setPreferredSize(new Dimension(90, 23));
		btnAjout.setMaximumSize(new Dimension(90, 23));
		btnAjout.setMinimumSize(new Dimension(90, 23));
		btnAjout.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				// hideDialog();
				if (jDialogList.getSelectedValue() != null)
				{
					ClientListData dialogListElement = jDialogList.getSelectedValue();
					String idDialog = dialogListElement.getKey();
					Vector<ClientDialog> listDialog = client.getDialogs();
					ClientDialog dialog = null;
					for (ClientDialog clientDialog : listDialog)
					{
						if (clientDialog.getIdDialog().equals(idDialog))
						{
							dialog = clientDialog;
						}
					}
					if (dialog != null)
					{
						AddClientUI addClient = new AddClientUI(client, dialog);
					}
				}
			}
		});

		JButton btnFermer = new JButton("Fermer");
		btnFermer.setPreferredSize(new Dimension(90, 23));
		btnFermer.setMaximumSize(new Dimension(90, 23));
		btnFermer.setMinimumSize(new Dimension(90, 23));
		btnFermer.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				hideDialog();
			}
		});

		vbBouton.add(btnEnvoyer);
		vbBouton.add(btnAjout);
		vbBouton.add(btnFermer);
		btnEnvoyer.setBorder(new EmptyBorder(5, 5, 5, 5));
		btnAjout.setBorder(new EmptyBorder(5, 5, 5, 5));
		btnFermer.setBorder(new EmptyBorder(5, 5, 5, 5));

		// Thread de mise a jour de la liste de conversation
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
						temp = new Vector<ClientDialog>(client.getDialogs());
						refreshList();
					} else
					{
						int cptInUse = 0;
						for (int i = 0; i < client.getDialogs().size(); i++)
						{
							if (client.getDialogs().get(i).isInUse())
							{
								cptInUse++;
							}
						}
						boolean needRefresh = false;
						for (int i = 0; i < client.getDialogs().size(); i++)
						{
							if (client.getDialogs().get(i).getClients().size() != temp.get(i).getClients().size())
							{
								needRefresh = true;
							}
						}
						if (cptInUse > jDialogList.getModel().getSize() || needRefresh)
						{
							refreshList();
						}

					}
					try
					{
						Thread.sleep(200);
					} catch (InterruptedException e)
					{
					}
				}

			}
		}).start();
		// Thread de mise a jour de la conversation
		new Thread(new Runnable()
		{

			@Override
			public void run()
			{
				while (true)
				{
					if (!jDialogList.isSelectionEmpty())
					{
						ClientListData dialog = jDialogList.getSelectedValue();
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

					}
				}

			}
		}).start();
	}

	public void refreshList()
	{
		dialogList = client.getDialogs();
		SimpleDialogtList = new Vector<ClientListData>();
		ClientListData sauvDialog = jDialogList.getSelectedValue();
		int indiceClient = -1;
		int cpt = 0;
		for (ClientDialog dialog : dialogList)
		{
			if (dialog.isInUse())
			{
				String idDialog = dialog.getIdDialog();
				Vector<ClientServerData> clients = dialog.getClients();
				String clientstring = "";
				for (ClientServerData clientServerData : clients)
				{
					clientstring += clientServerData.getName() + " ";
				}
				ClientListData clientListData = new ClientListData(idDialog, clientstring);
				SimpleDialogtList.add(clientListData);
				if (sauvDialog != null && sauvDialog.equals(clientListData))
				{
					indiceClient = cpt;
				}
				cpt++;
			}
		}
		System.out.println("nouvelle list" + SimpleDialogtList);
		jDialogList.setListData(SimpleDialogtList);
		if (textAreaSaisie.getText().trim().equals(""))
		{
			jDialogList.setSelectedIndices(new int[] { jDialogList.getModel().getSize() - 1 });
		} else if (sauvDialog != null)
		{
			jDialogList.setSelectedIndices(new int[] { indiceClient });
		}
		if (jDialogList.getSelectedValue() != null)
		{
			this.setTitle("Dialog-" + client.getName() + ":" + jDialogList.getSelectedValue());
		} else
		{
			this.setTitle("Dialog-" + client.getName());
		}
	}

	public void sendMessage()
	{
		if (jDialogList.getSelectedValue() != null)
		{
			ClientListData dialog = jDialogList.getSelectedValue();
			String message = textAreaSaisie.getText();
			String[] messagesSplit = message.split("[\r\n]");
			message = "";
			for (String messageSplit : messagesSplit)
			{
				message += messageSplit + " ";
			}
			client.sendMessageToClient(message, dialog.getKey());
			textAreaSaisie.setText("");
		}
	}

	public void hideDialog()
	{
		if (jDialogList.getSelectedValue() != null)
		{
			ClientListData dialog = jDialogList.getSelectedValue();
			client.hideDialog(dialog.getKey());
			textAreaSaisie.setText("");
			textAreaDialog.setText("");
			this.refreshList();
		}
	}

}
