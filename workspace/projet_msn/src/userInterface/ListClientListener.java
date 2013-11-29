package userInterface;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ListClientListener implements MouseListener
{
	@Override
	public void mouseClicked(MouseEvent e)
	{
		if (e.getClickCount() == 2)
		{
			System.out.println("dbclick");
			ClientListData clientList = ClientServerUI.jClientList.getSelectedValue();
			if (clientList != null)
			{
				ClientServerUI.client.askClientConnectionToServer(clientList.getKey());
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0)
	{
		// TODO Auto-generated method stub

	}

}
