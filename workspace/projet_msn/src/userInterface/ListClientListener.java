package userInterface;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
/**
 * 
 * @author Dorian, Mickaël, Raphaël, Thibault
 * 
 */
public class ListClientListener implements MouseListener
{
	@Override
	public void mouseClicked(MouseEvent e)
	{
		if (e.getClickCount() == 2)
		{
			System.out.println("dbclick");
			JListData clientList = ClientServerUI.jClientList.getSelectedValue();
			if (clientList != null)
			{
				ClientServerUI.client.askClientConnectionToServer(clientList.getKey());
				ClientServerUI.getDialogFrame().setVisible(true);
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
