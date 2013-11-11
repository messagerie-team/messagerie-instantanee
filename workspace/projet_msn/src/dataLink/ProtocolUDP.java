/**
 * 
 */
package dataLink;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

import clientServer.ThreadListenerUDP;

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
			this.writer = new DatagramPacket(null, 0);
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
