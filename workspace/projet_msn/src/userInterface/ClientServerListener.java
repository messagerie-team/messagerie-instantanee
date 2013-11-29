package userInterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

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
		case "Rafraichir":
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
				while ((ClientServerUI.client.getId() == null || ClientServerUI.client.getId().equals("")) && cpt < 500)
				{
					Thread.sleep(200);
					cpt++;
				}
				if (cpt != 500)
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

		default:
			break;
		}
	}
}
