package clientServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Thread d'ecoute du serveur. Permet de recevoir les connections des clients.
 * Si un clients se connecte, un nouveau thread est lancé pour communiquer avec.
 * Sinon cela voudrais dire que le serveur est bloquer tant que le client n'a
 * pas fini ce qu'il voulais faire. Du coup cela permet au serveur de dialoguer
 * avec plusieur client en mème temps.
 * 
 * @author raphael
 * 
 */
public class ThreadListener extends Thread
{
	// Serveur a qui appartient le thread d'ecoute
	private Server server;
	// Socket permettant le dialogue
	private ServerSocket socket;
	// Boolean permettant de stopper le Thread
	private boolean running;

	/**
	 * Construct ThreadListener
	 * 
	 * @param server
	 *            server lancant le thread
	 * @param port
	 *            numero de port d'ecoute
	 */
	public ThreadListener(Server server, int port)
	{
		try
		{
			this.server = server;
			this.socket = new ServerSocket(port);
			this.running = false;
		} catch (IOException e)
		{
			System.err.println("Erreur initialisation serveur, message: " + e.getMessage());
		}
	}

	public void run()
	{
		try
		{
			System.out.println("Lancement du Thread d'ecoute");
			this.running = true;
			while (running)
			{
				// On attend une connection client
				Socket socketClient = this.socket.accept();
				// Si on a une connection avec un client
				// On lance un thread de discution avec le client
				ThreadComunicationServer threadClientCom = new ThreadComunicationServer(this.server, socketClient);
				threadClientCom.start();
			}
			System.out.println("Arret du Thread d'ecoute");
			this.socket.close();
		} catch (Exception e)
		{
			System.err.println("Erreur du ThreadListener, message: " + e.getMessage());
		}
	}

	/**
	 * Method stopThread(). Methode permettant de stopper le thread proprement
	 */
	public void stopThread()
	{
		this.running = false;
	}

	public ServerSocket getSocket()
	{
		return socket;
	}

	public void setSocket(ServerSocket socket)
	{
		this.socket = socket;
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
