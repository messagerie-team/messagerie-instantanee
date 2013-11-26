/**
 * 
 */
package clientServer;

import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Set;
import java.util.StringTokenizer;

import dataLink.Protocol;
import dataLink.ProtocolUDP;

/**
 * @author Mickael
 * 
 */
public class Server extends AbstractClientServer
{
	// Liste des clients que connait le serveur

	// Thread d'ecoute du serveur
	private ThreadListenerTCP threadListener;
	private ThreadListenerUDP threadListenerUDP;
	Protocol protocol;
	private HashMap<String, Integer> clientTTL;

	/**
	 * Construct Server() Constructeur le la class Server. Initialise les
	 * variables server,clients et threadListener. En ouvrant sur le port 30972
	 */
	public Server()
	{
		super();
		this.protocol = new ProtocolUDP(30971);
		this.threadListener = new ThreadListenerTCP(this, 30970);
		this.threadListenerUDP = new ThreadListenerUDP(this, this.protocol);
		this.clientTTL = new HashMap<String, Integer>();
	}

	/**
	 * Construct Server(int port) Constructeur de la class Server. Initialise
	 * les variables server,clients et threadListener.
	 * 
	 * @param int numero de port
	 */
	public Server(int port)
	{
		super();
		this.protocol = new ProtocolUDP(port + 1);
		this.threadListener = new ThreadListenerTCP(this, port);
		this.threadListenerUDP = new ThreadListenerUDP(this, this.protocol);
		this.clientTTL = new HashMap<String, Integer>();
	}

	/**
	 * Method launch() Méthode permettant de lancer le serveur.
	 */
	public void launch()
	{
		this.threadListener.start();
		this.threadListenerUDP.start();
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				while (true)
				{
					try
					{
						Thread.sleep(1000);
					} catch (InterruptedException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Set<String> keys = clientTTL.keySet();
					for (String key : keys)
					{
						int ttl = clientTTL.get(key);
						clientTTL.put(key, ttl - 1);
						if ((ttl - 1) < 0)
						{
							removeClient(key);
						}

					}
				}
			}
		}).start();
	}

	/**
	 * Method stopServer() Méthode permettant de stopper le serveur.
	 */
	public void stopServer()
	{
		this.threadListener.stopThread();
		this.threadListenerUDP.stopThread();
	}

	/**
	 * Method addClient
	 * 
	 * @param name
	 *            Nom du client
	 * @param client
	 *            Socket du client
	 * @return true si client ajouté, false sinon.
	 */
	public String addClient(String name, Socket client, int listeningUDPPort)
	{
		if (this.getClients().add(new ClientServerData(name, client.getInetAddress(), listeningUDPPort)))
		{
			new Thread(new Runnable()
			{

				@Override
				public void run()
				{
					try
					{
						Thread.sleep(3500);
					} catch (InterruptedException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					for (ClientServerData clientServerData : getClients())
					{
						sendListClient(clientServerData);
					}
				}
			}).start();
			this.clientTTL.put(this.getClients().lastElement().getId(), 10);
			return this.getClients().lastElement().getId();
		} else
		{
			return null;
		}
	}

	/**
	 * Method removeClient
	 * 
	 * @param client
	 *            {@link ClientServerData}
	 * @return true si réussit, false sinon
	 */
	public boolean removeClient(ClientServerData client)
	{
		boolean ret = this.getClients().remove(client);
		this.clientTTL.remove(client.getId());
		for (ClientServerData clientServerData : this.getClients())
		{
			this.sendListClient(clientServerData);
		}
		return ret;
	}

	/**
	 * Method removeClient
	 * 
	 * @param name
	 *            Nom du client
	 * @return true si réussit, false sinon
	 */
	public boolean removeClient(String id)
	{
		boolean erase = false;
		this.clientTTL.remove(id);
		ClientServerData eraseClient = null;
		for (ClientServerData client : this.getClients())
		{
			if (client.getId().equals(id))
			{
				eraseClient = client;
				erase = true;
			}
		}
		if (erase)
		{
			boolean ret = this.getClients().remove(eraseClient);
			for (ClientServerData clientServerData : this.getClients())
			{
				this.sendListClient(clientServerData);
			}
			return ret;
		}
		return false;
	}

	/**
	 * Method removeClient
	 * 
	 * @param ip
	 *            Ip du client {@link InetAddress}
	 * @return true si reussit, false sinon
	 */
	public boolean removeClient(InetAddress ip)
	{
		boolean erase = false;
		for (ClientServerData client : this.getClients())
		{
			if (client.getIp().equals(ip))
			{
				this.clientTTL.remove(client.getId());
				this.getClients().remove(client);
				erase = true;
			}
		}
		for (ClientServerData clientServerData : this.getClients())
		{
			this.sendListClient(clientServerData);
		}
		return erase;
	}

	public String getListClient()
	{
		String ret = "";
		boolean firstOne = true;
		for (ClientServerData client : this.getClients())
		{
			ret += ((firstOne) ? "" : ",") + client.getId() + "-" + client.getName();
			firstOne = false;
		}
		return ret;
	}

	public void sendListClient(ClientServerData client)
	{
		String listClient = this.getListClient();
		protocol.sendMessage("listClient:" + listClient, client.getIp(), client.getPort());
	}

	public String getClient(String id)
	{
		for (ClientServerData client : this.getClients())
		{
			if (client.getId().equals(id))
			{
				return client.getId() + "," + client.getName() + "," + client.getIp().getCanonicalHostName() + "," + client.getPort();
			}
		}
		return null;
	}

	public ThreadListenerTCP getThreadListener()
	{
		return threadListener;
	}

	public void setThreadListener(ThreadListenerTCP threadListener)
	{
		this.threadListener = threadListener;
	}

	@Override
	public void treatIncomeTCP(Object object)
	{
		if (object instanceof Socket)
		{
			ThreadComunicationServer threadClientCom = new ThreadComunicationServer(this, (Socket) object);
			threadClientCom.start();
		} else
		{
			System.err.println("Erreur serveur, treatIncome: mauvaise argument");
		}
	}

	@Override
	public void treatIncomeUDP(String message)
	{
		System.out.println(message);
		StringTokenizer token = new StringTokenizer(message, ":");
		String firstToken = token.nextToken();
		switch (firstToken)
		{
		case "alive":
			if (token.hasMoreElements())
			{
				String key = token.nextToken();
				if (key.length() > 20)
				{
					clientTTL.put(key, 10);
				}
			}
			break;

		default:
			break;
		}
	}

	public static void main(String[] args)
	{
		Server server = new Server();
		server.launch();
	}
}
