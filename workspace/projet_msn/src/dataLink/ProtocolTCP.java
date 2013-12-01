package dataLink;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @author Dorian, Thibault, Raphaël
 * 
 * Permet de représenter le protocol TCP pour la communication TCP.
 * @see Protocol.
 */
public class ProtocolTCP extends Protocol
{
	/**
	 * Socket de communication.
	 * 
	 * @see Socket
	 */
	public Socket socket;
	/**
	 * {@link PrintWriter} permettant d'envoyer des messages.
	 */
	public PrintWriter writer;
	/**
	 * {@link BufferedReader} permettant de réceptionner des messages.
	 */
	public BufferedReader reader;

	/**
	 * Constructeur par défaut du protocol par défaut.
	 * 
	 * @param socket
	 */
	public ProtocolTCP(Socket socket)
	{
		super(socket.getLocalPort());
		try
		{
			this.socket = socket;
			this.writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream())), true);
			this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
		} catch (IOException e)
		{
			System.err.println("Erreur d'initialisation de Protocol, message: " + e.getMessage());
		}
	}

	public void sendMessage(String message, InetAddress adress, int port)
	{
		try
		{
			Socket socket = new Socket(adress.getCanonicalHostName(), port);
			PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
			writer.println(message);
			socket.close();
		} catch (IOException e)
		{
			System.err.println("Erreur d'envoie de message protocolTCP, message:" + e.getMessage());
		}

	}

	public void sendMessage(String message)
	{
		this.writer.println(message);
	}

	public String readMessage()
	{
		try
		{
			return this.reader.readLine();
		} catch (IOException e)
		{
			System.err.println("Erreur lecture message dans la class Protocol, message: " + e.getMessage());
		}
		return null;
	}

	public void close()
	{
		try
		{
			this.reader.close();
			this.writer.close();
			this.socket.close();
		} catch (IOException e)
		{
			System.err.println("Erreur fermeture dans la class Protocol, message: " + e.getMessage());
		}
	}

	public PrintWriter getWriter()
	{
		return writer;
	}

	public void setWriter(PrintWriter writer)
	{
		this.writer = writer;
	}

	public BufferedReader getReader()
	{
		return reader;
	}

	public void setReader(BufferedReader reader)
	{
		this.reader = reader;
	}
}
