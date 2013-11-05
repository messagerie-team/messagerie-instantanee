/**
 * 
 */
package clientServer;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

/**
 * @author Mickael
 *
 */
public class ClientToClient 
{

	final static int size = 1024; 
	final static byte buffer[] = new byte[size];

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		
		ThreadListenerUDP threadListenerUDP = new ThreadListenerUDP();
		threadListenerUDP.start();
		
		String buff = null;
		while(!"exit".equals(buff))
		{
			Scanner sc = new Scanner(System.in);
			buff = sc.nextLine();
			send(buff, args[1]);
			sc.close();
		}
		
	}
	
	public static void send(String message, String ip)
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
	
}
