package model;

import java.net.InetAddress;
import java.net.UnknownHostException;

import network.Protocol;

/**
 * 		   Thread d'écoute UDP du serveur. Permet de recevoir les messages des
 *         clients et les listes du serveur.
 * @author Dorian, Mickaël, Raphaël, Thibault
 *
 */

public class ThreadListenerUDP extends Thread
{
	/**
	 * Client/Serveur a qui appartient le thread.
	 * 
	 * @see AbstractClientServer
	 */
	private AbstractClientServer clientServer;
	/**
	 * Paramètre permettant d'arrêter le Thread.
	 */
	private boolean running;
	/**
	 * Protocol de communication.
	 */
	private Protocol protocol;

	/**
	 * Constructeur de la classe ThreadListenerUDP. Il recoit le port d'écoute
	 * en paramètre.
	 * 
	 * @param clientServer
	 * 
	 * @param protocol
	 */
	public ThreadListenerUDP(AbstractClientServer clientServer, Protocol protocol)
	{
		this.protocol = protocol;
		this.clientServer = clientServer;
	}

	/**
	 * Ce thread récéptionne les messages et les affiche dans la console.
	 */
	public void run()
	{
		this.running = true;

		try
		{
			while (this.running)
			{
				String message = protocol.readMessage();
				if (message != null && !message.equals(""))
				{
					this.clientServer.treatIncomeUDP(message);
				}
			}
			this.protocol.close();
		} catch (Exception e)
		{
			System.err.println("Erreur du ThreadListenerUDP, message: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Méthode permettant d'arrêter le thread proprement
	 */
	public void stopThread()
	{
		try
		{
			//On s'auto envoie un message pour stopper le thread
			protocol.sendMessage("", InetAddress.getByName("localhost"), this.protocol.getLocalPort());
		} catch (UnknownHostException e)
		{
			System.err.println("Erreur du ThreadListenerUDP, stopThread, message: " + e.getMessage());
		}
		this.running = false;
	}
}
