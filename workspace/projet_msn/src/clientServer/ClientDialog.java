package clientServer;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Vector;
import dataLink.Protocol;

/**
 * 
 * @author raphael
 * @category Class permetant de representer un dialogue entre clients.
 */
public class ClientDialog
{
	/**
	 * Client a qui appartient le dialogue
	 * 
	 * @see Client
	 */
	private Client client;
	/**
	 * Identifiant du dialogue. Cle permettant d'identifier le dialogue de façon
	 * unique.
	 */
	private String idDialog;
	/**
	 * Liste des clients avec qui le client proprietaire dialogue
	 * 
	 * @see ClientServerData
	 */
	private Vector<ClientServerData> clients;
	/**
	 * Protocol permettant au dialogue de communiquer.
	 * 
	 * @see Protocol
	 */
	private Protocol protocol;
	/**
	 * Ensemble des messages echanges
	 */
	private String dialogue;
	/**
	 * Dernier message echange
	 */
	private String lastMessage;
	/**
	 * Attribut permettant de savoir si la conversation est encore active.
	 */
	private boolean inUse;

	/**
	 * Constructeur permetant de creer un dialogue. Cette methode est utilise
	 * par le client lors de la creation d'un dialogue de sa part. Une cle
	 * unique est genere par constructeur pour identifier le dialogue.
	 * 
	 * @param client
	 * @param protocol
	 */
	public ClientDialog(Client client, Protocol protocol)
	{
		this.client = client;
		SecureRandom random = new SecureRandom();
		this.idDialog = new BigInteger(130, random).toString(32);
		this.clients = new Vector<ClientServerData>();
		this.protocol = protocol;
		this.dialogue = "";
		this.inUse = true;
	}

	/**
	 * Constructeur permettant de creer un dialogue. Cette methode est utilise
	 * par le client lorsqu'il reçoit une notification de dialogue par un autre
	 * client. Le premier parametre correspond a la cle unique du dialogue qui a
	 * du etre reçu.
	 * 
	 * @param idDialog
	 * @param client
	 * @param protocol
	 */
	public ClientDialog(String idDialog, Client client, Protocol protocol)
	{
		this.client = client;
		this.idDialog = idDialog;
		this.clients = new Vector<ClientServerData>();
		this.protocol = protocol;
		this.dialogue = "";
		this.lastMessage = "";
		this.inUse = true;
	}

	/**
	 * Methode permettant d'ajouter un message au dialogue
	 * 
	 * @param message
	 */
	public void addMessage(String message)
	{
		this.dialogue += "\n" + message;
		this.lastMessage = message;
	}

	/**
	 * Methode permettant d'envoyer un message à tout les clients du dialogue.
	 * 
	 * @param message
	 */
	public void sendMessage(String message)
	{
		this.inUse = true;
		for (ClientServerData client : this.clients)
		{
			this.protocol.sendMessage("dialog:message:" + this.idDialog + ":" + this.client.getName() + ">" + message, client.getIp(), client.getPort());
		}
		this.addMessage("moi>" + message);
	}

	/**
	 * Methode permettant de gerer la reception d'un message
	 * 
	 * @param message
	 * @return le message reçu
	 */
	public String receiveMessage(String message)
	{
		System.out.println(this.idDialog + "->" + message);
		this.inUse = true;
		this.addMessage(message);
		return message;
	}

	/**
	 * Methode permettant d'ajouter un client au dialogue
	 * 
	 * @param client
	 * @return true si le client est bien ajoute, false sinon.
	 */
	public boolean addClient(ClientServerData client)
	{
		boolean alreadyInDialog = false;
		for (ClientServerData clientL : this.clients)
		{
			if (client.equals(clientL))
			{
				alreadyInDialog = true;
			}
		}
		if (!alreadyInDialog && !this.client.equals(client))
		{
			return this.clients.add(client);
		}
		return false;
	}

	/**
	 * Methode permettant de supprimer un client du dialogue.
	 * 
	 * @param client
	 * @return
	 */
	public boolean removeClient(ClientServerData client)
	{
		return this.clients.remove(client);
	}

	public String getIdDialog()
	{
		return idDialog;
	}

	public void setIdDialog(String idDialog)
	{
		this.idDialog = idDialog;
	}

	public Vector<ClientServerData> getClients()
	{
		return clients;
	}

	public void setClients(Vector<ClientServerData> clients)
	{
		this.clients = clients;
	}

	public Protocol getProtocol()
	{
		return protocol;
	}

	public void setProtocol(Protocol protocol)
	{
		this.protocol = protocol;
	}

	public String getDialogue()
	{
		return dialogue;
	}

	public void setDialogue(String dialogue)
	{
		this.dialogue = dialogue;
	}

	public String getLastMessage()
	{
		return lastMessage;
	}

	public void setLastMessage(String lastMessage)
	{
		this.lastMessage = lastMessage;
	}

	public boolean isInUse()
	{
		return inUse;
	}

	public void setInUse(boolean inUse)
	{
		this.inUse = inUse;
	}
}
