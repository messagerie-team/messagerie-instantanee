package clientServer;

import java.util.Vector;

/**
 * 
 * @author raphael
 * @category Class abstraite permettant de representer un client ou un serveur
 *           suivant son instance.
 */
public abstract class AbstractClientServer
{
	/**
	 * Liste des clients que connait la class.
	 * 
	 * @see ClientServerData
	 */
	private Vector<ClientServerData> clients;

	/**
	 * Constructeur par defaut. Initialise la list des clients.
	 */
	public AbstractClientServer()
	{
		this.clients = new Vector<ClientServerData>();
	}

	/**
	 * Constructeur de la class, la liste des clients est initialisé par la
	 * liste fournit en parametre. La liste n'est pas clonée.
	 * 
	 * @param clients
	 * @see ClientServerData
	 */
	public AbstractClientServer(Vector<ClientServerData> clients)
	{
		this.clients = clients;
	}

	public Vector<ClientServerData> getClients()
	{
		return clients;
	}

	public void setClients(Vector<ClientServerData> clients)
	{
		this.clients = clients;
	}

	/**
	 * Methode permettant de traiter les elements reçu en TCP
	 * 
	 * @param object
	 */
	public abstract void treatIncomeTCP(Object object);

	/**
	 * Methode permettant de traiter les elements reçu en UDP
	 * 
	 * @param message
	 */
	public abstract void treatIncomeUDP(String message);
}
