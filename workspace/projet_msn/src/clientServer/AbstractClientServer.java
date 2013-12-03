package clientServer;

import java.util.ArrayList;

/**
 * Classe abstraite permettant de représenter un client ou un serveur
 *           suivant son instance.
 * @author Dorian, Mickaël, Raphaël, Thibault
 */
public abstract class AbstractClientServer
{
	/**
	 * Liste des clients que connait la classe.
	 * 
	 * @see ClientServerData
	 */
	private ArrayList<ClientServerData> clients;

	/**
	 * Constructeur par défaut. Initialise la liste des clients.
	 */
	public AbstractClientServer()
	{
		this.clients = new ArrayList<ClientServerData>();
	}

	/**
	 * Constructeur, la liste des clients est initialisées par la
	 * liste fourni en paramètre. La liste n'est pas clonée.
	 * 
	 * @param clients liste des clients
	 * @see ClientServerData
	 */
	public AbstractClientServer(ArrayList<ClientServerData> clients)
	{
		this.clients = clients;
	}

	/**
	 * Getter qui retourne la liste de Client
	 * 
	 * @return ArrayList liste des clients connue des sous classes
	 */
	public ArrayList<ClientServerData> getClients()
	{
		return clients;
	}
	/**
	 * Setter qui fixe la liste de clients
	 * 
	 * @param clients liste de client
	 */
	public void setClients(ArrayList<ClientServerData> clients)
	{
		this.clients = clients;
	}

	/**
	 * Méthode permettant de traiter les éléments reçu en TCP
	 * 
	 * @param object paquet reçu en TCP
	 */
	public abstract void treatIncomeTCP(Object object);

	/**
	 * Méthode permettant de traiter les éléments reçu en UDP
	 * 
	 * @param message paquet reçu en UDP
	 */
	public abstract void treatIncomeUDP(String message);
}
