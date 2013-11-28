/**
 * 
 */
package clientServer;

import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;

import dataLink.Protocol;
import dataLink.ProtocolUDP;

/**
 * @author Mickael
 * @category Class permetant de representer un serveur. Extends de la class
 *           AbstractClientServer.
 * @see AbstractClientServer
 */
public class Server extends AbstractClientServer
{
	/**
	 * Protocol permettant au serveur de communiquer via le threadListernerUDP.
	 * 
	 * @see Protocol
	 */
	private Protocol protocol;
	/**
	 * Thread TCP permettant au serveur de recevoir les connexions TCP.
	 * 
	 * @see ThreadListenerTCP
	 */
	private ThreadListenerTCP threadListenerTCP;
	/**
	 * Thread UDP permettant au serveur de revevoir des paquets UDP.
	 * 
	 * @see ThreadListenerUDP
	 */
	private ThreadListenerUDP threadListenerUDP;
	/**
	 * Liste des TTL clients permettant de gerer la deconexion si un client ne
	 * donne plus signe de vie.
	 */
	private HashMap<ClientServerData, Integer> clientTTL;
	/**
	 * Parametre permetant de savoir si le serveur est en train de tourner.
	 */
	private boolean running;

	/**
	 * Construct Server() Constructeur le la class Server. Initialise les
	 * variables server,clients et threadListener. En ouvrant sur le port TCP
	 * 30970 et le port UDP 30971.
	 */
	public Server()
	{
		super();
		this.protocol = new ProtocolUDP(30971);
		this.threadListenerTCP = new ThreadListenerTCP(this, 30970);
		this.threadListenerUDP = new ThreadListenerUDP(this, this.protocol);
		this.clientTTL = new HashMap<ClientServerData, Integer>();
	}

	/**
	 * Construct Server(int port) Constructeur de la class Server. Initialise
	 * les variables server,clients et threadListener.
	 * 
	 * @param int numero de port TCP, le port UDP sera a +1
	 */
	public Server(int port)
	{
		super();
		this.protocol = new ProtocolUDP(port + 1);
		this.threadListenerTCP = new ThreadListenerTCP(this, port);
		this.threadListenerUDP = new ThreadListenerUDP(this, this.protocol);
		this.clientTTL = new HashMap<ClientServerData, Integer>();
	}

	/**
	 * Method launch() Méthode permettant de lancer le serveur.
	 */
	public void launch()
	{
		this.running = true;
		this.threadListenerTCP.start();
		this.threadListenerUDP.start();
		// Thread de mise à jour des TTL Clients
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				while (running)
				{
					try
					{
						Thread.sleep(1000);
					} catch (InterruptedException e)
					{

					}
					// Set<String> keys = clientTTL.keySet();
					Vector<ClientServerData> copyClients = new Vector<ClientServerData>(getClients());
					for (ClientServerData client : copyClients)
					{
						int ttl = clientTTL.get(client);
						System.out.println(client.getId() + " ttl:" + ttl);
						clientTTL.put(client, ttl - 1);
						if ((ttl - 1) < 0)
						{
							if (removeClient(client))
							{
								clientTTL.remove(client);
							}
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
		this.running = false;
		this.threadListenerTCP.stopThread();
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
		ClientServerData newClient = new ClientServerData(name, client.getInetAddress(), listeningUDPPort);
		if (this.getClients().add(newClient))
		{
			new Thread(new Runnable()
			{

				@Override
				public void run()
				{
					try
					{
						Thread.sleep(3000);
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
			this.clientTTL.put(newClient, 10);
			return newClient.getId();
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
		// this.clientTTL.remove(client.getId());
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

	/**
	 * Methode permettant de recuperer la liste des clients que connait le
	 * serveur. Cette methode est utiliser pour renvoyer une liste de clients
	 * aux clients.
	 * 
	 * @return chaine client sous la forme
	 *         "ClePublic-NomCLient,ClePublic-NomClient...."
	 */
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

	/**
	 * Methode permetant d'envoyer une liste de clients à un client.
	 * 
	 * @param client
	 */
	public void sendListClient(ClientServerData client)
	{
		String listClient = this.getListClient();
		protocol.sendMessage("listClient:" + listClient, client.getIp(), client.getPort());
	}

	/**
	 * Methode permettant de recuperer les informations d'un client.
	 * 
	 * @param id
	 * @return chaine sous la forme
	 *         "ClePublic,NomClient,IpClient,PortEcouteClient"
	 */
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
		return threadListenerTCP;
	}

	public void setThreadListener(ThreadListenerTCP threadListener)
	{
		this.threadListenerTCP = threadListener;
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
				ClientServerData client = null;
				for (ClientServerData clientD : this.getClients())
				{
					if (key.trim().equals(clientD.getId().trim()))
					{
						client = clientD;
					}
				}
				if (client != null)
				{
					if (key.length() > 20)
					{
						clientTTL.put(client, 10);
					}
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
