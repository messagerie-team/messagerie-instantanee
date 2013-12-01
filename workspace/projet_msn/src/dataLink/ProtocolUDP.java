package dataLink;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * 
 * @author Dorian, Thibault, Raphaël
 * 
 */
public class ProtocolUDP extends Protocol
{
	/**
	 * Socket de communication UDP.
	 * 
	 * @see DatagramSocket
	 */
	private DatagramSocket socket;
	/**
	 * {@link DatagramPacket} permettant l'envoie de paquet.
	 */
	private DatagramPacket writer;
	/**
	 * {@link DatagramPacket} permettant la reception de paquet.
	 */
	private DatagramPacket reader;
	/**
	 * Buffer servant à la reception des paquets.
	 */
	private static byte bufferReader[];
	/**
	 * Taille du buffer.
	 * A déterminer pourquoi.
	 */
	// Pourquoi 60000 ? Limite théorique = 65535, limite en IPv4 = 65507
	private final static int sizeBufferReader = 60000;
	/**
	 * Dernier port avec lequel le protocol a communiqué
	 * (Port ouvert du dernier client avec lequel il a comuniqué)
	 */
	private int lastPort;
	/**
	 * Dernière adresse avec laquelle le protocol a communiqué
	 * (Adresse Ip du dernier client avec lequel il a comuniqué)
	 */
	private InetAddress lastAdress;

	/**
	 * Constructeur par défaut du protocol UDP
	 * @param localPort
	 */
	public ProtocolUDP(int localPort)
	{
		super(localPort);
		try
		{
			this.socket = new DatagramSocket(localPort);
			// this.socketReceive = new DatagramSocket(localPort);
			byte[] buffer = ("").getBytes();
			bufferReader = new byte[sizeBufferReader];
			this.writer = new DatagramPacket(buffer, 0);
			this.reader = new DatagramPacket(bufferReader, sizeBufferReader);
			this.lastPort = 0;
			this.lastAdress = null;
		} catch (SocketException e)
		{
			System.err.println("Erreur d'initialisation de ProtocolUDP, message: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void sendMessage(String message, InetAddress adress, int port)
	{
		byte buffer[];
		try
		{
			buffer = message.getBytes("UTF-8");
			int length = buffer.length;
			this.writer = new DatagramPacket(buffer, length, adress, port);
			this.socket.send(writer);
		} catch (IOException e)
		{
			System.err.println("Erreur d'envoie du message de ProtocolUDP, message:" + e.getMessage());
		}
	}

	public void sendMessage(String message)
	{
		byte buffer[];
		try
		{
			buffer = message.getBytes("UTF-8");
			int length = buffer.length;
			this.writer = new DatagramPacket(buffer, length, this.socket.getInetAddress(), this.getLocalPort());
			this.socket.send(writer);
		} catch (IOException e)
		{
			System.err.println("Erreur d'envoie du message de ProtocolUDP, message:" + e.getMessage());
		}
	}

	public String readMessage()
	{
		try
		{
			bufferReader = new byte[sizeBufferReader];
			DatagramPacket data = new DatagramPacket(bufferReader, sizeBufferReader);
			socket.receive(data);
			this.lastPort = data.getPort();
			this.lastAdress = data.getAddress();
			return new String(data.getData(), 0, data.getLength());
		} catch (IOException e)
		{
			System.err.println("Erreur de reception de message de ProtocolUDP, message:" + e.getMessage());
		}
		return "";
	}

	public void close()
	{
		this.socket.close();
		// this.socketReceive.close();
	}

	public DatagramPacket getWriter()
	{
		return writer;
	}

	public void setWriter(DatagramPacket writer)
	{
		this.writer = writer;
	}

	public DatagramPacket getReader()
	{
		return reader;
	}

	public void setReader(DatagramPacket reader)
	{
		this.reader = reader;
	}

	public int getLastPort()
	{
		return lastPort;
	}

	public void setLastPort(int lastPort)
	{
		this.lastPort = lastPort;
	}

	public InetAddress getLastAdress()
	{
		return lastAdress;
	}

	public void setLastAdress(InetAddress lastAdress)
	{
		this.lastAdress = lastAdress;
	}

}
