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

	final static int size = 2048; 
	final static byte buffer[] = new byte[size];

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		
		ThreadListenerUDP threadListenerUDP = new ThreadListenerUDP(Integer.parseInt(args[1]));
		threadListenerUDP.start();
		
		
		Scanner sc = new Scanner(System.in);
		String buff = null;
		
		while(!"exit".equals(buff))
		{	
			buff = sc.nextLine();
			System.out.println("APRES NEXT : " + buff);
			send(buff, args[0]);
			sc.reset();
		}
		sc.close();
	
	}
	
	public static void send(String message, String ip)
	{
		InetAddress server;
		try {
			server = InetAddress.getByName(ip);
			//int length = message.length(); 
			byte buffer[] = message.getBytes("UTF-8"); 
			int length = buffer.length;
			System.out.println("BUFFER : " + message + " ET TAILLE : " + length);
			DatagramPacket dataSent = new DatagramPacket(buffer,length,server,ThreadListenerUDP.getPort()); 
			DatagramSocket socket = new DatagramSocket(); 
			socket.send(dataSent); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 	
	}
	
}
