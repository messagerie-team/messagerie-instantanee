package network;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Classe représentant le transfert de fichier
 * 
 * @author Dorian, Mickaël, Raphaël, Thibault
 */
public class FileTransfer
{
	/**
	 * Methode permettant d'envoyer un fichier
	 * 
	 * @param os
	 * @param file
	 * @throws Exception
	 */
	public static void send(OutputStream os, String file) throws Exception
	{
		// sendfile
		File myFile = new File(file);
		byte[] mybytearray = new byte[(int) myFile.length() + 1];
		FileInputStream fis = new FileInputStream(myFile);
		BufferedInputStream bis = new BufferedInputStream(fis);
		bis.read(mybytearray, 0, mybytearray.length);
		System.out.println("Sending...");
		os.write(mybytearray, 0, mybytearray.length);
		os.flush();
		bis.close();
	}

	/**
	 * Methode permettant de recevoir un fichier
	 * 
	 * @param is
	 * @param folder
	 * @throws Exception
	 */
	public static void receiveFile(InputStream is, String folder) throws Exception
	{
		int filesize = 6022386;
		int bytesRead;
		int current = 0;
		byte[] mybytearray = new byte[filesize];

		FileOutputStream fos = new FileOutputStream(folder);
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		bytesRead = is.read(mybytearray, 0, mybytearray.length);
		current = bytesRead;

		do
		{
			bytesRead = is.read(mybytearray, current, (mybytearray.length - current));
			if (bytesRead >= 0)
			{
				current += bytesRead;
			}
		} while (bytesRead > -1);

		bos.write(mybytearray, 0, current);
		bos.flush();
		bos.close();
	}
}
