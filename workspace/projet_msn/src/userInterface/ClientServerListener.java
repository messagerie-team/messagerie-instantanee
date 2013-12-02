package userInterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JOptionPane;

/**
 * 
 * @author Dorian, Mickaël, Raphaël, Thibault
 * 
 */
public class ClientServerListener implements ActionListener
{
	@Override
	public void actionPerformed(ActionEvent e)
	{
		System.out.println("action:" + e.getActionCommand());
		switch (e.getActionCommand())
		{
		case "Profil":

			break;
		case "Rafraîchir":
			if (!ClientServerUI.client.getId().equals(""))
			{
				HashMap<String, String> list = new HashMap<>(ClientServerUI.client.getClientList());
				ClientServerUI.client.askListToServer();
				int ttl = 0;
				while (list.equals(ClientServerUI.client.getClientList()) && ttl < 7)
				{
					try
					{
						Thread.sleep(200);
						ttl++;
					} catch (InterruptedException e1)
					{
					}
				}
				System.out.println(ClientServerUI.client.getClientList());
				System.out.println(ClientServerUI.clientList);

				ClientServerUI.refreshClient();
			}
			break;
		case "Se connecter":
			ClientServerUI.client.setName(ClientServerUI.pseudoField.getText());
			ClientServerUI.client.registerToServer();
			try
			{
				int cpt = 0;
				while ((ClientServerUI.client.getId() == null || ClientServerUI.client.getId().equals("")) && cpt < 50)
				{
					Thread.sleep(200);
					cpt++;
				}
				if (cpt < 50)
				{
					ClientServerUI.refreshClient();
				}
			} catch (InterruptedException e1)
			{

			}
			break;
		case "Se déconnecter":
			ClientServerUI.client.unregisterToServer();
			ClientServerUI.jClientList.setVisible(false);
			ClientServerUI.connectionPanel.setVisible(true);
			break;
		case "Adresse serveur":
			if (ClientServerUI.client.getId().equals(""))
			{
				String ipServer = JOptionPane.showInputDialog(ClientServerUI.getMainFrame(), "IP serveur", ClientServerUI.client.getIpServer());
				if (ipServer != null)
				{
					ClientServerUI.client.setIpServer(ipServer);
				}
			}
			break;
		case "Port UDP serveur":
			if (ClientServerUI.client.getId().equals(""))
			{
				String udpServerPort = JOptionPane.showInputDialog(ClientServerUI.getMainFrame(), "Port UDP serveur", ClientServerUI.client.getUdpServerPort());
				if (udpServerPort != null)
				{
					ClientServerUI.client.setUdpServerPort(Integer.parseInt(udpServerPort));
				}
			}
			break;
		case "Port TCP serveur":
			if (ClientServerUI.client.getId().equals(""))
			{
				String tcpServerPort = JOptionPane.showInputDialog(ClientServerUI.getMainFrame(), "Port UDP serveur", ClientServerUI.client.getTcpServerPort());
				if (tcpServerPort != null)
				{
					ClientServerUI.client.setTcpServerPort(Integer.parseInt(tcpServerPort));
				}
			}
			break;
		case "Port UDP":
			if (ClientServerUI.client.getId().equals(""))
			{
				String port = JOptionPane.showInputDialog(ClientServerUI.getMainFrame(), "Port UDP", ClientServerUI.client.getListeningUDPPort());
				if (port != null)
				{
					ClientServerUI.client.setListeningUDPPort(Integer.parseInt(port));
				}
			}
			break;

		default:
			break;
		}
	}
}
