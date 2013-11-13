package clientServer;

import java.net.InetAddress;
import java.net.UnknownHostException;

import dataLink.Protocol;
import dataLink.ProtocolUDP;

public class ThreadListenerUDP extends Thread
{

	private AbstractClientServer clientServer;
	// Pourquoi 60000 ? Limite théorique = 65535, limite en IPv4 = 65507
	// private int port;
	// Boolean permettant de stopper le Thread
	private boolean running;
	private Protocol protocol;

	/**
	 * Constructeur de la classe ThreadListenerUDP. Il recoit le port d'écoute
	 * en paramètre.
	 * 
	 * @param port
	 */
	public ThreadListenerUDP(AbstractClientServer clientServer, Protocol protocol)
	{
		this.protocol = protocol;
		this.clientServer = clientServer;
	}

	/**
	 * Ce thread récéptionne les messages et les affiches dans la console.
	 */
	public void run()
	{
		this.running = true;

		try
		{
			while (this.running)
			{
				String message = protocol.readMessage();
				this.clientServer.treatIncomeUDP(message);
			}
			this.protocol.close();
		} catch (Exception e)
		{
			System.err.println("Erreur du ThreadListenerUDP, message: " + e.getMessage());
		}
	}

	public void stopThread()
	{
		try
		{
			protocol.sendMessage("", InetAddress.getByName("localhost"), this.protocol.getLocalPort());
		} catch (UnknownHostException e)
		{
			System.err.println("Erreur du ThreadListenerUDP, stopThread, message: " + e.getMessage());
		}
		this.running = false;
	}
}
