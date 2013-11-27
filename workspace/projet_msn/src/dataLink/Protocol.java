package dataLink;

import java.net.InetAddress;

/**
 * 
 * @author raphael
 * @category Class abstraite permettant de representer un protocol de
 *           communication.
 */
public abstract class Protocol
{
	/**
	 * Port de communication du protocol.
	 */
	private int localPort;

	/**
	 * Constructeur par defaut du Protocol.
	 * 
	 * @param localPort
	 */
	public Protocol(int localPort)
	{
		this.localPort = localPort;
	}

	/**
	 * Methode permettant d'envoyer un message a une adresse sur un port.
	 * 
	 * @param message
	 * @param adress
	 * @param port
	 */
	public abstract void sendMessage(String message, InetAddress adress, int port);

	/**
	 * Methode permettant d'envoyer un message à l'adresse par defaut du
	 * protocol.
	 * 
	 * @param message
	 */
	public abstract void sendMessage(String message);

	/**
	 * Methode permettant de receptionner un message.
	 * 
	 * @return Chaine receptionne
	 */
	public abstract String readMessage();

	/**
	 * Methode permettant de gerer la fermeture du protocol.
	 */
	public abstract void close();

	public int getLocalPort()
	{
		return localPort;
	}

	public void setLocalPort(int localPort)
	{
		this.localPort = localPort;
	}

}
