package model;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/** 
 *         Thread d'écoute TCP du serveur. Permet de recevoir les connections des
 *         clients. Si un client se connecte, un nouveau thread est lancé pour
 *         communiquer avec. Sinon cela voudrait dire que le serveur est bloqué
 *         tant que le client n'a pas fini ce qu'il voulait faire. Du coup cela
 *         permet au serveur de dialoguer avec plusieurs clients en même temps.
 *         
 *         @author Dorian, Mickaël, Raphaël, Thibault
 * 
 */
public class ThreadListenerTCP extends Thread
{
	/**
	 * Client/Serveur à qui appartient le thread.
	 * 
	 * @see AbstractClientServer
	 */
	private AbstractClientServer clientServer;
	/**
	 * Socket de réception.
	 */
	private ServerSocket socket;
	/**
	 * Paramètre permettant d'arrêter le Thread.
	 */
	private boolean running;

	/**
	 * Constructeur du ThreadListener TCP
	 * 
	 * @param server
	 *            serveur lancant le thread
	 * @param port
	 *            numero de port d'écoute
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
			System.out.println("Lancement du Thread d'ecoute TCP");
			this.running = true;
			while (running)
			{
				// On attend une connection client
				Socket socketClient = this.socket.accept();
				// Si on a une connection avec un client
				// On lance un thread de discutions avec le client
				this.clientServer.treatIncomeTCP(socketClient);
			}
			System.out.println("Arret du Thread d'ecoute");
			this.socket.close();
		} catch (Exception e)
		{
			if (running)
			{
				System.err.println("Erreur du ThreadListenerTCP, message: " + e.getMessage());
			}
		}
	}

	/**
	 * Méthode permettant d'arrêter le thread proprement
	 */
	public void stopThread()
	{
		try
		{
			this.socket.close();
		} catch (IOException e)
		{
			System.err.println("Erreur ThreadListenerTCP, erreur de fermerture du socket, message : " + e.getMessage());
			// e.printStackTrace();
		}
		this.running = false;
	}

	/**
	 * Getter du socket du thread
	 * 
	 * @return le socket utilisé
	 */
	public ServerSocket getSocket()
	{
		return socket;
	}

	/**
	 * Setter qui fixe le socket du thread
	 * 
	 * @param socket
	 *            le socket que l'on souhaite utilisé pour le thread
	 */
	public void setSocket(ServerSocket socket)
	{
		this.socket = socket;
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
