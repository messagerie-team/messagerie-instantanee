package clientServer;

import java.util.Vector;

public abstract class AbstractClientServer
{
	private Vector<ClientServerData> clients;

	public AbstractClientServer()
	{
		this.clients = new Vector<ClientServerData>();
	}

	public AbstractClientServer(Vector<ClientServerData> clients)
	{
		this.clients = clients;
	}

	public Vector<ClientServerData> getClients()
	{
		return clients;
	}

	public void setClients(Vector<ClientServerData> clients)
	{
		this.clients = clients;
	}

	public abstract void treatIncomeTCP(Object object);

	public abstract void treatIncomeUDP(String message);
}
