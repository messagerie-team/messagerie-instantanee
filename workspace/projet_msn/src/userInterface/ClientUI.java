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

public class ClientUI extends JFrame 
{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientUI frame = new ClientUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ClientUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panelBas = new JPanel();
		contentPane.add(panelBas, BorderLayout.SOUTH);
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
		
		JButton btnValider = new JButton("Valider");
		btnValider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		vbBouton.add(btnValider);
		
		JButton btnQuitter = new JButton("Quitter");
		btnQuitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		vbBouton.add(btnQuitter);
		
		JPanel panelHaut = new JPanel();
		contentPane.add(panelHaut, BorderLayout.CENTER);
		panelHaut.setLayout(new BorderLayout(0, 0));
		
		Box hbHaut = Box.createHorizontalBox();
		panelHaut.add(hbHaut, BorderLayout.CENTER);
		
		JPanel panelList = new JPanel();
		hbHaut.add(panelList);
		panelList.setLayout(new BorderLayout(0, 0));
		
		JList list = new JList();
		panelList.add(list, BorderLayout.CENTER);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setModel(new AbstractListModel() {
			String[] values = new String[] {"Micka", "Thibault", "Dorian", "Raph"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		
		Component hsRightList = Box.createHorizontalStrut(20);
		hsRightList.setPreferredSize(new Dimension(5, 0));
		hbHaut.add(hsRightList);
		
		JTextArea textAreaDialog = new JTextArea();
		hbHaut.add(textAreaDialog);
	}

}
