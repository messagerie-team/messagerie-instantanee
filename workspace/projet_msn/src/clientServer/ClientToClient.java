/**
 * 
 */
package clientServer;

import java.io.IOException;
import java.net.*;

/**
 * @author Mickael
 *
 */
public class ClientToClient 
{

	private String ip;
	final static int size = 1024; 
	final static byte buffer[] = new byte[size];

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub

	}
	
	public void send(String message)
	{
		InetAddress serveur;
		try {
			serveur = InetAddress.getByName(ip);
			int length = message.length(); 
			byte buffer[] = message.getBytes(); 
			DatagramPacket dataSent = new DatagramPacket(buffer,length,serveur,ThreadListenerUDP.getPort()); 
			DatagramSocket socket = new DatagramSocket(); 
			socket.send(dataSent); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 	
	}
	
	public void setIp(String ip) 
	{
		this.ip = ip;
	}

}
