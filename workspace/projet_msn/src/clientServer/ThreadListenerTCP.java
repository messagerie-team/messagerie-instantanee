package clientServer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 
 * @author Dorian, Mickaël, Raphaël, Thibault
 * 
 *         Thread d'écoute du serveur. Permet de recevoir les connections des
 *         clients. Si un client se connecte, un nouveau thread est lancé pour
 *         communiquer avec. Sinon cela voudrait dire que le serveur est bloqué
 *         tant que le client n'a pas fini ce qu'il voulait faire. Du coup cela
 *         permet au serveur de dialoguer avec plusieurs clients en même temps.
 * 
 */
public class ThreadListenerTCP extends Thread
{
	/**
	 * Client/Serveur a qui appartient le thread.
	 * 
	 * @see AbstractClientServer
	 */
	private AbstractClientServer clientServer;
	/**
	 * Socket de reception.
	 */
	private ServerSocket socket;
	/**
	 * Paramètre permettant d'arrêter le Thread.
	 */
	private boolean running;

	/**
	 * Construct ThreadListener
	 * 
	 * @param server
	 *            server lancant le thread
	 * @param port
	 *            numero de port d'ecoute
	 */
	public ThreadListenerTCP(AbstractClientServer server, int port)
	{
		try
		{
			this.clientServer = server;
			this.socket = new ServerSocket(port);
			System.out.println(this.socket.getInetAddress());
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
				// On lance un thread de discutions avec le client
				this.clientServer.treatIncomeTCP(socketClient);
				// ThreadComunicationServer threadClientCom = new
				// ThreadComunicationServer(this.server, socketClient);
				// threadClientCom.start();
			}
			System.out.println("Arret du Thread d'ecoute");
			this.socket.close();
		} catch (Exception e)
		{
			System.err.println("Erreur du ThreadListenerTCP, message: " + e.getMessage());
		}
	}

	/**
	 * Méthode permettant d'arrêter le thread proprement
	 */
	public void stopThread()
	{
		Thread.interrupted();
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
