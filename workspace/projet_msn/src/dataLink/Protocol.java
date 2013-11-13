package dataLink;

import java.net.InetAddress;

public abstract class Protocol
{
	private int localPort;

	public Protocol(int localPort)
	{
		this.localPort = localPort;
	}

	public abstract void sendMessage(String message, InetAddress adress, int port);

	public abstract void sendMessage(String message);

	public abstract String readMessage();

	public abstract void close();

	public int getLocalPort()
	{
		return localPort;
	}

	public void setLocalPort(int localPort)
	{
		this.localPort = localPort;
	}

}
