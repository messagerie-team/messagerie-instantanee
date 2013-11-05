package clientServer;

import java.net.*;

public class ThreadListenerUDP extends Thread {

	private static int port;
	private final static int size = 1024;
	private static byte buffer[] = new byte[size];
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
				DatagramPacket data = new DatagramPacket(buffer, buffer.length);
				socket.receive(data);
				System.out.println(data.getAddress());
				System.out.println(new String(data.getData()));
				buffer = new byte[size];
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
