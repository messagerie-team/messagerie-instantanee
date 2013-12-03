package jUnitTest;

import static org.junit.Assert.*;

import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import model.Client;
import model.ClientDialog;
import model.ClientServerData;
import network.ProtocolTCP;

import org.junit.Test;
/**
 * 
 * @author Dorian, Mickaël, Raphaël, Thibault
 * 
 */
public class ClientDialogTest 
{
	private Client clientTest;
	private InetAddress inetTest;
	private ClientServerData clientDataTest;
	private ClientDialog clientDialogueTest;

	
	private void initTest()
	{
		try 
		{
			inetTest = InetAddress.getLocalHost();
		} 
		catch (UnknownHostException e) 
		{
			e.printStackTrace();
		}

		clientTest = new Client("TestUnitaire_C2",3009,"localhost");
		clientDialogueTest = new ClientDialog(clientTest,new ProtocolTCP(new Socket()));
		clientDataTest = new ClientServerData("JUNIT_C1", inetTest, 3009);
	}


	@Test
	public void testAddMessage()
	{
		initTest();
		//ajout du premier message
		clientDialogueTest.addMessage("test junit");
		//test si le dernier message est egale a celui qui est inserr� 
		assertEquals("test junit", clientDialogueTest.getLastMessage());
		//test si le dialogue message est egale au message la premiere fois
		assertEquals("\ntest junit", clientDialogueTest.getDialogue());
		//ajout du second message
		clientDialogueTest.addMessage("test junit2");
		//test si le dernier message est egale a celui qui est inserr� 
		assertEquals("test junit2", clientDialogueTest.getLastMessage());
		//test si le dialogue message n'est egale au message puisuqu'il contient tous les messages
		//assertNotEquals("\ntest junit2", clientDialogueTest.getDialogue());
		//test si le dialogue est �gale a l'ensemble des messages ajout�s
		assertEquals("\ntest junit\ntest junit2", clientDialogueTest.getDialogue());
	}
	
	@Test
	public void testSendMessage() 
	{
		initTest();
		//ajout du premier message
		clientDialogueTest.sendMessage("test junit");
		//test si le dernier message est egale a celui qui est inserr� 
		assertEquals("moi>test junit", clientDialogueTest.getLastMessage());
		//test si le dialogue message est egale au message la premiere fois
		assertEquals("\nmoi>test junit", clientDialogueTest.getDialogue());
		//ajout du second message
		clientDialogueTest.sendMessage("test junit2");
		//test si le dernier message est egale a celui qui est inserr� 
		assertEquals("moi>test junit2", clientDialogueTest.getLastMessage());
		//test si le dialogue message n'est egale au message puisuqu'il contient tous les messages
		//assertNotEquals("\nmoi>test junit2", clientDialogueTest.getDialogue());
		//test si le dialogue est �gale a l'ensemble des messages ajout�s
		assertEquals("\nmoi>test junit\nmoi>test junit2", clientDialogueTest.getDialogue());
	}

	@Test
	public void testReceiveMessage() 
	{
		initTest();
		//ajout du premier message
		clientDialogueTest.receiveMessage("test junit");
		//test si le dernier message est egale a celui qui est inserr� 
		assertEquals("test junit", clientDialogueTest.getLastMessage());
		//test si le dialogue message est egale au message la premiere fois
		assertEquals("\ntest junit", clientDialogueTest.getDialogue());
		//ajout du second message
		clientDialogueTest.receiveMessage("test junit2");
		//test si le dernier message est egale a celui qui est inserr� 
		assertEquals("test junit2", clientDialogueTest.getLastMessage());
		//test si le dialogue message n'est egale au message puisuqu'il contient tous les messages
		//assertNotEquals("\ntest junit2", clientDialogueTest.getDialogue());
		//test si le dialogue est �gale a l'ensemble des messages ajout�s
		assertEquals("\ntest junit\ntest junit2", clientDialogueTest.getDialogue());
	}

	@Test
	public void testAddClient()
	{
		//initialisation des variables utils pour les tests
		initTest();
		//test si la liste (ou plutot vector de merde) est vide
		assertEquals(clientDialogueTest.getClients().size(),0);
		//test si la liste (ou plutot vector de merde) n'est pas null
		assertNotNull(clientDialogueTest.getClients());
		//ajout d'un client
		clientDialogueTest.addClient(clientDataTest);
		//test si la liste (ou plutot vector de merde) poss�de un element
		assertEquals(clientDialogueTest.getClients().size(),1);
	}

	@Test
	public void testRemoveClient()
	{
		//initialisation des variables utils pour les tests
		initTest();
		//test si la liste (ou plutot vector de merde) est vide
		assertEquals(clientDialogueTest.getClients().size(),0);
		//test si la liste (ou plutot vector de merde) n'est pas null
		assertNotNull(clientDialogueTest.getClients());
		//ajout d'un client
		clientDialogueTest.addClient(clientDataTest);
		//test si la liste (ou plutot vector de merde) poss�de un element
		assertEquals(clientDialogueTest.getClients().size(),1);
		//on supprime un �l�ment existant
		clientDialogueTest.removeClient(clientDataTest);
		//test si la liste (ou plutot vector de merde) est vide
		assertEquals(clientDialogueTest.getClients().size(),0);
		//on ressuprime un �l�ment pour v�rifier si �a ne plante pas
		clientDialogueTest.removeClient(clientDataTest);
		//test si la liste (ou plutot vector de merde) est vide
		assertEquals(clientDialogueTest.getClients().size(),0);
	}

}
