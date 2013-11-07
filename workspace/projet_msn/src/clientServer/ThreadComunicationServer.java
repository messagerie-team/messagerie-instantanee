package clientServer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.StringTokenizer;

import dataLink.Protocol;

/**
 * Thread de comunication avec un client. Il permet de gerer les demandes
 * client. Connection, Deconnection, demande de lien etc...
 * 
 * @author raphael
 * 
 */
public class ThreadComunicationServer extends Thread
{
	private Server server;
	private Socket socket;
	private Protocol protocol;
	private boolean running;

	public ThreadComunicationServer(Server server, Socket socket)
	{
		this.server = server;
		this.socket = socket;
		this.protocol = new Protocol(socket);
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
		} catch (Exception e)
		{
			System.err.println("Erreur du ThreadComunicationServer, message: " + e.getMessage());
			//e.printStackTrace();
		}
	}

	private void messageTraitement(String message)
	{
		System.out.println("Début du traitement du message : "+message);
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

	private void messageTraitementRequest(String message, StringTokenizer token)
	{
		System.out.println("Message request");
		switch (message)
		{
		case "register":
			this.registerClient(token);
			break;
		case "unregister":
			this.unregisterClient();
			break;

		default:
			break;
		}
	}

	private void messageTraitementReply(String message, StringTokenizer token)
	{
		System.out.println("Message reply");
		switch (message)
		{
		case "register":
			this.registerClient(token);
			break;
		case "unregister":
			this.unregisterClient();
			break;
		case "list":
			break;

		default:
			break;
		}
	}

	private void unregisterClient()
	{
		this.server.removeClient(this.socket.getLocalAddress());
		this.protocol.sendMessage("reply:unregister:DONE");
		this.stopThread();
	}

	private void registerClient(StringTokenizer token)
	{
		System.out.println("register");
		// Si on a un element de plus dans le token, alors il s'agit d'un reply
		if (token.hasMoreTokens())
		{
			String nextToken = token.nextToken();
			if (token.hasMoreTokens())
			{
				switch (nextToken)
				{
				case "name":
					// On informe le client qu'on a bien reçu son nom
					this.protocol.sendMessage("reply:register:name:OK");
					String name = token.nextToken();
					if (this.server.addClient(name, this.socket))
					{
						this.protocol.sendMessage("reply:register:DONE");
						System.out.println(this.server.getClients());
						this.stopThread();
					}
					break;

				default:
					break;
				}
			}
		}
		// Sinon c'est qu'il s'agit d'un request
		else
		{
			System.out.println("Demande d'enregistrement");
			try
			{
				PrintWriter out = new PrintWriter(this.socket.getOutputStream());
				out.println("test");
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// On envoie un message pour dire que on a bien recu son message
			this.protocol.sendMessage("reply:register:OK");
			// On envoie un autre pour demander son nom
			this.protocol.sendMessage("request:register:name");
		}
	}

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

	public void setProtocol(Protocol protocol)
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
