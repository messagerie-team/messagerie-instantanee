package clientServer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

import dataLink.Protocol;
import dataLink.ProtocolTCP;

/**
 * Thread de comunication d'un client vers un serveur. Il permet de gerer les
 * demandes client. Connection, Deconnection, demande de lien etc...
 * 
 * @author raphael
 * 
 */
public class ThreadComunicationClient extends Thread
{
	/**
	 * Client a qui appartient le thread.
	 * 
	 * @see Client
	 */
	private Client client;
	/**
	 * Socket de communication.
	 * 
	 * @see Socket
	 */
	private Socket socket;
	/**
	 * Protocol permettant de communiquer.
	 * 
	 * @see Protocol
	 */
	private Protocol protocol;
	/**
	 * Parametre permettant d'areter le thread.
	 */
	private boolean running;
	/**
	 * Adresse ip du serveur.
	 * 
	 * @see Server
	 */
	private String ipServer;

	/**
	 * Constructeur par defaut de thread de communication.
	 * 
	 * @param client
	 * @param ipServer
	 */
	public ThreadComunicationClient(Client client, String ipServer)
	{
		this.client = client;
		this.ipServer = ipServer;
	}

	/**
	 * Constructeur de thread de communication. Pas encore utilise.
	 * 
	 * @param client
	 * @param socket
	 * @param ipServer
	 */
	public ThreadComunicationClient(Client client, Socket socket, String ipServer)
	{
		this.client = client;
		this.socket = socket;
		this.ipServer = ipServer;
		this.protocol = new ProtocolTCP(socket);
	}

	@Override
	public void run()
	{
		try
		{
			System.out.println(this.ipServer);
			this.socket = new Socket(this.ipServer, 30970);
			this.protocol = new ProtocolTCP(socket);
		} catch (Exception e)
		{
			System.err.println("Erreur du ThreadComunicationClient,Connexion, message: " + e.getMessage());
		}
		try
		{
			System.out.println("Lancement du Thread de communication");
			this.running = true;
			while (running)
			{
				Thread.sleep(500);
				// On attent que le serveur nous envoie un message
				String message = protocol.readMessage();
				// On traite ensuite le message reçu.
				this.messageTraitement(message);
			}
			System.out.println("Arret du Thread de communication");
			this.socket.close();
			this.protocol.close();
		} catch (IOException | InterruptedException e)
		{
			System.err.println("Erreur du ThreadComunicationClient, message: " + e.getMessage());
		}
	}

	/**
	 * Methode permettant de traiter la reception d'un message.
	 * 
	 * @param message
	 */
	private void messageTraitement(String message)
	{
		System.out.println("Début du traitement du message : " + message);
		StringTokenizer token = new StringTokenizer(message, ":");
		String firstToken = token.nextToken();
		if (token.hasMoreTokens())
		{
			String nextToken = token.nextToken();
			switch (firstToken)
			{
			case "request":
				this.messageTraitementRequest(nextToken, token);
				break;
			case "reply":
				this.messageTraitementReply(nextToken, token);
				break;
			case "end":
				this.stopThread();
				break;

			default:
				this.stopThread();
				break;
			}
		}
	}

	/**
	 * Methode permettant de traiter les request d'un serveur.
	 * 
	 * @see Server
	 * @param message
	 * @param token
	 */
	private void messageTraitementRequest(String message, StringTokenizer token)
	{
		switch (message)
		{
		case "register":
			this.registerClient(token);
			break;
		case "unregister":
			this.unregisterClient(token);
			break;
		case "clientConnection":
			this.getClientConnection(token);
			break;

		default:
			break;
		}
	}

	/**
	 * Message permettant de traiter les reply d'un serveur.
	 * 
	 * @see Server
	 * @param message
	 * @param token
	 */
	private void messageTraitementReply(String message, StringTokenizer token)
	{
		switch (message)
		{
		case "register":
			this.registerClient(token);
			break;
		case "unregister":
			this.unregisterClient(token);
			break;
		case "list":
			this.askListClient(token);
			break;
		case "clientConnection":
			this.getClientConnection(token);
			break;

		default:
			break;
		}
	}

	/**
	 * Methode permettant traiter le procesus de desenregistrement
	 * 
	 * @param token
	 */
	public void unregisterClient(StringTokenizer token)
	{
		if (token.hasMoreTokens())
		{
			String nextToken = token.nextToken();
			switch (nextToken)
			{
			case "DONE":
				this.client.setId("");
				this.stopThread();
				break;

			default:
				this.stopThread();
				break;
			}
		} else
		{
			this.protocol.sendMessage("request:unregister:" + this.client.getId());
		}
	}

	/**
	 * Methode permettant de gerer le procesus d'enregistrement.
	 * 
	 * @param token
	 */
	public void registerClient(StringTokenizer token)
	{
		if (token.hasMoreTokens())
		{
			String nextToken = token.nextToken();
			switch (nextToken)
			{
			case "name":
				if (!token.hasMoreTokens())
				{
					this.protocol.sendMessage("reply:register:name:" + this.client.getName());
				}
				break;
			case "port":
				if (!token.hasMoreTokens())
				{
					this.protocol.sendMessage("reply:register:port:" + this.client.getListeningUDPPort());
				}
				break;
			case "id":
				if (token.hasMoreTokens())
				{
					this.client.setId(token.nextToken());
					this.protocol.sendMessage("reply:register:id:OK");
				}
				break;
			case "DONE":
				this.stopThread();
				break;
			default:
				this.stopThread();
				break;
			}
		} else
		{
			this.protocol.sendMessage("request:register");
		}
	}

	/**
	 * Methode permettant de gerer le procesus de demande de list Client au
	 * serveur.
	 * 
	 * @param token
	 */
	public void askListClient(StringTokenizer token)
	{
		if (token.hasMoreTokens())
		{
			String list = token.nextToken();
			this.client.addClientList(list);
			this.protocol.sendMessage("reply:list:DONE");
			this.stopThread();
		} else
		{
			this.protocol.sendMessage("request:list");
		}
	}

	/**
	 * Methode permettant de gerer le procesus de demande d'information de
	 * connexion client.
	 * 
	 * @param token
	 */
	public void getClientConnection(StringTokenizer token)
	{
		if (token.hasMoreTokens())
		{
			// System.out.println(token.nextToken());
			String[] elements = token.nextToken().split(",");
			if (elements.length == 4)
			{
				ClientServerData client;
				try
				{
					client = new ClientServerData(elements[0], elements[1], InetAddress.getByName(elements[2]), Integer.parseInt(elements[3]));

					boolean add = this.client.getClients().add(client);
					System.out.println("Ajout du client recu");
					if (add)
					{
						protocol.sendMessage("reply:clientConnection:DONE");
						this.stopThread();
					} else
					{
						protocol.sendMessage("reply:clientConnection:ERROR");
						this.stopThread();
					}
				} catch (NumberFormatException | UnknownHostException e)
				{
					e.printStackTrace();
				}
			} else
			{
				if (elements[0].equals("ERROR"))
				{
					this.stopThread();
				}
			}
		}
	}

	/**
	 * Methode permettant d'envoyer une demande d'information client au serveur.
	 * 
	 * @param clientId
	 */
	public void getClientConnection(String clientId)
	{
		this.protocol.sendMessage("request:clientConnection:" + clientId);
	}

	/**
	 * Methode permettant de stopper le thread
	 */
	public void stopThread()
	{
		this.running = false;
	}

	public Socket getSocket()
	{
		return socket;
	}

	public void setSocket(Socket socket)
	{
		this.socket = socket;
	}

	public Protocol getProtocol()
	{
		return protocol;
	}

	public void setProtocol(ProtocolTCP protocol)
	{
		this.protocol = protocol;
	}

	public boolean isRunning()
	{
		return running;
	}

	public void setRunning(boolean running)
	{
		this.running = running;
	}
}
