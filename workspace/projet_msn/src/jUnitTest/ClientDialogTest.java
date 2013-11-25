package jUnitTest;

import static org.junit.Assert.*;

import java.net.Socket;

import org.junit.Test;

import clientServer.ClientDialog;
import dataLink.ProtocolTCP;

public class ClientDialogTest {

	@Test
	public void testClientDialogStringProtocol() 
	{
		fail("Not yet implemented");
	}

	@Test
	public void testClientDialogProtocol() 
	{
		fail("Not yet implemented");
	}

	@Test
	public void testAddMessage()
	{
		ClientDialog cTest = new ClientDialog(new ProtocolTCP(new Socket()));
		cTest.addMessage("test junit");
		//test si le dernier message est egale a celui qui est inserré 
		assertEquals("test junit", cTest.getLastMessage());
		//test si le dialogue message est egale au message la premiere fois
		assertEquals("\ntest junit", cTest.getDialogue());
		cTest.addMessage("test junit2");
		//test si le dernier message est egale a celui qui est inserré 
		assertEquals("test junit2", cTest.getLastMessage());
		//test si le dialogue message n'est egale au message puisuqu'il contient tous les messages
		assertNotEquals("\ntest junit2", cTest.getDialogue());
		//test si le dialogue est égale a l'ensemble des messages ajoutés
		assertEquals("\ntest junit\ntest junit2", cTest.getDialogue());
	}
	
	@Test
	public void testSendMessage() 
	{
		fail("Not yet implemented");
	}

	@Test
	public void testReceiveMessage() 
	{
		fail("Not yet implemented");
	}

	@Test
	public void testAddClient()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testRemoveClient()
	{
		fail("Not yet implemented");
	}

}
