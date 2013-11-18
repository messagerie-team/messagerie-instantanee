package clientServer;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;
import java.util.Vector;

import dataLink.Protocol;
import dataLink.ProtocolUDP;

public class ClientDialog
{
	private String idDialog;
	private Vector<ClientServerData> clients;
	private Protocol protocol;
	private String dialogue;
	private String lastMessage;

	public ClientDialog(String idDialog, Protocol protocol)
	{
		this.idDialog = idDialog;
		this.clients = new Vector<ClientServerData>();
		this.protocol = protocol;
		this.dialogue = "";
		this.lastMessage = "";
	}

	public ClientDialog(Protocol protocol)
	{
		SecureRandom random = new SecureRandom();
		Random ran = new Random();
		int nb = ran.nextInt();
		// this.idDialog = new BigInteger(130, random).toString(32);
		this.idDialog = nb * nb + "";
		this.clients = new Vector<ClientServerData>();
		this.protocol = protocol;
		this.dialogue = "";
	}

	private void addMessage(String message)
	{
		this.dialogue += "\n" + message;
		this.lastMessage = message;
	}

	public void sendMessage(String message)
	{
		for (ClientServerData client : this.clients)
		{
			this.protocol.sendMessage("dialog:message:" + this.idDialog + ":>" + message, client.getIp(), client.getPort());
		}
		this.addMessage(message);
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
