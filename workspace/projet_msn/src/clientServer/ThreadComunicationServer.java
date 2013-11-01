package clientServer;

import java.net.Socket;

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
		}
	}

	private void messageTraitement(String message)
	{
		switch (message)
		{
		case "request:register":
			this.registerClient();
			break;
		case "request:unregister":
			this.unregisterClient();
			break;
		case "end":
			this.stopThread();
			break;

		default:
			break;
		}
	}

	private void unregisterClient()
	{
		this.server.removeClient(this.socket.getLocalAddress());
	}

	private void registerClient()
	{
		// On envoie un message pour dire que on a bien recu son message
		this.protocol.sendMessage("OK");
		// On envoie un autre pour demander son nom
		this.protocol.sendMessage("request:name");
		// On attend la reponse
		String name = this.protocol.readMessage();
		// On informe le client qu'on a bien reçu son nom
		this.protocol.sendMessage("reply:name.ok");
		// TODO gestion des mots de passe
		// TODO gestion de l'ip hors reseau local

		// On enregitre donc le client dans le serveur pour mettre à jour sa
		// base de client en ligne
		this.server.addClient(name, this.socket);
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
