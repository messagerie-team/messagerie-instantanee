/**
 * 
 */
package clientServer;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

/**
 * Cette classe va dispaitre pour se retrouver dans l'UI de Mika.
 * Le main sera dans l'action du bouton 'Envoyer'.
 * La méthode send sera dans la classe Protocol.
 * @author 
 *
 */
public class ClientToClient 
{
	
	private final static int size = 1024; 
	private final static byte buffer[] = new byte[size];

	/**
	 * Main permettant de lancer 2 clients pour discuter entre eux.
	 * args[0] correspond à l'@IP du client à joindre.
	 * args[1] correspond au port d'écoute.
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
	
	/**
	 * Cette méthode sert à envoyer un message via le protocol UDP.
	 * Elle doit être déplacée dans la classe Protocol.
	 * @param message
	 * @param ip
	 */
	public static void send(String message, String ip)
	{
		InetAddress server;
		try {
			server = InetAddress.getByName(ip);
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
