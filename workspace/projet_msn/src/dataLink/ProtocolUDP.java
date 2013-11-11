/**
 * 
 */
package dataLink;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * @author Mickael
 * 
 */
public class ProtocolUDP extends Protocol
{
	public DatagramSocket socket;
	public DatagramPacket writer;
	public DatagramPacket reader;
	private static byte bufferReader[];
	private final static int sizeBufferReader = 60000;

	public ProtocolUDP(int localPort)
	{
		try
		{
			this.socket = new DatagramSocket(localPort);
			byte[] buffer = ("").getBytes();
			bufferReader = new byte[sizeBufferReader];
			this.writer = new DatagramPacket(buffer, 0);
			this.reader = new DatagramPacket(bufferReader, sizeBufferReader);
		} catch (SocketException e)
		{
			System.err.println("Erreur d'initialisation de ProtocolUDP, message: " + e.getMessage());
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

	public ProtocolUDP()
	{
		// TODO Auto-generated constructor stub
	}

	public void sendMessage(String message)
	{
		byte buffer[];
		try
		{
			buffer = message.getBytes("UTF-8");
			int length = buffer.length;
			this.writer = new DatagramPacket(buffer, length, this.socket.getInetAddress(), this.socket.getPort());
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
			return new String(data.getData());
		} catch (IOException e)
		{
			System.err.println("Erreur de reception de message de ProtocolUDP, message:" + e.getMessage());
		}
		return null;
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
}
