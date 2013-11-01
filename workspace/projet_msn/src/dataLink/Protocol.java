/**
 * 
 */
package dataLink;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author Mickael
 * 
 */
public class Protocol
{
	private PrintWriter writer;
	private BufferedReader reader;

	public Protocol(PrintWriter writer, BufferedReader reader)
	{
		this.writer = writer;
		this.reader = reader;
	}

	public Protocol(Socket socket)
	{
		try
		{
			this.writer = new PrintWriter(socket.getOutputStream());
			this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e)
		{
			System.err.println("Erreur d'initialisation de Protocol, message: " + e.getMessage());
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
