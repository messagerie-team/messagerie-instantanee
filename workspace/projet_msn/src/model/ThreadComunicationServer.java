package model;

import java.io.IOException;
import java.net.Socket;
import java.util.StringTokenizer;

import network.Protocol;
import network.ProtocolTCP;

/**
 *         Thread de comunication d'un serveur vers un client. Il permet de
 *         gérer les demandes client. Connection, Déconnection, demande de lien
 *         etc...
 *         
 *         @author Dorian, Mickaël, Raphaël, Thibault
 * 
 */
public class ThreadComunicationServer extends Thread
{
	/**
	 * Serveur à qui appartient le thread de communication.
	 * 
	 * @see Server
	 */
	private Server server;
	/**
	 * Socket de communication.
	 * 
	 * @see Socket
	 */
	private Socket socket;
	/**
	 * Protocol de communication.
	 * 
	 * @see Protocol
	 */
	private Protocol protocol;
	/**
	 * Paramètre permettant d'arrêter le thread.
	 */
	private boolean running;
	/**
	 * Variable temporaire utile au traitement des requêtes.
	 */
	private String tempVar;

	/**
	 * Constructeur par défaut du Thread.
	 * 
	 * @param server
	 * @param socket
	 */
	public ThreadComunicationServer(Server server, Socket socket)
	{
		this.server = server;
		this.socket = socket;
		this.protocol = new ProtocolTCP(socket);
	}

	@Override
	public void run()
	{
		try
		{
			System.out.println("Lancement du Thread de communication");
			this.running = true;
			while (running)
			{
				Thread.sleep(500);
				// On attent que le client nous envoie un message
				String message = protocol.readMessage();
				// On traite ensuite le message reçu.
				this.messageTraitement(message);
			}
			System.out.println("Arret du Thread de communication");
			this.socket.close();
			this.protocol.close();
		} catch (IOException | InterruptedException e)
		{
			System.err.println("Erreur du ThreadComunicationServer, message: " + e.getMessage());
		}
	}

	/**
	 * Méthode permettant de traiter la réception d'un message.
	 * 
	 * @param message
	 */
	private void messageTraitement(String message)
	{
		StringTokenizer token = new StringTokenizer(message, ":");
		System.out.println("Début du traitement du message : " + message);
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
	 * Méthode permettant de traiter les demandes d'un serveur.
	 * {@link #messageTraitement(String)}
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
	 * Message permettant de traiter les réponses d'un serveur.
	 * {@link #messageTraitement(String)}
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
			this.stopThread();
			break;
		}
	}

	/**
	 * Méthode permettant traiter le processus de desenregistrement
	 * {@link #messageTraitementReply(String, StringTokenizer) #messageTraitementRequest(String, StringTokenizer)}
	 * @param token
	 */
	private void unregisterClient(StringTokenizer token)
	{
		if (token.hasMoreTokens())
		{
			String id = token.nextToken();
			this.server.removeClient(id);
			this.protocol.sendMessage("reply:unregister:DONE");
			this.stopThread();
		} else
		{
			this.protocol.sendMessage("reply:unregister:ERROR");
			this.stopThread();
		}
	}

	/**
	 * Méthode permettant de gérer le processus d'enregistrement.
	 * {@link #messageTraitementReply(String, StringTokenizer) #messageTraitementRequest(String, StringTokenizer)}
	 * @param token
	 */
	private void registerClient(StringTokenizer token)
	{
		// Si on a un élément de plus dans le token, alors il s'agit d'un reply
		if (token.hasMoreTokens())
		{
			String nextToken = token.nextToken();
			if (token.hasMoreTokens())
			{
				switch (nextToken)
				{
				case "name":
					// On informe le client qu'on a bien reçu son nom
					// this.protocol.sendMessage("reply:register:name:OK");
					this.tempVar = token.nextToken();
					if (!this.tempVar.trim().equals(""))
					{
						this.protocol.sendMessage("request:register:password");
					} else
					{
						this.protocol.sendMessage("reply:register:ERROR");
						this.stopThread();
					}
					break;
				case "password":
					// On informe le client qu'on a bien reçu son nom
					// this.protocol.sendMessage("reply:register:name:OK");
					String password = token.nextToken();

					if (!password.trim().equals(""))
					{
						if (this.server.verifyIDAndPassword(tempVar, password))
						{
							this.protocol.sendMessage("request:register:port");
						} else
						{
							this.server.registerClientInBase(tempVar, password);
							this.protocol.sendMessage("reply:register:ERROR");
						}
					} else
					{
						//this.protocol.sendMessage("reply:register:ERROR");
						this.stopThread();
					}
					break;
				case "port":
					if (token.hasMoreTokens())
					{
						String stringPort = token.nextToken();
						int port = Integer.parseInt(stringPort);
						String id = this.server.addClient(this.tempVar, this.socket, port);
						if (id != null)
						{
							this.protocol.sendMessage("reply:register:id:" + id);
						} else
						{
							this.protocol.sendMessage("reply:register:ERROR");
							this.stopThread();
						}
					} else
					{
						this.protocol.sendMessage("reply:register:ERROR");
					}
					break;
				case "id":
					this.protocol.sendMessage("reply:register:DONE");
					System.out.println(this.server.getClients());
					this.stopThread();
					break;
				default:
					this.stopThread();
					break;
				}
			} else
			{
				this.protocol.sendMessage("reply:register:ERROR");
				this.stopThread();
			}
		}
		// Sinon c'est qu'il s'agit d'une request
		else
		{
			System.out.println("Demande d'enregistrement");
			// On envoie un autre pour demander son nom
			this.protocol.sendMessage("request:register:name");
		}
	}

	/**
	 * Méthode permettant de gérer le processus de demande de list Client au
	 * serveur.
	 * {@link #messageTraitementReply(String, StringTokenizer) #messageTraitementRequest(String, StringTokenizer)}
	 * @param token
	 */
	public void askListClient(StringTokenizer token)
	{
		if (token.hasMoreTokens())
		{
			this.stopThread();
		} else
		{
			this.protocol.sendMessage("reply:list:" + this.server.getListClient());
		}
	}

	/**
	 * Méthode permettant de gérer le processus de demande d'information de
	 * connection client.
	 * {@link #messageTraitementReply(String, StringTokenizer) #messageTraitementRequest(String, StringTokenizer)}
	 * @param token
	 */
	public void getClientConnection(StringTokenizer token)
	{
		if (token.hasMoreTokens())
		{
			String nextToken = token.nextToken();
			if (nextToken.length() > 20)
			{
				String requested = this.server.getClient(nextToken);
				if (requested != null)
				{
					this.protocol.sendMessage("reply:clientConnection:" + requested);
				} else
				{
					this.protocol.sendMessage("reply:clientConnection:ERROR");
					this.stopThread();
				}
			} else
			{
				if (nextToken.equals("DONE"))
				{
					this.stopThread();
				} else if (nextToken.equals("ERROR"))
				{
					this.stopThread();
				} else
				{
					this.protocol.sendMessage("reply:clientConnection:ERROR");
					this.stopThread();
				}
			}
		} else
		{
			this.protocol.sendMessage("reply:clientConnection:ERROR");
			this.stopThread();
		}
	}

	/**
	 * Méthode permettant d'arrêter le thread
	 */
	public void stopThread()
	{
		this.running = false;
	}

	/**
	 * Getter du socket du thread
	 * 
	 * @return le socket utilisé
	 */
	public Socket getSocket()
	{
		return socket;
	}

	/**
	 * Setter qui fixe le socket du thread
	 * 
	 * @param socket
	 *            le socket que l'on souhaite utilisé pour le thread
	 */
	public void setSocket(Socket socket)
	{
		this.socket = socket;
	}

	/**
	 * Getter du protocol du thread
	 * 
	 * @return le protocol utilisé
	 */
	public Protocol getProtocol()
	{
		return protocol;
	}

	/**
	 * Setter qui fixe le protocol du thread
	 * 
	 * @param protocol
	 *            protocol que l'on souhaite utiliser
	 */
	public void setProtocol(Protocol protocol)
	{
		this.protocol = protocol;
	}

	/**
	 * Getter pour savoir si le thread est en route ou non
	 * 
	 * @return l'état du thread, vrai pour en marche, faux sinon
	 */
	public boolean isRunning()
	{
		return running;
	}

	/**
	 * Setter qui fixe si le thread est en exécution ou non
	 * 
	 * @param running
	 *            vrai pour le mettre en route faux sinon
	 */
	public void setRunning(boolean running)
	{
		this.running = running;
	}
}
