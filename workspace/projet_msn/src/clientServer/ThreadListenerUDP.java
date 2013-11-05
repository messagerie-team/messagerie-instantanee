package clientServer;

import java.net.*;

public class ThreadListenerUDP extends Thread {

	// Pourquoi 60000 ? Limite théorique = 65535, limite en IPv4 = 65507
	private static int port;
	private final static int size = 60000;
	private static byte buffer[];
	private DatagramSocket socket;
	
	/**
	 * Constructeur de la classe ThreadListenerUDP.
	 * Il recoit le port d'écoute en paramètre.
	 * @param port
	 */
	public ThreadListenerUDP(int port)
	{
		this.port = port;
	}
	
	/**
	 * Ce thread récéptionne les messages et les affiches dans la console.
	 */
	public void run() 
	{
		try 
		{
			socket = new DatagramSocket(port);
			while (true) 
			{
				buffer = new byte[size];
				DatagramPacket data = new DatagramPacket(buffer, buffer.length);
				socket.receive(data);
				//System.out.println(data.getAddress());
				System.out.println(new String(data.getData()));
			}
		} catch (Exception e) 
		{
			// TODO: handle exception
		}
	}
	
	public static int getPort()
	{
		return port;
	}

}
