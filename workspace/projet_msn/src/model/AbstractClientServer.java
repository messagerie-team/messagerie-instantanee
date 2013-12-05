package model;

import java.util.ArrayList;

/**
 * Classe abstraite permettant de représenter un client ou un serveur.
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
	 * Constructeur avec un paramètre, la liste des clients.
	 * 
	 * @param clients liste des clients
	 * @see ClientServerData
	 */
	public AbstractClientServer(ArrayList<ClientServerData> clients)
	{
		this.clients = clients;
	}

	/**
	 * Getter qui retourne la liste des clients
	 * 
	 * @return ArrayList, Liste des clients connus des sous classes
	 */
	public ArrayList<ClientServerData> getClients()
	{
		return clients;
	}
	/**
	 * Setter qui fixe la liste des clients
	 * 
	 * @param clients Liste des clients
	 */
	public void setClients(ArrayList<ClientServerData> clients)
	{
		this.clients = clients;
	}

	/**
	 * Méthode permettant de traiter les éléments reçus en TCP
	 * 
	 * @param object Paquet reçus en TCP
	 */
	public abstract void treatIncomeTCP(Object object);

	/**
	 * Méthode permettant de traiter les éléments reçus en UDP
	 * 
	 * @param message Paquet reçus en UDP
	 */
	public abstract void treatIncomeUDP(String message);
}
