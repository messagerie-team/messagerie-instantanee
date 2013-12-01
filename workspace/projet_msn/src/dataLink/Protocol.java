package dataLink;

import java.net.InetAddress;

/**
 * 
 * @author Dorian, Mickaël, Raphaël, Thibault
 * Classe abstraite permettant de représenter un protocol de
 *           communication.
 */
public abstract class Protocol
{
	/**
	 * Port de communication du protocol.
	 */
	private int localPort;

	/**
	 * Constructeur par défaut du Protocol.
	 * 
	 * @param localPort
	 */
	public Protocol(int localPort)
	{
		this.localPort = localPort;
	}

	/**
	 * Méthode permettant d'envoyer un message à une adresse sur un port.
	 * 
	 * @param message
	 * @param adress
	 * @param port
	 */
	public abstract void sendMessage(String message, InetAddress adress, int port);

	/**
	 * Méthode permettant d'envoyer un message à l'adresse par defaut du
	 * protocol.
	 * 
	 * @param message
	 */
	public abstract void sendMessage(String message);

	/**
	 * Méthode permettant de réceptionner un message.
	 * 
	 * @return Chaine receptionne
	 */
	public abstract String readMessage();

	/**
	 * Méthode permettant de gérer la fermeture du protocol.
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
