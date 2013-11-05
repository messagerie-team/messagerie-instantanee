package clientServer;

import java.net.*;

public class ThreadListenerUDP extends Thread {

	private final static int port = 30003;
	private final static int size = 1024;
	private final static byte buffer[] = new byte[size];
	private DatagramSocket socket;

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
				socket.send(data);
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
