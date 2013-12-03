package model;

import java.math.BigInteger;
import java.net.InetAddress;
import java.security.SecureRandom;

/**
 * 
 * Classe permettant de stocker les informations client pour la classe
 * {@link AbstractClientServer}. Comme son nom, son IP, son port ouvert etc...
 * Elle représente les informations que stock le serveur pour un client.
 * 
 * @author Dorian, Mickaël, Raphaël, Thibault
 */
public class ClientServerData
{
	/**
	 * Clé publique unique d'un client.
	 */
	private String id;
	/**
	 * Nom d'un client.
	 */
	private String name;
	/**
	 * Adresse ip du client.
	 */
	private InetAddress ip;
	/**
	 * Port d'écoute du client.
	 */
	private int port;

	/**
	 * Contructeur par défaut de ClientServerData. Ce constructeur génère une
	 * clé publique unique qui sera propre au client.
	 * 
	 * @param name nom du client
	 * @param ip ip du client
	 * @param port port d'écoute UDP du client
	 */
	public ClientServerData(String name, InetAddress ip, int port)
	{
		SecureRandom random = new SecureRandom();
		this.id = new BigInteger(130, random).toString(32);
		this.name = name;
		this.ip = ip;
		this.port = port;
	}

	/**
	 * Constructeur de ClientServerData. Il permet de construire entièrement une
	 * donnée client.
	 * 
	 * @param id clé publique du client
	 * @param name nom du client
	 * @param ip ip du client
	 * @param port port d'écoute UDP du client
	 */
	public ClientServerData(String id, String name, InetAddress ip, int port)
	{
		this.id = id;
		this.name = name;
		this.ip = ip;
		this.port = port;
	}

	/**
	 * Getter du nom du client
	 * 
	 * @return name le nom du client
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Setter qui fixe le nom du client
	 * 
	 * @param name nom du client
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * Getter de l'adresse IP
	 * 
	 * @return l'ip du client
	 */
	public InetAddress getIp()
	{
		return ip;
	}

	/**
	 * Setter qui fixe l'adresse IP du client
	 * 
	 * @param ip l'ip du client
	 */
	public void setIp(InetAddress ip)
	{
		this.ip = ip;
	}

	/**
	 * Getter du port d'écoute UDP du client
	 * 
	 * @return le port d'écoute UDP
	 */
	public int getPort()
	{
		return port;
	}

	/**
	 * Setter qui fixe le port UDP d'écoute du client
	 * 
	 * @param port port UDP
	 */
	public void setPort(int port)
	{
		this.port = port;
	}

	/**
	 * Getter de la clé publique du client
	 * 
	 * @return id la clé publique du client
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * Setter qui fixe la clé publique du client
	 * 
	 * @param id clé publique unique du client
	 */
	public void setId(String id)
	{
		this.id = id;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof ClientServerData))
		{
			return false;
		} else if (obj == this)
		{
			return true;
		} else if (!this.name.equals(((ClientServerData) obj).name))
		{
			return false;
		} else if (this.port != ((ClientServerData) obj).port)
		{
			return false;
		} else if (!this.ip.equals(((ClientServerData) obj).ip))
		{
			return false;
		} else if (!this.id.equals(((ClientServerData) obj).id))
		{
			return false;
		}
		return true;
	}

	@Override
	public String toString()
	{
		return "Client: " + this.name + " " + this.ip + " " + this.port;
	}
}
