package network;

import java.net.InetAddress;

/**
 * Classe abstraite permettant de représenter un protocol de
 *           communication.
 *           
 * @author Dorian, Mickaël, Raphaël, Thibault
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
	 * @param message message que l'on souhaite envoyer
	 * @param adress adresse de destination
	 * @param port port de destination
	 */
	public abstract void sendMessage(String message, InetAddress adress, int port);

	/**
	 * Méthode permettant d'envoyer un message à l'adresse par defaut du
	 * protocol.
	 * 
	 * @param message message que l'on souhaite envoyer
	 */
	public abstract void sendMessage(String message);

	/**
	 * Méthode permettant de réceptionner un message.
	 * 
	 * @return Chaine réceptionnée
	 */
	public abstract String readMessage();

	/**
	 * Méthode permettant de gérer la fermeture du protocol.
	 */
	public abstract void close();

	/**
	 * Getter du port local
	 * 
	 * @return localPort Port local
	 */
	public int getLocalPort()
	{
		return localPort;
	}

	/**
	 * Setter qui fixe le port local
	 * 
	 * @param localPort port de communication
	 *           
	 */
	public void setLocalPort(int localPort)
	{
		this.localPort = localPort;
	}

}
