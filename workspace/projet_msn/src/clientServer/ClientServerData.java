package clientServer;

import java.net.InetAddress;

/**
 * Class permettant de stocker les informations client. Comme son nom, son IP,
 * son port ouvert etc... Elle represente les informations que stock le serveur
 * pour un client.
 * 
 * @author raphael
 * 
 */
public class ClientServerData
{
	private String name;
	private InetAddress ip;
	private int port;

	public ClientServerData(String name, InetAddress ip, int port)
	{
		this.name = name;
		this.ip = ip;
		this.port = port;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public InetAddress getIp()
	{
		return ip;
	}

	public void setIp(InetAddress ip)
	{
		this.ip = ip;
	}

	public int getPort()
	{
		return port;
	}

	public void setPort(int port)
	{
		this.port = port;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(!(obj instanceof ClientServerData))
		{
			return false;
		}else if(obj == this)
		{
			return true;
		}else if(!this.name.equals(((ClientServerData)obj).name))
		{
			return false;
		}else if(this.port!=((ClientServerData)obj).port)
		{
			return false;
		}else if(!this.ip.equals(((ClientServerData)obj).ip))
		{
			return false;
		}
		return true;
	}
}
