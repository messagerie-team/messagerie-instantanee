package clientServer;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Vector;
import dataLink.Protocol;

public class ClientDialog
{
	private Client client;
	private String idDialog;
	private Vector<ClientServerData> clients;
	private Protocol protocol;
	private String dialogue;
	private String lastMessage;

	public ClientDialog(String idDialog, Client client, Protocol protocol)
	{
		this.client = client;
		this.idDialog = idDialog;
		this.clients = new Vector<ClientServerData>();
		this.protocol = protocol;
		this.dialogue = "";
		this.lastMessage = "";
	}

	public ClientDialog(Client client, Protocol protocol)
	{
		this.client = client;
		SecureRandom random = new SecureRandom();
		this.idDialog = new BigInteger(130, random).toString(32);
		this.clients = new Vector<ClientServerData>();
		this.protocol = protocol;
		this.dialogue = "";
	}

	public void addMessage(String message)
	{
		this.dialogue += "\n" + message;
		this.lastMessage = message;
	}

	public void sendMessage(String message)
	{
		for (ClientServerData client : this.clients)
		{
			this.protocol.sendMessage("dialog:message:" + this.idDialog + ":" + this.client.getName() + ">" + message, client.getIp(), client.getPort());
		}
		this.addMessage("moi>" + message);
	}

	public String receiveMessage(String message)
	{
		System.out.println(this.idDialog + "->" + message);
		this.addMessage(message);
		return message;
	}

	public boolean addClient(ClientServerData client)
	{
		return this.clients.add(client);
	}

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
}
