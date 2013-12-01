package clientServer;

import java.util.Vector;

/**
 * 
 * @author Dorian, Mickaël, Raphaël, Thibaultl
 * Classe abstraite permettant de representer un client ou un serveur
 *           suivant son instance.
 */
public abstract class AbstractClientServer
{
	/**
	 * Liste des clients que connait la classe.
	 * 
	 * @see ClientServerData
	 */
	private Vector<ClientServerData> clients;

	/**
	 * Constructeur par défaut. Initialise la liste des clients.
	 */
	public AbstractClientServer()
	{
		this.clients = new Vector<ClientServerData>();
	}

	/**
	 * Constructeur de la classe, la liste des clients est initialisées par la
	 * liste fourni en parametre. La liste n'est pas clonée.
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
	 * Méthode permettant de traiter les éléments reçu en TCP
	 * 
	 * @param object
	 */
	public abstract void treatIncomeTCP(Object object);

	/**
	 * Méthode permettant de traiter les éléments reçu en UDP
	 * 
	 * @param message
	 */
	public abstract void treatIncomeUDP(String message);
}
