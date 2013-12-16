package network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

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
		File fileF = new File(file);
		String[] pathFile = file.split("/");
		String nameFile = pathFile[pathFile.length - 1];
		System.out.println("Nom du fichier : " + nameFile);
		
		PrintWriter print = new PrintWriter(new BufferedWriter(new OutputStreamWriter(os)), true);
		print.println(nameFile);
		print.flush();
		print.close();
		
		int fileSize = (int) fileF.length();
		FileInputStream fis = new FileInputStream(fileF);
		// Ouverture des OutputStream et InputStream du fichier
		DataOutputStream dos = new DataOutputStream(os);
		// Affichage de la taille du fichier
		int sizeAvailable = fis.available();
		System.out.println("Size available for File Input Stream : " + sizeAvailable);
		// Déclaration du tableau de bytes
		byte byteTab[] = new byte[fileSize];
		// Lecture des bytes
		fis.read(byteTab);
		long debut = 0, total = 0;
		debut = System.nanoTime();
		dos.write(byteTab);
		total = System.nanoTime() - debut; // Calcul du temps écoulé pour
											// envoyer un paquet

		System.out.println("Envoi total du paquet en " + total + " nano-secondes");

		dos.flush();

		fis.close();

		System.out.println("Flux envoyé");
		// this.socket.close();
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
		// int sizeAvailable = receiveSocket.getReceiveBufferSize();
		// System.out.println("Available Size Server Socket: " + sizeAvailable);
		// Ouverture des Input et Output du fichier à enregistrer
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		String fichier = reader.readLine();
		reader.close();
		System.out.println(fichier);
		
		
		DataInputStream dis = new DataInputStream(is);
		// Reception du fichier
		File receivedFile = new File(fichier);
		FileOutputStream fos = new FileOutputStream(receivedFile);

		// attente available devienne different de zero
		while (dis.available() == 0)
			;

		// Attente de 10
		Thread.sleep(10);
		// Déclaration de la taille du fichier dans size
		int size = dis.available();
		int i = 0;
		// Tant que la taille différente de 0
		while (size != 0)
		{
			i++;
			size = dis.available();
			// Reception des bytes
			byte receivedDataTab[] = new byte[size];
			dis.read(receivedDataTab);
			// Ecriture dans le fichier
			fos.write(receivedDataTab);
			System.out.println("Taille du paquet :" + size + " octets");
			Thread.sleep(15);
		}
		System.out.println(i + " segments TCP recus");

		// Close(s)
		fos.close();

		dis.close();
	}
}
