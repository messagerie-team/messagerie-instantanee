package userInterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientServerListener implements ActionListener
{
	@Override
	public void actionPerformed(ActionEvent e)
	{
		// TODO Auto-generated method stub
		System.out.println("action:" + e.getActionCommand());
		switch (e.getActionCommand())
		{
		case "Profil":
			
			break;
		case "Rafraichir":
			ClientServerUI.client.askListToServer();
			try
			{
				Thread.sleep(1000);
			} catch (InterruptedException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println(ClientServerUI.client.getClientList());
			System.out.println(ClientServerUI.clientList);
			ClientServerUI.refreshClient();
			break;
		case "Se connecter":
			ClientServerUI.client.registerToServer();
			ClientServerUI.refreshClient();
			break;
		case "Se déconnecter":
			ClientServerUI.client.unregisterToServer();
			break;

		default:
			break;
		}
	}
}
