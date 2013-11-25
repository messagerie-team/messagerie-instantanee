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

import java.awt.Dimension;
import java.awt.Component;
import java.util.Vector;

public class ClientUI extends JFrame
{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	private JList<ClientListData> list;
	private static Vector<ClientListData> simpleClientList;

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
					ClientUI frame = new ClientUI();
					frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ClientUI()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

		JTextArea textAreaDialog = new JTextArea();
		textAreaDialog.setColumns(40);
		hbHaut.add(textAreaDialog);

		Component vsPanHautPanBas = Box.createVerticalStrut(5);
		vbGlobale.add(vsPanHautPanBas);

		JPanel panelBas = new JPanel();
		vbGlobale.add(panelBas);
		panelBas.setLayout(new BorderLayout(0, 0));

		Box hbBas = Box.createHorizontalBox();
		panelBas.add(hbBas, BorderLayout.CENTER);

		JTextArea textAreaSaisie = new JTextArea();
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
	}

}
