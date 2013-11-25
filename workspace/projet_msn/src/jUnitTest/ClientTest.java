package jUnitTest;

import static org.junit.Assert.*;

import org.junit.Test;

import clientServer.Client;

public class ClientTest 
{

	@Test
	public void testTreatIncomeTCP() 
	{
		Client Ctest = new Client("TestUnitaire",3009,"localhost");
		Ctest.treatIncomeTCP("JUnit");
	}

	@Test
	public void testTreatIncomeUDP()
	{
		Client Ctest = new Client("TestUnitaire",3009,"localhost");
		//test avec un message bien formé
		Ctest.treatIncomeUDP("dialog:ALLLOOOO");
		//test avec message mal formé
		Ctest.treatIncomeUDP("toto");
	}

	@Test
	public void testClient() {
		fail("Not yet implemented");
	}

	@Test
	public void testRegisterToServer() {
		fail("Not yet implemented");
	}

	@Test
	public void testUnregisterToServer() {
		fail("Not yet implemented");
	}

	@Test
	public void testAskListToServer() {
		fail("Not yet implemented");
	}

	@Test
	public void testAskClientConnectionToServer() {
		fail("Not yet implemented");
	}

	@Test
	public void testStartDialogToClient() {
		fail("Not yet implemented");
	}

	@Test
	public void testSendMessageToClient() {
		fail("Not yet implemented");
	}

	@Test
	public void testLaunchThread() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetName() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetName() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetId() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetId() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetClientList() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetClientList() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddClientList() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetListeningUDPPort() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetListeningUDPPort() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetDialogs() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetDialogs() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetThreadComunicationClient() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetThreadComunicationClient() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetThreadListenerUDP() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetThreadListenerUDP() {
		fail("Not yet implemented");
	}

	@Test
	public void testTreatIncomeDialog() {
		fail("Not yet implemented");
	}
}
